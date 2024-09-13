package com.funkeln.pronouns;

import com.funkeln.pronouns.event.BroadcastEventListener;
import com.funkeln.pronouns.event.ServerJoinListener;
import com.funkeln.pronouns.interaction.FlagOutput;
import com.funkeln.pronouns.interaction.UserInteraction;
import com.funkeln.pronouns.nametag.FlagNameTag;
import com.funkeln.pronouns.profile.ProfileRepository;
import com.google.gson.JsonObject;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.funkeln.pronouns.nametag.PronounNameTag;

@AddonMain
public class PronounAddon extends LabyAddon<PronounConfiguration> {
  private static final Log log = LogFactory.getLog(PronounAddon.class);
  public static PronounAddon INSTANCE;
  public String pronoun;
  public volatile Component component;
  public String meow;

  public PronounAddon() {
    INSTANCE = this;
  }

  public static PronounAddon getInstance() {
    return INSTANCE;
  }

  @Override
  protected void enable() {
    INSTANCE = this;
    this.registerSettingCategory();
    if(this.configuration().enabled().get()) {
      labyAPI().eventBus().registerListener(new BroadcastEventListener());
      labyAPI().taskExecutor().getScheduledPool().scheduleAtFixedRate(
          () -> {
            publishNameUpdate();
            ProfileRepository.updateProfiles(false);
          },
          30000,
          30000,
          java.util.concurrent.TimeUnit.MILLISECONDS
      );
      labyAPI().eventBus().registerListener(new ServerJoinListener(this));
    }
    this.logger().info("Enabled the Addon");
    this.labyAPI().interactionMenuRegistry().register(new UserInteraction(
        this
    ));
    this.labyAPI().interactionMenuRegistry().register(new FlagOutput(
        this
    ));
    this.labyAPI().tagRegistry().register(
      "pronouns",
      PositionType.BELOW_NAME,
      new PronounNameTag(
        Laby.references().renderPipeline(),
        Laby.references().renderPipeline().rectangleRenderer()
      )
    );
    this.labyAPI().tagRegistry().register(
      "pronouns_flags",
      PositionType.ABOVE_NAME,
      new FlagNameTag(
        Laby.references().renderPipeline(),
        Laby.references().renderPipeline().rectangleRenderer()
      )
    );
    this.configuration().name().addChangeListener(this::publishNameUpdate);
  }

  public void publishNameUpdate() {
    String newName = configuration().name().get();
    logger().info("Publishing pronoun name change to " + newName);
    JsonObject data = new JsonObject();
    data.addProperty("name", newName);
    labyAPI().labyConnect().getSession().sendBroadcastPayload("pronouns", data);
    ProfileRepository.clearExpired();
  }

  @Override
  protected Class<PronounConfiguration> configurationClass() {
    return PronounConfiguration.class;
  }
}
