package com.funkeln.pronouns.utils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class Pridetags {

  public static Set<Profile> profiles = new CopyOnWriteArraySet<>();



  public static Set<Profile> getProfiles() {
    // return all profiles from the HashMap
    StringBuilder sb = new StringBuilder();
    for (Profile profile : profiles) {
      // put them into a big string
      sb.append(profile.getUsername() + " : " + profile.getPronoun() + "\n");
    }
    return profiles;
  }

}