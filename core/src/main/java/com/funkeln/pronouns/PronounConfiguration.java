package com.funkeln.pronouns;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget.ButtonSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.MethodOrder;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
@ConfigName("settings")
@SuppressWarnings("unused")
public class PronounConfiguration extends AddonConfig {
	private static final String
		 URL_PRONOUNS = "https://pronouns.page",
		 URL_ADDON = "https://github.com/HerXayah/pronound-addon/blob/master/README.md";

	@SwitchSetting
	@SpriteSlot(size = 32, x = 1)
	private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

	@SwitchSetting
	private final ConfigProperty<Boolean> renderFlags = new ConfigProperty<>(true);

	@SwitchSetting
	private final ConfigProperty<Boolean> renderTag = new ConfigProperty<>(true);

	@TextFieldSetting
	private final ConfigProperty<String> name = new ConfigProperty<>("");

	@ButtonSetting
	@MethodOrder(after = "name")
	public void button(Setting setting) {
		OperatingSystem.getPlatform().openUri(URL_PRONOUNS);
	}

	@ButtonSetting
	@MethodOrder(after = "button")
	public void button2(Setting setting) {
		OperatingSystem.getPlatform().openUri(URL_ADDON);
	}


	public ConfigProperty<String> name() {
		return this.name;
	}

	public ConfigProperty<Boolean> renderTag() {
		return this.renderTag;
	}

	public ConfigProperty<Boolean> renderFlags() {
		return this.renderFlags;
	}

	@Override
	public ConfigProperty<Boolean> enabled() {
		return this.enabled;
	}
}
