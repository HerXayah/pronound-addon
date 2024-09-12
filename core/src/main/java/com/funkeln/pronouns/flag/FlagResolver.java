package com.funkeln.pronouns.flag;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author <a href="https://github.com/PrincessAkira">PrincessAkira ~ Sarah</a>
 * @version 1.0.0
 * @apiNote another day of insanity
 * @since 9/9/2024 @10:21 PM
 */
public final class FlagResolver {
	private static final ConcurrentHashMap<String, Icon> CACHE = new ConcurrentHashMap<>();
	private static final String FLAGS_URL = "https://en.pronouns.page/flags/";

	FlagResolver() {}

	public static void iconFromName(
		 String name,
		 Consumer<Icon> lazyReturn
	) {
		if (name.contains("?") || name.contains("&") || name.contains("/") || name.contains("."))
			return;
		if (CACHE.containsKey(name))
			lazyReturn.accept(CACHE.get(name));
		Laby.labyAPI().taskExecutor().getPool().execute(() -> lazyReturn.accept(CACHE.put(
			 name, Icon.url("%s%s.png".formatted(FLAGS_URL, name.replaceAll(" +", "%20")))
		)));
	}

	public static void iconsFromName(
		 Consumer<Icon[]> lazyReturn,
		 String... names
	) {
		int size = names.length;
		AtomicLong remaining = new AtomicLong(size);
		Icon[] results = new Icon[size];

		for (int i = 0 ; i < names.length ; i++) {
			String name = names[i];
			int finalI = i;
			iconFromName(name, icon -> {
				results[finalI] = icon;
				if (remaining.decrementAndGet() == 0)
					lazyReturn.accept(results);
			});
		}
	}

	public static void iconsFromName(
		 Consumer<Icon[]> lazyReturn,
		 List<String> names
	) {
		iconsFromName(lazyReturn, names.toArray(new String[0]));
	}
}
