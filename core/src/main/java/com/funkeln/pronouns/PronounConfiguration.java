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

@ConfigName("settings")
public class PronounConfiguration extends AddonConfig {
  @SpriteSlot(size = 32, x = 1)
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> renderFlags = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> renderTag = new ConfigProperty<>(true);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> renderTag() {
    return this.renderTag;
  }

  public ConfigProperty<Boolean> renderFlags() {
    return this.renderFlags;
  }

  @TextFieldSetting
  private final ConfigProperty<String> name = new ConfigProperty<>("");

  @MethodOrder(after = "name")
  @ButtonSetting
  public void button(Setting setting) {
    OperatingSystem.getPlatform().openUri(String.format("https://pronouns.page"));
  }

  @MethodOrder(after = "button")
  @ButtonSetting
  public void button2(Setting setting) {
    OperatingSystem.getPlatform().openUri(String.format("https://github.com/HerXayah/pronound-addon/blob/master/README.md"));
  }


  public ConfigProperty<String> name() {
    return this.name;
  }
}
