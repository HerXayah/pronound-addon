package com.funkeln.pronouns.util.json;

import java.io.IOException;

@SuppressWarnings("unused")
class JsonString extends JsonValue {
	private final String string;

	JsonString(String string) {
		if (string == null)
			throw new NullPointerException("string is null");
		this.string = string;
	}

	@Override
	protected void write(JsonWriter writer) throws IOException {
		writer.writeString(string);
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public String asString() {
		return string;
	}

	@Override
	public int hashCode() {
		return string.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		return string.equals(((JsonString) object).string);
	}
}
