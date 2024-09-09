package com.funkeln.pronouns.event;

import com.funkeln.pronouns.PronounAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/9/2024 @11:18 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class ServerJoinListener {
  private final PronounAddon addon;

  public ServerJoinListener(PronounAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void on(ServerJoinEvent event) {
    addon.publishNameUpdate();
  }
}
