package com.funkeln.pronouns.profile;


import com.funkeln.pronouns.flag.FlagResolver;
import com.funkeln.pronouns.util.json.JsonArray;
import com.funkeln.pronouns.util.json.JsonObject;
import com.funkeln.pronouns.util.json.JsonParser;
import com.funkeln.pronouns.util.json.JsonValue;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
public class Profile {
	private static final String API_URL = "https://pronouns.page/api/profile/get/%s?version=2";

	public Icon[] flags;
	private String username = "";
	private String pronoun;

	public static String pronounFromJson(JsonObject profile) {
		JsonArray pronouns = profile.get("profiles").asObject().get("en").asObject()
		                            .get("pronouns").asArray();
		return (pronouns == null || pronouns.isEmpty() ? null : String.join(
			 " & ",
			 pronouns.asList(ArrayList::new)
			         .stream()
			         .map(value -> value.asObject().get("value").asString())
			         .toList()
		));
	}

	public static @NotNull List<String> flagNamesFromJson(JsonObject profile) {
		JsonObject profiles = profile.get("profiles").asObject();
		List<String> emptyList = Collections.emptyList();

		if (profiles == null)
			return emptyList;

		JsonObject enProfile = profiles.get("en").asObject();
		if (enProfile == null)
			return emptyList;

		JsonArray flagsArray = enProfile.get("flags").asArray();
		return flagsArray == null || flagsArray.isEmpty() ? Collections.emptyList() : List.of(
			 flagsArray.asList(ArrayList::new)
			           .stream()
			           .map(JsonValue::asString)
			           // .limit(3) TODO: ... | why? ;w; ~luzey
			           .toArray(String[]::new)
		);
	}

	private static JsonObject getProfile(String username) {
		try (
			 HttpClient client = HttpClient
				  .newBuilder()
				  .version(Version.HTTP_2)
				  .executor(Executors.newSingleThreadExecutor())
				  .connectTimeout(Duration.of(10L, ChronoUnit.SECONDS))
				  .build()
		) {
			return new JsonParser(client.send(
				 HttpRequest.newBuilder().uri(URI.create(API_URL.formatted(
					  URLEncoder.encode(username, StandardCharsets.UTF_8)
				 ))).build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
			).body()).parse().asObject();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateName(String name) {
		if (username.equals(name)) return;
		this.username = name;
		pronoun = null;
		flags = null;
	}

	public void update() {
		JsonObject object = getProfile(username);
		this.pronoun = pronounFromJson(object);
		FlagResolver.iconsFromName(
			 this::updateFlags,
			 flagNamesFromJson(object)
		);
	}

	public boolean pronounsAvailable() {
		return pronoun != null;
	}

	public boolean flagsAvailable() {
		return flags != null;
	}

	public Icon[] flags() {
		return flags;
	}

	public void updateFlags(Icon[] flags) {
		this.flags = flags;
	}

	public String username() {
		return this.username;
	}

	public String pronoun() {
		return this.pronoun;
	}
}
