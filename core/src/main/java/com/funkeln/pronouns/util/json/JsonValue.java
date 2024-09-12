package com.funkeln.pronouns.util.json;

import java.io.*;

public abstract class JsonValue implements Serializable {
	public static final JsonValue TRUE = new JsonLiteral("true");

	public static final JsonValue FALSE = new JsonLiteral("false");

	public static final JsonValue NULL = new JsonLiteral("null");

	JsonValue() {}

	public static JsonValue readFrom(Reader reader) throws IOException {
		return new JsonParser(reader).parse();
	}

	public static JsonValue readFrom(String text) {
		try {
			return new JsonParser(text).parse();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static JsonValue valueOf(int value) {
		return new JsonNumber(Integer.toString(value, 10));
	}

	public static JsonValue valueOf(long value) {
		return new JsonNumber(Long.toString(value, 10));
	}

	public static JsonValue valueOf(float value) {
		if (Float.isInfinite(value) || Float.isNaN(value)) {
			throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
		}
		return new JsonNumber(cutOffPointZero(Float.toString(value)));
	}

	public static JsonValue valueOf(double value) {
		if (Double.isInfinite(value) || Double.isNaN(value)) {
			throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
		}
		return new JsonNumber(cutOffPointZero(Double.toString(value)));
	}

	public static JsonValue valueOf(String string) {
		return string == null ? NULL : new JsonString(string);
	}

	public static JsonValue valueOf(boolean value) {
		return value ? TRUE : FALSE;
	}

	private static String cutOffPointZero(String string) {
		if (string.endsWith(".0")) {
			return string.substring(0, string.length() - 2);
		}
		return string;
	}

	public boolean isObject() {
		return false;
	}

	public boolean isArray() {
		return false;
	}

	public boolean isNumber() {
		return false;
	}

	public boolean isString() {
		return false;
	}

	public boolean isBoolean() {
		return false;
	}

	public boolean isTrue() {
		return false;
	}

	public boolean isFalse() {
		return false;
	}

	public boolean isNull() {
		return false;
	}

	public JsonObject asObject() {
		throw new UnsupportedOperationException("Not an object: " + toString());
	}

	public JsonArray asArray() {
		throw new UnsupportedOperationException("Not an array: " + toString());
	}

	public int asInt() {
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public long asLong() {
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public float asFloat() {
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public double asDouble() {
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public String asString() {
		throw new UnsupportedOperationException("Not a string: " + toString());
	}

	public boolean asBoolean() {
		throw new UnsupportedOperationException("Not a boolean: " + toString());
	}

	public void writeTo(Writer writer) throws IOException {
		write(new JsonWriter(writer));
	}

	@Override
	public String toString() {
		StringWriter stringWriter = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(stringWriter);
		try {
			write(jsonWriter);
		} catch (IOException exception) {
			// StringWriter does not throw IOExceptions
			throw new RuntimeException(exception);
		}
		return stringWriter.toString();
	}

	@Override
	public boolean equals(Object object) {
		return super.equals(object);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	protected abstract void write(JsonWriter writer) throws IOException;

}
