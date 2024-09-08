package com.funkeln.pronouns.utils;

import com.funkeln.pronouns.PronounAddon;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class PronounsAPI {

  public static final String API_URL = "https://en.pronouns.page/api/";
  public static final String FLAGS_URL = "https://en.pronouns.page/flags/";
  private static PronounsAPI instance;

  private static final Queue<Consumer<Void>> queues = new ConcurrentLinkedQueue<>();
  private static final Map<String, ProfileFetchListener> listeners = new HashMap<>();

  public static void addProfileFetchListener(String username, ProfileFetchListener listener) {
    listeners.put(username, listener);
  }

  public static PronounsAPI getInstance() {
    if (instance == null) {
      instance = new PronounsAPI();
    }
    return instance;
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
        Icon[] flags = getFlags(profile);
        Profile profileObj = new Profile(name, pronoun, flags);
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

  public static Icon[] getFlags(JsonObject profile) {
    if (PronounAddon.getInstance().configuration().enabled().get()) {
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
      List<Icon> flagsList = new ArrayList<>();
      List<String> flagNamesList = new ArrayList<>();
      for (JsonElement flag : flagsArray) {
        String url = FLAGS_URL + flag.getAsString() + ".png";
        try {
          URL iconURL = new URL(url);
          Icon icon = Icon.url(String.valueOf(iconURL));
          flagsList.add(icon);
          flagNamesList.add(flag.getAsString());
        } catch (Exception e) {
          e.printStackTrace();  // Consider better error handling here
        }
      }
      return flagsList.toArray(new Icon[0]);
    }
    return null;
  }
  }
