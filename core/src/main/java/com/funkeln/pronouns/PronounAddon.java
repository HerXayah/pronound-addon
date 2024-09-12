package com.funkeln.pronouns;

import com.funkeln.pronouns.nametag.NameTagFlag;
import com.funkeln.pronouns.nametag.NameTagPronoun;
import com.google.gson.JsonObject;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
@AddonMain
public class PronounAddon extends LabyAddon<PronounConfiguration> {
	private static PronounAddon instance;

	public PronounAddon() {
		instance = this;
	}

	public static PronounAddon getInstance() {
		return instance;
	}

	@Override
	@SuppressWarnings("unstable")
	protected void enable() {
		instance = this;

		this.labyAPI().eventBus().registerListener(new PronounEvents());
		this.labyAPI().interactionMenuRegistry().register(new PronounInteraction());
		this.labyAPI().tagRegistry().registerBefore(
			 "pronouns",
			 "pronouns_flags",
			 PositionType.BELOW_NAME,
			 new NameTagPronoun()
		);
		this.labyAPI().tagRegistry().registerAfter(
			 "pronouns_flags",
			 "pronouns",
			 PositionType.ABOVE_NAME,
			 new NameTagFlag()
		);

		Executors.newSingleThreadScheduledExecutor()
		         .scheduleAtFixedRate(this::publishNameUpdate, 0L, 10L, TimeUnit.SECONDS);

		this.registerSettingCategory();
		this.configuration().name().addChangeListener(this::publishNameUpdate);

		this.logger().info("pronouns-addon says hellow. :D");
		this.logger().info("made by funkeln with \u2661");
	}

	public void publishNameUpdate() {
		String newName = configuration().name().get();
		logger().debug("publishing name change to %s".formatted(newName));
		JsonObject data = new JsonObject();
		data.addProperty("name", newName);
		Optional.ofNullable(labyAPI().labyConnect().getSession()).ifPresent(
			 session -> session.sendBroadcastPayload("pronouns", data)
		);
	}

	@Override
	protected Class<PronounConfiguration> configurationClass() {
		return PronounConfiguration.class;
	}
}
