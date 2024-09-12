package com.funkeln.pronouns.profile;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
public class ProfileRepository {
	private final static ConcurrentHashMap<UUID, Profile> profiles = new ConcurrentHashMap<>();

	ProfileRepository() {}

	public static void enterName(UUID id, String name) {
		profiles.remove(id);
		Profile profile = new Profile();
		profile.updateName(name);
		profiles.put(id, profile);
	}

	public static Optional<@Nullable Profile> find(UUID id) {
		return Optional.ofNullable(profiles.getOrDefault(id, null));
	}

	public static void updateProfiles() {
		profiles.values().forEach(Profile::update);
	}
}
