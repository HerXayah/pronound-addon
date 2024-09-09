package com.funkeln.pronouns.event;

import com.funkeln.pronouns.profile.ProfileRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectBroadcastEvent;
import java.util.UUID;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/9/2024 @9:58 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class BroadcastEventListener {
    @Subscribe
    public void onBroadcastReceive(LabyConnectBroadcastEvent event) {
      if (!event.getKey().equals("pronouns")) {
        return;
      }
      JsonElement payload = event.getPayload();
      if (!payload.isJsonObject()) {
        return;
      }
      JsonObject jsonObject = payload.getAsJsonObject();
      UUID sender = event.getSender();
      String name = jsonObject.get("name").getAsJsonPrimitive().getAsString();
      ProfileRepository.enterName(sender, name);
      ProfileRepository.updateProfiles(true);
    }
  }
