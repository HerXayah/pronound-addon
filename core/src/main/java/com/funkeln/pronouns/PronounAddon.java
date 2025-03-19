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
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.funkeln.pronouns.nametag.PronounNameTag;
import java.util.regex.Pattern;

@AddonMain
public class PronounAddon extends LabyAddon<PronounConfiguration> {

  public static PronounAddon INSTANCE;

  public PronounAddon() {
    INSTANCE = this;
  }

  public static PronounAddon getInstance() {
    return INSTANCE;
  }

  private static final Pattern validUsernamePattern = Pattern.compile("^[a-zA-Z0-9._-]{4,16}$");

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
    // check if name is just spaces
    if (newName == null || newName.trim().isEmpty()) {
      configuration().information().set("No Username set!");
      return;
    }
    if (!validUsernamePattern.matcher(newName).matches()) {
      configuration().information().set("Invalid Username");
      return;
    } else {
      configuration().information().set("Valid Username");
    }
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
