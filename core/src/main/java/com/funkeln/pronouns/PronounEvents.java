package com.funkeln.pronouns;

import com.funkeln.pronouns.profile.ProfileRepository;
import com.google.gson.JsonElement;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectBroadcastEvent;

@SuppressWarnings("unused")
public final class PronounEvents {
	@Subscribe
	public void handleBroadcastEvent(LabyConnectBroadcastEvent event) {
		if (
			 !PronounAddon.getInstance().configuration().enabled().getOrDefault(false) ||
			 !event.getKey().equals("pronouns")
		) return;
		JsonElement payload = event.getPayload();
		if (!payload.isJsonObject())
			return;
		ProfileRepository.enterName(
			 event.getSender(),
			 payload.getAsJsonObject().get("name").getAsJsonPrimitive().getAsString()
		);
		ProfileRepository.updateProfiles();
	}

	@Subscribe
	public void handleServerJoinEvent(ServerJoinEvent event) {
		if (!PronounAddon.getInstance().configuration().enabled().getOrDefault(false))
			return;
		PronounAddon.getInstance().publishNameUpdate();
	}
}
