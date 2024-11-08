package com.funkeln.pronouns.interaction;

import com.funkeln.pronouns.PronounAddon;
import com.funkeln.pronouns.profile.Profile;
import com.funkeln.pronouns.profile.ProfileRepository;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.api.models.OperatingSystem;
import java.util.UUID;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/10/2024 @5:36 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class UserInteraction extends AbstractBulletPoint {

  private static final String URL = "https://en.pronouns.page/@%s";

  private final PronounAddon pronounAddon;

  private Profile otherPlayer;

  public UserInteraction(
    PronounAddon pronounAddon
  ) {
    super(Component.translatable("pronouns.bulletPoint.open.name"));

    this.pronounAddon = pronounAddon;
  }

  public void execute(Player player) {
    this.otherPlayer = ProfileRepository.find(player.getUniqueId()).orElse(null);
    //Debug reasons
    //ProfileRepository.enterName(player.getUniqueId(), "funkeln");
    OperatingSystem.getPlatform().openUri(String.format(URL, otherPlayer.username()));
  }

  public boolean isVisible(Player player) {
    if (!pronounAddon.configuration().enabled().get()) {
      return false;
    }

    //return this.broadcastController.get(player.getUniqueId()) != null;
    return ProfileRepository.find(player.getUniqueId()).isPresent();
  }

}
