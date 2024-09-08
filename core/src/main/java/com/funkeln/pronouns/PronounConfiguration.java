package com.funkeln.pronouns;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

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


  public ConfigProperty<String> name() {
    return this.name;
  }
}
