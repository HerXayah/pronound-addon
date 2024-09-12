package com.funkeln.pronouns.util.json;

public class ParseException extends RuntimeException {

  private final int offset, line, column;

  ParseException(String message, int offset, int line, int column) {
    super(String.format("%s at %d:%d", message, line, column));
    this.offset = offset;
    this.line = line;
    this.column = column;
  }

  public int getOffset() {
    return offset;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }
}
