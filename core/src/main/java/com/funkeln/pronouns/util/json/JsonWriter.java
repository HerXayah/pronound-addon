package com.funkeln.pronouns.util.json;

import com.funkeln.pronouns.util.json.JsonObject.Member;

import java.io.IOException;
import java.io.Writer;

@SuppressWarnings("unused")
class JsonWriter {
	private static final int CONTROL_CHARACTERS_START = 0x0000;
	private static final int CONTROL_CHARACTERS_END = 0x001f;

	private static final char[] QUOT_CHARS = { '\\', '"' };
	private static final char[] BS_CHARS = { '\\', '\\' };
	private static final char[] LF_CHARS = { '\\', 'n' };
	private static final char[] CR_CHARS = { '\\', 'r' };
	private static final char[] TAB_CHARS = { '\\', 't' };
	private static final char[] UNICODE_2028_CHARS = { '\\', 'u', '2', '0', '2', '8' };
	private static final char[] UNICODE_2029_CHARS = { '\\', 'u', '2', '0', '2', '9' };
	private static final char[] HEX_DIGITS = {
		 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		 'a', 'b', 'c', 'd', 'e', 'f'
	};

	protected final Writer writer;

	JsonWriter(Writer writer) {
		this.writer = writer;
	}

	private static char[] getReplacementChars(char ch) {
		var chars = switch (ch) {
			case '\u0022' -> QUOT_CHARS;
			case '\\' -> BS_CHARS;
			case '\n' -> LF_CHARS;
			case '\r' -> CR_CHARS;
			case '\t' -> TAB_CHARS;
			case '\u2028' -> UNICODE_2028_CHARS;
			case '\u2029' -> UNICODE_2029_CHARS;
			default -> null;
		};
		if (chars == null && ch <= CONTROL_CHARACTERS_END) return new char[]{
			 '\\', 'u', '0', '0', HEX_DIGITS[ch >> 4 & 0x000f], HEX_DIGITS[ch & 0x000f]
		};
		return chars;
	}

	void write(String string) throws IOException {
		writer.write(string);
	}

	void writeString(String string) throws IOException {
		writer.write('"');
		int length = string.length();
		int start = 0;
		char[] chars = new char[length];
		string.getChars(0, length, chars, 0);
		for (int index = 0 ; index < length ; index++) {
			char[] replacement = getReplacementChars(chars[index]);
			if (replacement != null) {
				writer.write(chars, start, index - start);
				writer.write(replacement);
				start = index + 1;
			}
		}
		writer.write(chars, start, length - start);
		writer.write('"');
	}

	protected void writeObject(JsonObject object) throws IOException {
		writeBeginObject();
		boolean first = true;
		for (Member member : object) {
			if (!first) {
				writeObjectValueSeparator();
			}
			writeString(member.getName());
			writeNameValueSeparator();
			member.getValue().write(this);
			first = false;
		}
		writeEndObject();
	}

	protected void writeBeginObject() throws IOException {
		writer.write('{');
	}

	protected void writeEndObject() throws IOException {
		writer.write('}');
	}

	protected void writeNameValueSeparator() throws IOException {
		writer.write(':');
	}

	protected void writeObjectValueSeparator() throws IOException {
		writer.write(',');
	}

	protected void writeArray(JsonArray array) throws IOException {
		writeBeginArray();
		boolean first = true;
		for (JsonValue value : array) {
			if (!first) {
				writeArrayValueSeparator();
			}
			value.write(this);
			first = false;
		}
		writeEndArray();
	}

	protected void writeBeginArray() throws IOException {
		writer.write('[');
	}

	protected void writeEndArray() throws IOException {
		writer.write(']');
	}

	protected void writeArrayValueSeparator() throws IOException {
		writer.write(',');
	}

}
