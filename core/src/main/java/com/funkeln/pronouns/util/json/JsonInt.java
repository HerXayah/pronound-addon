package com.funkeln.pronouns.util.json;

import java.io.IOException;

@SuppressWarnings("unused")
class JsonInt extends JsonValue {
	private final int value;

	JsonInt(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	protected void write(JsonWriter writer) throws IOException {
		writer.write(toString());
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public int asInt() {
		return value;
	}

	@Override
	public long asLong() {
		return value;
	}

	@Override
	public float asFloat() {
		return (float) asDouble();
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public boolean equals(Object object) {
		return this == object || (object != null && getClass() == object.getClass() && value == ((JsonInt) object).value);
	}
}
