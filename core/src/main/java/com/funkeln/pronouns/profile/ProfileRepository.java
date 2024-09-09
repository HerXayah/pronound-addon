package com.funkeln.pronouns.profile;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/9/2024 @10:21 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class ProfileRepository {
  private final static Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

  public static void enterName(UUID id, String name) {
    profiles.computeIfAbsent(id, Profile::new).updateName(name);
  }

  public static Optional<Profile> find(UUID id) {
    return Optional.ofNullable(profiles.get(id));
  }

  public static Set<UUID> knownPlayers() {
    return profiles.keySet();
  }

  public static void updateProfiles(boolean onlyUpdateFirstTimes) {
    for (Profile profile : profiles.values()) {
      if (profile.updateRequired()) {
        if (onlyUpdateFirstTimes && !profile.requiresUpdateNow()) {
          return;
        }
        profile.update();
      }
    }
  }

  public static void clearExpired() {
    profiles.entrySet().removeIf(
      entry -> entry.getValue().expired()
    );
  }
}
