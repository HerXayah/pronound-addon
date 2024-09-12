package com.funkeln.pronouns.util.json;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@SuppressWarnings("unused")
class JsonNumber extends JsonValue {
	private final String string;

	JsonNumber(@NotNull String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	protected void write(JsonWriter writer) throws IOException {
		writer.write(string);
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public int asInt() {
		return Integer.parseInt(string, 10);
	}

	@Override
	public long asLong() {
		return Long.parseLong(string, 10);
	}

	@Override
	public float asFloat() {
		return Float.parseFloat(string);
	}

	@Override
	public double asDouble() {
		return Double.parseDouble(string);
	}

	@Override
	public int hashCode() {
		return string.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		return this == object || (object != null && getClass() == object.getClass() && string.equals(((JsonNumber) object).string));
	}
}
