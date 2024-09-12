package com.funkeln.pronouns.nametag;

import com.funkeln.pronouns.PronounAddon;
import com.funkeln.pronouns.profile.Profile;
import com.funkeln.pronouns.profile.ProfileRepository;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
public class NameTagFlag extends NameTag {
	@Override
	protected @Nullable RenderableComponent getRenderableComponent() {
		if (
			 !(this.entity instanceof Player) ||
			 this.entity.isCrouching()
		) return null;

		if (ProfileRepository.find(this.entity.getUniqueId()).isEmpty()) return null;

		PronounAddon addon = PronounAddon.getInstance();

		if (!addon.configuration().enabled().get())
			return null;
		if (!addon.configuration().renderFlags().get())
			return null;

		Component component = Component.empty();
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
		x = 0;
		Icon[] flags = myFlags();
		if (flags == null) return;
		for (Icon flag : Arrays.stream(flags).filter(Objects::nonNull).toArray(Icon[]::new)) {
			flag.render(stack, x, +1, 15, this.getHeight() - 1);
			x += 15.5F;
		}
		super.renderText(stack, component, discrete, textColor, 0, this.getWidth(), y + 1);
	}

	@Override
	public float getScale() {
		return 0.65F;
	}

	@Override
	public float getWidth() {
		Icon[] flags = myFlags();
		if (flags == null)
			return super.getWidth();
		return super.getWidth() + 15 * flags.length;
	}

	@Override
	public float getHeight() {
		return super.getHeight() + 1;
	}

	private Icon[] myFlags() {
		Optional<Profile> profileFetch = ProfileRepository.find(this.entity.getUniqueId());
		if (profileFetch.isPresent()) {
			Profile profile = profileFetch.get();
			if (profile.flagsAvailable()) {
				return profile.flags();
			}
		}
		return null;
	}
}
