package com.funkeln.pronouns.nametag;

import com.funkeln.pronouns.PronounAddon;
import com.funkeln.pronouns.profile.ProfileRepository;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import com.funkeln.pronouns.profile.Profile;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 8/16/2024 @7:26 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class PronounNameTag extends NameTag {
  private final RectangleRenderer rectangleRenderer;

  public PronounNameTag(RenderPipeline renderPipeline, RectangleRenderer rectangleRenderer) {
    RenderPipeline renderPipeline1 = Laby.references().renderPipeline();
    this.rectangleRenderer = renderPipeline1.rectangleRenderer();
  }

  @Override
  protected @Nullable RenderableComponent getRenderableComponent() {
    if (!(this.entity instanceof Player) || this.entity.isCrouching()) {
      return null;
    }

    PronounAddon addon = PronounAddon.getInstance();
    if (!addon.configuration().enabled().get()) {
      return null;
    }

    if (!addon.configuration().renderTag().get()) {
      return null;
    }

    String pronoun = myPronoun();
    if (pronoun != null) {
      HorizontalAlignment alignment = HorizontalAlignment.CENTER;

      Component component = Component.text(pronoun);
      if (component == null) {
        return null;
      }

      return RenderableComponent.of(component, alignment);
    } else {
      return null;
    }
  }

  @Override
  protected void renderText(
      Stack stack,
      RenderableComponent component,
      boolean discrete,
      int textColor,
      int backgroundColor,
      float x,
      float y
  ) {
    float width = this.getWidth();
    float height = this.getHeight();
    this.rectangleRenderer.renderRectangle(
        stack,
        x,
        y,
        width,
        height,
        backgroundColor
    );
    x += + 1;
    super.renderText(stack, component, discrete, textColor, 0, x, y + 1);
  }

  @Override
  public float getScale() {
    return 0.65F;
  }

  @Override
  public float getHeight() {
    return super.getHeight() + 1;
  }

  @Override
  public float getWidth() {
    if (myPronoun() == null) {
      return 0;
    }
    return super.getWidth();
  }

  private String myPronoun() {
    Optional<Profile> profileFetch = ProfileRepository.find(this.entity.getUniqueId());
    if (profileFetch.isPresent()) {
      Profile profile = profileFetch.get();
      if (profile.pronounsAvailable()) {
        return profile.pronoun();
      }
    }
    return null;
  }
}