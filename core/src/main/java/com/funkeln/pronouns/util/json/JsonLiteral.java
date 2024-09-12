package com.funkeln.pronouns.util.json;

import java.io.IOException;

@SuppressWarnings("unused")
class JsonLiteral extends JsonValue {
	private final String value;

	JsonLiteral(String value) {
		this.value = value;
	}

	@Override
	protected void write(JsonWriter writer) throws IOException {
		writer.write(value);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean asBoolean() {
		return isBoolean() ? isTrue() : super.asBoolean();
	}

	@Override
	public boolean isNull() {
		return this == NULL;
	}

	@Override
	public boolean isBoolean() {
		return this == TRUE || this == FALSE;
	}

	@Override
	public boolean isTrue() {
		return this == TRUE;
	}

	@Override
	public boolean isFalse() {
		return this == FALSE;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		return this == object || (object != null && getClass() == object.getClass() && value.equals(((JsonLiteral) object).value));
	}
}
