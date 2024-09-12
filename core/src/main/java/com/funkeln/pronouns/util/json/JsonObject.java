package com.funkeln.pronouns.util.json;

import com.funkeln.pronouns.util.ArraySlice;
import com.funkeln.pronouns.util.json.JsonObject.Member;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.Serial;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JsonObject extends JsonValue implements Iterable<Member> {
	private String[] names;
	private JsonValue[] values;
	private transient HashIndexTable table;

	public JsonObject() {
		names = new String[0];
		values = new JsonValue[0];
		table = new HashIndexTable();
	}

	private JsonObject(JsonObject object) {
		if (object == null) {
			throw new NullPointerException("object is null");
		}
		this.names = object.names.clone();
		this.values = object.values.clone();
		this.table = new HashIndexTable();
		this.updateHashIndex();
	}

	public static JsonObject readFrom(Reader reader) throws IOException {
		return JsonValue.readFrom(reader).asObject();
	}

	public static JsonObject readFrom(String string) {
		return JsonValue.readFrom(string).asObject();
	}

	@Override public String toString() {
		return new StringJoiner(", ", JsonObject.class.getSimpleName() + "[", "]")
			 .add("names=" + Arrays.toString(names))
			 .add("values=" + Arrays.toString(values))
			 .add("table=" + table)
			 .toString();
	}

	public JsonObject add(String name, int value) {
		add(name, valueOf(value));
		return this;
	}

	public JsonObject add(String name, long value) {
		add(name, valueOf(value));
		return this;
	}

	public JsonObject add(String name, float value) {
		add(name, valueOf(value));
		return this;
	}

	public JsonObject add(String name, double value) {
		add(name, valueOf(value));
		return this;
	}

	public JsonObject add(String name, boolean value) {
		add(name, valueOf(value));
		return this;
	}

	public JsonObject add(String name, String value) {
		add(name, valueOf(value));
		return this;
	}

	public JsonObject add(String name, JsonValue value) {
		if (name == null) {
			throw new NullPointerException("name is null");
		}
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		table.add(name, names.length);
		this.names = ArraySlice.add(String[]::new, this.names, name);
		this.values = ArraySlice.add(JsonValue[]::new, this.values, value);
		return this;
	}

	public JsonObject set(String name, int value) {
		set(name, valueOf(value));
		return this;
	}

	public JsonObject set(String name, long value) {
		set(name, valueOf(value));
		return this;
	}

	public JsonObject set(String name, float value) {
		set(name, valueOf(value));
		return this;
	}

	public JsonObject set(String name, double value) {
		set(name, valueOf(value));
		return this;
	}

	public JsonObject set(String name, boolean value) {
		set(name, valueOf(value));
		return this;
	}

	public JsonObject set(String name, String value) {
		set(name, valueOf(value));
		return this;
	}

	public JsonObject set(String name, JsonValue value) {
		if (name == null) {
			throw new NullPointerException("name is null");
		}
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		int index = indexOf(name);
		if (index != -1) {
			this.values = ArraySlice.addAt(
				 JsonValue[]::new,
				 ArraySlice.removeAt(JsonValue[]::new, this.values, index),
				 value,
				 index - 1
			);
		} else {
			table.add(name, names.length);
			this.names = ArraySlice.add(String[]::new, this.names, name);
			this.values = ArraySlice.add(JsonValue[]::new, this.values, value);
		}
		return this;
	}

	public JsonObject remove(String name) {
		if (name == null) {
			throw new NullPointerException("name is null");
		}
		int index = indexOf(name);
		if (index != -1) {
			table.remove(index);
			this.names = ArraySlice.removeAt(String[]::new, this.names, index);
			this.values = ArraySlice.removeAt(JsonValue[]::new, this.values, index);
		}
		return this;
	}

	public JsonValue get(@NotNull String name) {
		int index = indexOf(name);
		return index != -1 ? values[index] : null;
	}

	public boolean has(@NotNull String name) {
		return this.get(name) != null;
	}

	public int size() {
		return names.length;
	}

	public boolean isEmpty() {
		return names.length == 0;
	}

	public <L extends List<String>> L asListNames(Supplier<L> factory) {
		return ArraySlice.asList(factory, this.names);
	}

	public <L extends List<JsonValue>> L asListValues(Supplier<L> factory) {
		return ArraySlice.asList(factory, this.values);
	}

	public @NotNull Iterator<Member> iterator() {
		return new Iterator<>() {
			private String[] names = JsonObject.this.names.clone();
			private JsonValue[] values = JsonObject.this.values.clone();

			public boolean hasNext() {
				return values.length != 0;
			}

			public Member next() {
				String name = this.names[this.names.length - 1];
				this.names = ArraySlice.remove(String[]::new, this.names, name);

				JsonValue value = this.values[this.values.length - 1];
				this.values = ArraySlice.remove(JsonValue[]::new, this.values, value);

				return new Member(name, value);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	protected void write(JsonWriter writer) throws IOException {
		writer.writeObject(this);
	}

	@Override
	public boolean isObject() {
		return true;
	}

	@Override
	public JsonObject asObject() {
		return this;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + Arrays.hashCode(names);
		result = 31 * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JsonObject other = (JsonObject) obj;
		return Arrays.equals(names, other.names) && Arrays.equals(values, other.values);
	}

	int indexOf(String name) {
		int index = table.get(name);
		return index != -1 && name.equals(names[index]) ? index : ArraySlice.indexOf(this.names, name);
	}

	@Serial
	private synchronized void readObject(
		 ObjectInputStream inputStream
	) throws IOException, ClassNotFoundException {
		inputStream.defaultReadObject();
		table = new HashIndexTable();
		updateHashIndex();
	}

	private void updateHashIndex() {
		for (int i = 0 ; i < names.length ; i++) {
			table.add(names[i], i);
		}
	}

	public static class Member {
		private final String name;
		private final JsonValue value;

		Member(String name, JsonValue value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public JsonValue getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			int result = 1;
			result = 31 * result + name.hashCode();
			result = 31 * result + value.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object object) {
			Member other = (Member) object;
			return this == object || object != null && getClass() == object.getClass() && (
				 name.equals(other.name) && value.equals(other.value));
		}
	}

	static class HashIndexTable {
		private final byte[] hashTable = new byte[32]; // must be a power of two

		public HashIndexTable() {}

		public HashIndexTable(HashIndexTable original) {
			System.arraycopy(original.hashTable, 0, hashTable, 0, hashTable.length);
		}

		void add(String name, int index) {
			hashTable[hashSlotFor(name)] = index < 0xff ? (byte) (index + 1) : 0;
		}

		void remove(int index) {
			for (int i = 0 ; i < hashTable.length ; i++) {
				if (hashTable[i] == index + 1) {
					hashTable[i] = 0;
				} else if (hashTable[i] > index + 1) {
					hashTable[i]--;
				}
			}
		}

		int get(Object name) {
			return (hashTable[hashSlotFor(name)] & 0xff) - 1;
		}

		private int hashSlotFor(Object element) {
			return element.hashCode() & hashTable.length - 1;
		}
	}
}
