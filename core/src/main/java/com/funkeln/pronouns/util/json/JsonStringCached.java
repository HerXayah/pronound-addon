package com.funkeln.pronouns.util.json;

@SuppressWarnings("unused")
public class JsonStringCached {
	private final String wrappedString;
	private JsonString jsonString;

	public JsonStringCached(String wrappedString) {
		this.wrappedString = wrappedString;
	}

	public JsonString asJsonString() {
		if (jsonString == null)
			jsonString = new JsonString(wrappedString);
		return jsonString;
	}

	public Key createCachedJsonStringKey() {
		return new KeyFromString(wrappedString);
	}

	@Override
	public String toString() {
		return wrappedString;
	}

	public static abstract class Key {
		protected char[] charArray;
		protected int length;
		protected int cachedHashCodeFast;
		protected int startIdx;
		protected int cachedPureHashCode;

		protected abstract char charAt(int idx);

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;

			Key that = (Key) o;

			if (this.length != that.length)
				return false;

			if (this.pureHashCode() != that.pureHashCode()) {
				return false;
			}

			int thisIdx = this.startIdx;
			int thatIdx = that.startIdx;

			int lastChar = startIdx + length;

			while (thisIdx < lastChar) {
				if (this.charArray[thisIdx] != that.charArray[thatIdx]) {
					return false;
				}

				thisIdx++;
				thatIdx++;
			}

			return true;
		}

		@Override
		public int hashCode() {
			if (cachedHashCodeFast == 0) {
				if (length == 0) {
					cachedHashCodeFast = 1;
				} else {
					int secondCharIdx = length > 1 ? startIdx + 1 : startIdx;
					int lastCharIdx = startIdx + length - 1;

					cachedHashCodeFast = length << 24 | charArray[startIdx] << 16 | charArray[secondCharIdx] << 8
					                     | charArray[lastCharIdx];
				}
			}
			return cachedHashCodeFast;
		}

		public int pureHashCode() {
			int hash = cachedPureHashCode;
			if (hash == 0) {
				if (length == 0) {
					return 0;
				}
				final int end = startIdx + length;
				for (int i = startIdx ; i < end ; ++i) {
					hash = 31 * hash + charArray[i];
				}
				cachedPureHashCode = hash;
			}
			return cachedPureHashCode;
		}

	}

	public static class KeyFromString extends Key {
		private final String theString;

		public KeyFromString(String theString) {
			this.theString = theString;
			this.charArray = theString.toCharArray();
			this.length = theString.length();
		}

		protected char charAt(int idx) {
			return theString.charAt(idx);
		}

	}

	public static class KeyFromBuffer extends Key {
		public void attach(char[] charArray, int startIdx, int length) {
			this.charArray = charArray;
			this.startIdx = startIdx;
			this.length = length;
			this.cachedHashCodeFast = 0;
			this.cachedPureHashCode = 0;
		}

		public void detach() {
			this.charArray = null;
		}

		protected char charAt(int idx) {
			return charArray[idx + startIdx];
		}

		public JsonStringCached toCachedJsonString() {
			String s = new String(charArray, startIdx, length);
			return new JsonStringCached(s);
		}
	}
}
