package com.funkeln.pronouns.nametag;

import com.funkeln.pronouns.PronounAddon;
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
    if (!(this.entity instanceof Player) || this.entity.isCrouching() || !Laby.labyAPI().minecraft().getClientPlayer().getName().equals(((Player) this.entity).getName())) {
      return null;
    }

    HorizontalAlignment alignment;
    alignment = HorizontalAlignment.CENTER;


    PronounAddon addon = PronounAddon.getInstance();
    if (!addon.configuration().enabled().get()) {
      return null;
    }

    if (!addon.configuration().renderTag().get()) {
      return null;
    }

    Component component = Component.text(PronounAddon.getInstance().pronoun);
    if (component == null) {
      return null;
    }

    return RenderableComponent.of(component, alignment);
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
  public float getWidth() {
    if (Profile.getFlags() == null) {
      return super.getWidth();
    }

    return super.getWidth();
  }


  @Override
  public float getHeight() {
    return super.getHeight() + 1;
  }
}