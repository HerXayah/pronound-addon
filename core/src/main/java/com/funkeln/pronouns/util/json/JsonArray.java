package com.funkeln.pronouns.util.json;

import com.funkeln.pronouns.util.ArraySlice;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JsonArray extends JsonValue implements Iterable<JsonValue> {
	private JsonValue[] values;

	public JsonArray() {
		this.values = new JsonValue[0];
	}

	private JsonArray(@NotNull JsonArray array) {
		this.values = array.values;
	}

	public static JsonArray readFrom(Reader reader) throws IOException {
		return JsonValue.readFrom(reader).asArray();
	}

	public static JsonArray readFrom(String string) {
		return JsonValue.readFrom(string).asArray();
	}

	public JsonArray add(int value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, valueOf(value));
		return this;
	}

	public JsonArray add(long value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, valueOf(value));
		return this;
	}

	public JsonArray add(float value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, valueOf(value));
		return this;
	}

	public JsonArray add(double value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, valueOf(value));
		return this;
	}

	public JsonArray add(boolean value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, valueOf(value));
		return this;
	}

	public JsonArray add(String value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, valueOf(value));
		return this;
	}

	public JsonArray add(@NotNull JsonValue value) {
		this.values = ArraySlice.add(JsonValue[]::new, this.values, value);
		return this;
	}

	public JsonArray set(int index, int value) {
		this.values = ArraySlice.addAt(JsonValue[]::new, ArraySlice.removeAt(JsonValue[]::new, this.values, index),
		                               valueOf(value), index - 1
		);
		return this;
	}

	public JsonArray set(int index, long value) {
		this.values = ArraySlice.addAt(JsonValue[]::new, ArraySlice.removeAt(JsonValue[]::new, this.values, index),
		                               valueOf(value), index - 1
		);
		return this;
	}

	public JsonArray set(int index, float value) {
		this.values = ArraySlice.addAt(
			 JsonValue[]::new,
			 ArraySlice.removeAt(JsonValue[]::new, this.values, index),
			 valueOf(value), index - 1
		);
		return this;
	}

	public JsonArray set(int index, double value) {
		this.values = ArraySlice.addAt(
			 JsonValue[]::new,
			 ArraySlice.removeAt(JsonValue[]::new, this.values, index),
			 valueOf(value), index - 1
		);
		return this;
	}

	public JsonArray set(int index, boolean value) {
		this.values = ArraySlice.addAt(
			 JsonValue[]::new,
			 ArraySlice.removeAt(JsonValue[]::new, this.values, index),
			 valueOf(value), index - 1
		);
		return this;
	}

	public JsonArray set(int index, String value) {
		this.values = ArraySlice.addAt(
			 JsonValue[]::new,
			 ArraySlice.removeAt(JsonValue[]::new, this.values, index),
			 valueOf(value), index - 1
		);
		return this;
	}

	public JsonArray set(int index, JsonValue value) {
		this.values = ArraySlice.addAt(
			 JsonValue[]::new,
			 ArraySlice.removeAt(JsonValue[]::new, this.values, index),
			 value, index - 1
		);
		return this;
	}

	public JsonArray remove(int index) {
		this.values = ArraySlice.removeAt(JsonValue[]::new, this.values, index);
		return this;
	}

	public int size() {
		return this.values.length;
	}

	public boolean isEmpty() {
		return this.values.length == 0;
	}

	public JsonValue get(int index) {
		return this.values[index];
	}

	public <L extends List<JsonValue>> L asList(Supplier<L> factory) {
		return ArraySlice.asList(factory, this.values);
	}

	public Iterator<JsonValue> iterator() {
		return new Iterator<>() {
			private JsonValue[] values = JsonArray.this.values.clone();

			public boolean hasNext() {
				return values.length != 0;
			}

			public JsonValue next() {
				JsonValue value = values[values.length - 1];
				this.values = ArraySlice.remove(JsonValue[]::new, this.values, value);
				return value;
			}
		};
	}

	@Override
	protected void write(JsonWriter writer) throws IOException {
		writer.writeArray(this);
	}

	@Override
	public boolean isArray() {
		return true;
	}

	@Override
	public JsonArray asArray() {
		return this;
	}

	@Override
	public int hashCode() {
		return values.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		return this == object || (object != null && getClass() == object.getClass() && Arrays.equals(values, ((JsonArray) object).values));
	}

	@Override
	public JsonArray clone() throws CloneNotSupportedException {
		return (JsonArray) super.clone();
	}
}
