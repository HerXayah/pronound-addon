package com.funkeln.pronouns.profile;


import com.funkeln.pronouns.flag.FlagResolver;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/8/2024 @2:39 AM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class Profile {
  public static final String API_URL = "https://pronouns.page/api/";
  public static long PROFILE_EXPIRY = 5 * 60 * 1000L;
  public static long PRONOUN_EXPIRY = 5 * 60 * 1000L;

  private final UUID id;
  private String websiteName = "";
  private String pronoun;
  public Icon[] flags;

  private boolean requestedUpdate;
  private boolean firstRequestSpent = false;
  private long lastNameUpdate = System.currentTimeMillis();
  private long lastPronounUpdate = System.currentTimeMillis();

  public Profile(UUID id) {
    this.id = id;
  }

  public void updateName(String name) {
    if (!websiteName.equals(name)) {
      this.websiteName = name;
      pronoun = null;
      flags = null;
      requestedUpdate = true;
    }
    lastNameUpdate = System.currentTimeMillis();
  }

  public void update() {
    firstRequestSpent = true;
    requestedUpdate = false;
    Laby.labyAPI().taskExecutor().getPool().submit(() -> {
      // async
      try {
        URL url = new URI(API_URL + "profile/get/" + websiteName + "?version=2").toURL();
        JsonObject profile = null;
        try (InputStream stream = url.openStream()) {
          JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
          profile = element.getAsJsonObject();
        }
        updatePronouns(pronounFromJson(profile));
        List<String> flagNames = flagNamesFromJson(profile);
        FlagResolver.iconsFromName(flagNames, this::updateFlags);
      } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
      }
    });
  }
  
  public boolean updateRequired() {
    return System.currentTimeMillis() - lastPronounUpdate > PRONOUN_EXPIRY || requestedUpdate;
  }

  public boolean requiresUpdateNow() {
    return !firstRequestSpent;
  }

  public static String pronounFromJson(JsonObject profile) {
    JsonArray pronounsArray = profile.getAsJsonObject("profiles").getAsJsonObject("en").getAsJsonArray("pronouns");
    if (pronounsArray == null || pronounsArray.isEmpty()) return null;
    List<String> outputPronounList = new ArrayList<>();
    for(JsonElement pronoun : pronounsArray) {
      outputPronounList.add(pronoun.getAsJsonObject().get("value").getAsString());
      if(outputPronounList.size() > 3) {
        break;
      }
    }
    return String.join(" & ", outputPronounList);
  }

  public static List<String> flagNamesFromJson(JsonObject profile) {
    JsonObject profiles = profile.getAsJsonObject("profiles");
    if (profiles == null) {
      return null;
    }
    JsonObject enProfile = profiles.getAsJsonObject("en");
    if (enProfile == null) {
      return null;
    }
    JsonArray flagsArray = enProfile.getAsJsonArray("flags");
    if (flagsArray == null || flagsArray.isEmpty()) {
      return null;
    }
    List<String> flagNamesList = new ArrayList<>();
    for (JsonElement flag : flagsArray) {
      if(flagNamesList.size() > 3) {
        break;
        }
      flagNamesList.add(flag.getAsString());
    }
    return flagNamesList;
  }

  public boolean expired() {
    return System.currentTimeMillis() - lastNameUpdate > PROFILE_EXPIRY
  ;
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

  public void updatePronouns(String pronoun) {
    this.pronoun = pronoun;
    lastPronounUpdate = System.currentTimeMillis();
  }

  public void updateFlags(Icon[] flags) {
    this.flags = flags;
    lastPronounUpdate = System.currentTimeMillis();
  }

  public String username() {
    return websiteName;
  }

  public String pronoun() {
    return pronoun;
  }
}
