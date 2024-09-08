package com.funkeln.pronouns.utils;


import net.labymod.api.client.gui.icon.Icon;

/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/8/2024 @2:39 AM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class Profile {

  private final String username;
  private final String pronoun;

  public static volatile Icon[] flags;

  public Profile(String username, String pronoun, Icon[] flags) {
    this.username = username;
    this.pronoun = pronoun;
    this.flags = flags;
  }

  public String getUsername() { return username; }
  public String getPronoun() { return pronoun; }
  public static Icon[] getFlags() { return flags; }

}
