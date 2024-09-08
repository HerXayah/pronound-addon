package com.funkeln.pronouns.nametag;

import com.funkeln.pronouns.PronounAddon;
import com.funkeln.pronouns.utils.Profile;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.Nullable;

import static com.funkeln.pronouns.utils.Profile.flags;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/8/2024 @2:20 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class FlagNameTag extends NameTag {

  private final RectangleRenderer rectangleRenderer;

  public FlagNameTag(RenderPipeline renderPipeline, RectangleRenderer rectangleRenderer) {
    RenderPipeline renderPipeline1 = Laby.references().renderPipeline();
    this.rectangleRenderer = renderPipeline1.rectangleRenderer();
  }

  public FlagNameTag(RectangleRenderer rectangleRenderer) {
    this.rectangleRenderer = rectangleRenderer;
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

      if (!addon.configuration().renderFlags().get()) {
        return null;
      }

      Component component = Component.empty();
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
    x = 0;
    float width = this.getWidth();
    float height = this.getHeight();

    if (Profile.getFlags() != null) {
      float padding = 0.5f; // Space between flags
      int widtz = 15; // Define the width for each flag
      for (Icon flag : flags) {

        // Render flag at the updated x position
        flag.render(stack, x, +1, 15, height - 1);

        // Move the x position to the right for the next flag
        x += widtz + padding; // Increment x by the width of the flag and padding
      }
    }
    super.renderText(stack, component, discrete, textColor, 0, width, y + 1);
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

    return super.getWidth() + 15 * flags.length;
  }

  @Override
  public float getHeight() {
    return super.getHeight() + 1;
  }
}
