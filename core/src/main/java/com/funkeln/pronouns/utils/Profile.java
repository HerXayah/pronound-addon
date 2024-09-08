package com.funkeln.pronouns.utils;



/**
 * @author https://github.com/PrincessAkira (Sarah) Today is the 9/8/2024 @2:39 AM This project is
 * named labymod4-addon-template
 * @description Another day of Insanity
 */
public class Profile {

  private final String username;
  private final String pronoun;

  public Profile(String username, String pronoun) {
    this.username = username;
    this.pronoun = pronoun;
  }

  public String getUsername() { return username; }
  public String getPronoun() { return pronoun; }

}
