package com.funkeln.pronouns.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class PronounsAPI {

  public static final String API_URL = "https://en.pronouns.page/api/";
  public static final String FLAGS_URL = "https://en.pronouns.page/flags/";

  private static final Queue<Consumer<Void>> queues = new ConcurrentLinkedQueue<>();
  private static final Map<String, ProfileFetchListener> listeners = new HashMap<>();

  public static void addProfileFetchListener(String username, ProfileFetchListener listener) {
    listeners.put(username, listener);
  }

  private static void fetchProfile(String name) {
    new Thread(() -> {
      try {
        URL url = new URI(API_URL + "profile/get/" + name + "?version=2").toURL();
        JsonObject profile = null;
        try (InputStream stream = url.openStream()) {
          JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
          profile = element.getAsJsonObject();
        }
        String pronoun = getPronoun(profile);
        Profile profileObj = new Profile(name, pronoun);
        Pridetags.profiles.add(profileObj);

        // Notify listener
        ProfileFetchListener listener = listeners.get(name);
        if (listener != null) {
          listener.onProfileFetched(profileObj);
        }
      } catch (URISyntaxException | IOException e) {
        // Notify listener of failure
        ProfileFetchListener listener = listeners.get(name);
        if (listener != null) {
          listener.onProfileFetchFailed(name, e);
        }
      }
    }).start();
  }

  public interface ProfileFetchListener {
    void onProfileFetched(Profile profile);
    void onProfileFetchFailed(String username, Exception e);
  }


  public static Profile getProfile(String name) {
    Optional<Profile> maybeProfile = Pridetags.profiles.stream()
        .filter(profile -> profile.getUsername().equals(name))
        .findFirst();

    if (maybeProfile.isPresent()) return maybeProfile.get();

    fetchProfile(name);
    return null;
  }

  public static String getPronoun(JsonObject profile) {
    JsonArray pronounsArray = profile.getAsJsonObject("profiles").getAsJsonObject("en").getAsJsonArray("pronouns");
    if (pronounsArray == null || pronounsArray.isEmpty()) return null;
    return pronounsArray.get(0).getAsJsonObject().get("value").getAsString();
  }
}
