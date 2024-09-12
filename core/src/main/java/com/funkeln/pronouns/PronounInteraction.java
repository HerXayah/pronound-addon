package com.funkeln.pronouns;

import com.funkeln.pronouns.profile.ProfileRepository;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.api.models.OperatingSystem;

import java.util.Optional;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
public class PronounInteraction extends AbstractBulletPoint {
	private static final String URL = "https://en.pronouns.page/@%s";
	private static final Component TITLE = Component.text("")
	                                                // P
	                                                .color(TextColor.color(228, 3, 3))
	                                                .text("P")
	                                                // R
	                                                .color(TextColor.color(255, 140, 0))
	                                                .text("r")
	                                                // O
	                                                .color(TextColor.color(255, 237, 0))
	                                                .text("o")
	                                                // N
	                                                .color(TextColor.color(0, 128, 38))
	                                                .text("n")
	                                                // O
	                                                .color(TextColor.color(36, 64, 142))
	                                                .text("o")
	                                                // U
	                                                .color(TextColor.color(115, 41, 130))
	                                                .text("u")
	                                                // N
	                                                .color(TextColor.color(255, 175, 200))
	                                                .text("n")
	                                                // S
	                                                .color(TextColor.color(116, 215, 238))
	                                                .text("n")
	                                                // Interaction
	                                                .color(TextColor.color(-1)).text(" Interaction");

	protected PronounInteraction() {
		super(TITLE);
	}

	@Override
	public void execute(Player player) {
		Optional.ofNullable(ProfileRepository.find(player.getUniqueId())).ifPresent(
			 profile -> OperatingSystem.getPlatform().openUri(URL.formatted(profile.get().username()))
		);
	}

	@Override
	public boolean isVisible(Player player) {
		if (!PronounAddon.getInstance().configuration().enabled().get())
			return false;
		if (player.getUniqueId().equals(this))
			return true;
		return ProfileRepository.find(player.getUniqueId()).isPresent();
	}
}
