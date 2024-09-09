package com.funkeln.pronouns.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/9/2024 @10:30 PM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class FlagResolver {
  private final static Map<String, Icon> cache = new HashMap<>();
  private static final String FLAGS_URL = "https://en.pronouns.page/flags/";

  // Name zu Icon
  public static void iconFromName(
    String name,
    Consumer<Icon> lazyReturn
  ) {
    if (name.contains("?") || name.contains("&") || name.contains("/") || name.contains(".")) {
      return;
    }
    if (cache.containsKey(name)) {
      lazyReturn.accept(cache.get(name));
    }
    Laby.labyAPI().taskExecutor().getPool().submit(() -> {
      Icon icon = Icon.url(FLAGS_URL + name + ".png");
      cache.put(name, icon);
      lazyReturn.accept(icon);
    });
  }

  // Namen zu Icons
  public static void iconsFromName(
    Consumer<Icon[]> lazyReturn,
    String... names
  ) {
    int size = names.length;
    AtomicLong remaining = new AtomicLong(size);
    Icon[] results = new Icon[size];

    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      int finalI = i;
      iconFromName(name, icon -> {
        results[finalI] = icon;
        if (remaining.decrementAndGet() == 0) {
          lazyReturn.accept(results);
        }
      });
    }
  }
  
  public static void iconsFromName(
    List<String> names,
    Consumer<Icon[]> lazyReturn
  ) {
    iconsFromName(lazyReturn, names.toArray(new String[0]));
  }
}
