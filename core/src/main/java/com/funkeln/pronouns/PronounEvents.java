package com.funkeln.pronouns;

import com.funkeln.pronouns.profile.ProfileRepository;
import com.google.gson.JsonElement;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectBroadcastEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class PronounEvents {
	@Subscribe
	public void handleBroadcastEvent(LabyConnectBroadcastEvent event) {
		CompletableFuture.supplyAsync(() -> {
			CompletableFuture<UUID> returnVal = CompletableFuture.completedFuture(event.getSender());
			if (
				 !PronounAddon.getInstance().configuration().enabled().getOrDefault(false) ||
				 !event.getKey().equals("pronouns")
			) return returnVal;
			JsonElement payload = event.getPayload();
			if (!payload.isJsonObject())
				return returnVal;
			ProfileRepository.enterName(
				 event.getSender(),
				 payload.getAsJsonObject().get("name").getAsJsonPrimitive().getAsString()
			);
			ProfileRepository.updateProfiles();
			return returnVal;
		});
	}

	@Subscribe
	public void handleServerJoinEvent(ServerJoinEvent event) {
		if (!PronounAddon.getInstance().configuration().enabled().getOrDefault(false))
			return;
		PronounAddon.getInstance().publishNameUpdate();
	}

	@Subscribe
	public void handleEntitySpawnEvent(EntitySpawnEvent event) {
		if (!PronounAddon.getInstance().configuration().enabled().getOrDefault(false))
			return;
		if (!(event.entity() instanceof Player player)) return;
		PronounAddon.getInstance().publishNameUpdate();
	}
}
