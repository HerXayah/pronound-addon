package com.funkeln.pronouns.util.json;

import java.io.IOException;
import java.io.Reader;

@SuppressWarnings("unused")
public final class Json {
	public static final JsonValue NULL = new JsonLiteral("null");
	public static final JsonValue TRUE = new JsonLiteral("true");
	public static final JsonValue FALSE = new JsonLiteral("false");

	private Json() {}

	public static JsonValue value(int value) {
		return new JsonNumber(Integer.toString(value, 10));
	}

	public static JsonValue value(long value) {
		return new JsonNumber(Long.toString(value, 10));
	}

	public static JsonValue value(float value) {
		if (Float.isInfinite(value) || Float.isNaN(value)) {
			throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
		}
		return new JsonNumber(cutOffPointZero(Float.toString(value)));
	}

	public static JsonValue value(double value) {
		if (Double.isInfinite(value) || Double.isNaN(value)) {
			throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
		}
		return new JsonNumber(cutOffPointZero(Double.toString(value)));
	}

	public static JsonValue value(String string) {
		return string == null ? NULL : new JsonString(string);
	}

	public static JsonValue value(boolean value) {
		return value ? TRUE : FALSE;
	}

	public static JsonArray array() {
		return new JsonArray();
	}

	public static JsonArray array(int... values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		JsonArray array = new JsonArray();
		for (int value : values) {
			array.add(value);
		}
		return array;
	}

	public static JsonArray array(long... values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		JsonArray array = new JsonArray();
		for (long value : values) {
			array.add(value);
		}
		return array;
	}

	public static JsonArray array(float... values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		JsonArray array = new JsonArray();
		for (float value : values) {
			array.add(value);
		}
		return array;
	}

	public static JsonArray array(double... values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		JsonArray array = new JsonArray();
		for (double value : values) {
			array.add(value);
		}
		return array;
	}

	public static JsonArray array(boolean... values) {
		if (values == null) {
			throw new NullPointerException("values is null");
		}
		JsonArray array = new JsonArray();
		for (boolean value : values) {
			array.add(value);
		}
		return array;
	}

	public static JsonArray array(String... strings) {
		if (strings == null) {
			throw new NullPointerException("values is null");
		}
		JsonArray array = new JsonArray();
		for (String value : strings) {
			array.add(value);
		}
		return array;
	}

	public static JsonObject object() {
		return new JsonObject();
	}

	public static JsonValue parse(String string) {
		if (string == null) {
			throw new NullPointerException("string is null");
		}
		return JsonValue.readFrom(string);
	}

	public static JsonValue parse(Reader reader) throws IOException {
		if (reader == null) {
			throw new NullPointerException("reader is null");
		}
		return JsonValue.readFrom(reader);
	}

	private static String cutOffPointZero(String string) {
		if (string.endsWith(".0")) {
			return string.substring(0, string.length() - 2);
		}
		return string;
	}

}