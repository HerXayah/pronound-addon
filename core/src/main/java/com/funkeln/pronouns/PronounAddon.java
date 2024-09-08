package com.funkeln.pronouns;

import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.funkeln.pronouns.nametag.PronounNameTag;
import com.funkeln.pronouns.utils.Profile;
import com.funkeln.pronouns.utils.PronounsAPI;
import com.funkeln.pronouns.utils.PronounsAPI.ProfileFetchListener;

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
    this.registerSettingCategory();
    if(this.configuration().enabled().get()) {
      if(configuration().name().get().isEmpty()) {
        meow = labyAPI().minecraft().getClientPlayer().getName();
      } else {
        meow = configuration().name().get().trim();
      }
      displayMessage(meow);
      PronounsAPI.addProfileFetchListener(meow, new ProfileFetchListener() {
        @Override
        public void onProfileFetched(Profile profile) {
          pronoun = profile.getPronoun().trim();
        }

        @Override
        public void onProfileFetchFailed(String username, Exception e) {
          log.error("Failed to fetch profile for " + username, e);
          e.printStackTrace();
        }
      });

      // Request the profile
      // if name is empty, set it to the ingame user
      PronounsAPI.getProfile(meow);

      // Wait to ensure the async task completes (for demonstration purposes)
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    this.logger().info("Enabled the Addon");
    this.labyAPI().tagRegistry().register(
        "pronouns",
        PositionType.BELOW_NAME,
        new PronounNameTag(
            Laby.references().renderPipeline(),
            Laby.references().renderPipeline().rectangleRenderer()
        )
    );
  }

  public void prepareComponent(Profile profile) {
    component = Component.text(profile.getPronoun());
    logger().info("ara ara: " + component.toString());
  }


  @Override
  protected Class<PronounConfiguration> configurationClass() {
    return PronounConfiguration.class;
  }
}
