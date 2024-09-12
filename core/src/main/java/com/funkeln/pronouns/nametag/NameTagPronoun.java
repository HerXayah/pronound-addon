package com.funkeln.pronouns.nametag;

import com.funkeln.pronouns.PronounAddon;
import com.funkeln.pronouns.profile.Profile;
import com.funkeln.pronouns.profile.ProfileRepository;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
public class NameTagPronoun extends NameTag {

	private final RectangleRenderer rectangleRenderer;

	public NameTagPronoun() {
		this.rectangleRenderer = Laby.references().renderPipeline().rectangleRenderer();
	}

	@Override
	protected @Nullable RenderableComponent getRenderableComponent() {
		if (!(this.entity instanceof Player) || this.entity.isCrouching())
			return null;

		String pronoun = myPronoun();
		if (pronoun == null) return null;

		PronounAddon addon = PronounAddon.getInstance();
		if (!addon.configuration().enabled().get())
			return null;
		if (!addon.configuration().renderTag().get())
			return null;

		Component component = Component.text(pronoun);
		if (component == null)
			return null;

		return RenderableComponent.of(component, HorizontalAlignment.CENTER);
	}

	@Override
	protected void renderText(
		 Stack stack, RenderableComponent component, boolean discrete,
		 int textColor, int backgroundColor,
		 float x, float y
	) {
		if (this.getRenderableComponent() == null) return;
		this.rectangleRenderer.renderRectangle(
			 stack, x, y,
			 this.getWidth(), this.getHeight(),
			 backgroundColor
		);
		x++;
		super.renderText(stack, component, discrete, textColor, 0, x, y + 1);
	}

	@Override
	public float getScale() {
		return 0.65F;
	}

	@Override
	public float getWidth() {
		myPronoun();
		return super.getWidth();
	}

	@Override
	public float getHeight() {
		return super.getHeight() + 1;
	}


	private String myPronoun() {
		Optional<Profile> profile = ProfileRepository.find(this.entity.getUniqueId());
		return profile.isPresent() && profile.get().pronounsAvailable() ?
		       profile.get().pronoun() : null;
	}
}