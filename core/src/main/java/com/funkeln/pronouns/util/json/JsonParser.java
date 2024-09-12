package com.funkeln.pronouns.util.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class JsonParser {
	public static final ConcurrentHashMap<JsonStringCached.Key, JsonStringCached> CACHED_JSON_NAMES = new ConcurrentHashMap<JsonStringCached.Key, JsonStringCached>();
	private static final int BUFFER_SIZE_MIN = 10;
	private static final int BUFFER_SIZE_DEFAULT = 4096;
	private static final int MAX_CACHE_LENGTH_NAMES = Integer.MAX_VALUE;
	private static final int MAX_CACHE_LENGTH_VALUES = Integer.MAX_VALUE;
	private final JsonStringCached.KeyFromBuffer reusedCachedJsonStringKey;
	public HashMap<JsonStringCached.Key, JsonStringCached> cachedJsonValues = new HashMap<JsonStringCached.Key, JsonStringCached>();
	private Reader reader;
	private char[] buffer;
	private int bufferOffset;
	private int index;
	private int fill;
	private int line;
	private int lineOffset;
	private int current;
	private StringBuilder captureStringBuffer;
	private long captureNumberBuffer;
	private byte fractionCount;
	private int captureStart;
	private int savedStringAllocation;
	private int createdCachedItems;

	public JsonParser(String string) {
		this(
			 new StringReader(string),
			 Math.max(BUFFER_SIZE_MIN, Math.min(BUFFER_SIZE_DEFAULT, string.length()))
		);
	}

	JsonParser(Reader reader) {
		this(reader, BUFFER_SIZE_DEFAULT);
	}

	JsonParser(Reader reader, int buffersize) {
		reusedCachedJsonStringKey = new JsonStringCached.KeyFromBuffer();
		init(reader);
	}

	public void init(Reader reader, int bufferSize) {
		this.reader = reader;

		bufferOffset = 0;
		index = 0;
		fill = 0;
		lineOffset = 0;
		current = 0;
		captureStringBuffer = null;

		line = 1;
		captureStart = -1;

		int requestedBufferSize = Math.max(BUFFER_SIZE_MIN, Math.min(BUFFER_SIZE_DEFAULT, bufferSize));
		if (buffer == null || buffer.length < requestedBufferSize) {
			buffer = new char[requestedBufferSize];
		}
	}

	public void init(Reader reader) {
		init(reader, BUFFER_SIZE_DEFAULT);
	}

	public JsonValue parse() throws IOException {
		savedStringAllocation = 0;
		createdCachedItems = 0;

		read();
		skipWhiteSpace();
		JsonValue result = readValue();
		skipWhiteSpace();
		if (!isEndOfText()) {
			throw error("Unexpected character");
		}

		cachedJsonValues.clear();

		return result;
	}

	private JsonValue readValue() throws IOException {
		return switch (current) {
			case 'n' -> readNull();
			case 't' -> readTrue();
			case 'f' -> readFalse();
			case '"' -> readString();
			case '[' -> readArray();
			case '{' -> readObject();
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> readNumber();
			default -> throw expected("default");
		};
	}

	private JsonArray readArray() throws IOException {
		read();
		JsonArray array = new JsonArray();
		skipWhiteSpace();
		if (readChar(']'))
			return array;
		do {
			skipWhiteSpace();
			array.add(readValue());
			skipWhiteSpace();
		} while (readChar(','));
		if (!readChar(']'))
			throw expected("',' or ']'");
		return array;
	}

	private JsonObject readObject() throws IOException {
		read();
		JsonObject object = new JsonObject();
		skipWhiteSpace();
		if (readChar('}')) {
			return object;
		}
		do {
			skipWhiteSpace();
			String name = readName();
			skipWhiteSpace();
			if (!readChar(':')) {
				throw expected("':'");
			}
			skipWhiteSpace();
			object.add(name, readValue());
			skipWhiteSpace();
		} while (readChar(','));
		if (!readChar('}')) {
			throw expected("',' or '}'");
		}
		return object;
	}

	private String readName() throws IOException {
		if (current != '"') {
			throw expected("name");
		}
		return readStringInternal(CACHED_JSON_NAMES, MAX_CACHE_LENGTH_NAMES).toString();
	}

	private JsonValue readNull() throws IOException {
		read();
		readRequiredChar('u');
		readRequiredChar('l');
		readRequiredChar('l');
		return JsonValue.NULL;
	}

	private JsonValue readTrue() throws IOException {
		read();
		readRequiredChar('r');
		readRequiredChar('u');
		readRequiredChar('e');
		return JsonValue.TRUE;
	}

	private JsonValue readFalse() throws IOException {
		read();
		readRequiredChar('a');
		readRequiredChar('l');
		readRequiredChar('s');
		readRequiredChar('e');
		return JsonValue.FALSE;
	}

	private void readRequiredChar(char ch) throws IOException {
		if (!readChar(ch)) {
			throw expected("'" + ch + "'");
		}
	}

	private JsonValue readString() throws IOException {
		return readStringInternal(cachedJsonValues, MAX_CACHE_LENGTH_VALUES).asJsonString();
	}

	private JsonStringCached readStringInternal(
		 Map<JsonStringCached.Key, JsonStringCached> cache,
		 int maxStringCacheLength
	) throws IOException {
		read();
		startStringCapture();
		while (current != '"') {
			if (current == '\\') {
				pauseStringCapture();
				readEscape();
				startStringCapture();
			} else if (current < 0x20) {
				throw expected("valid string character");
			} else {
				read();
			}
		}
		JsonStringCached cachedJsonString = endStringCapture(cache, maxStringCacheLength);

		read();
		return cachedJsonString;
	}

	private void readEscape() throws IOException {
		read();
		switch (current) {
			case '"', '/', '\\' -> captureStringBuffer.append((char) current);
			case 'b' -> captureStringBuffer.append('\b');
			case 'f' -> captureStringBuffer.append('\f');
			case 'n' -> captureStringBuffer.append('\n');
			case 'r' -> captureStringBuffer.append('\r');
			case 't' -> captureStringBuffer.append('\t');
			case 'u' -> {
				char[] hexChars = new char[4];
				for (int i = 0 ; i < 4 ; i++) {
					read();
					if (!isHexDigit())
						throw expected("hexadecimal digit");
					hexChars[i] = (char) current;
				}
				captureStringBuffer.append((char) Integer.parseInt(String.valueOf(hexChars), 16));
			}
			default -> throw expected("valid escape sequence");
		}
		read();
	}

	private JsonValue readNumber() throws IOException {
		boolean isPositive = readSign();
		long integerPart = readInteger();
		long fractionPart = readFraction();
		byte capturedFractionCountBuffer = fractionCount;
		short exponentPart = readExponent();

		return (capturedFractionCountBuffer == 0 &&
		        exponentPart == 0 &&
		        integerPart > Integer.MIN_VALUE &&
		        integerPart < Integer.MAX_VALUE) ? new JsonInt((int) (isPositive ? integerPart : -1 * integerPart)) :
		       new OptimizedJsonNumber(
			        integerPart,
			        fractionPart,
			        capturedFractionCountBuffer,
			        exponentPart,
			        isPositive
		       );
	}

	private boolean readSign() throws IOException {
		boolean negative = readChar('-');
		if (!negative)
			readChar('+');
		return !negative;
	}

	private long readInteger() throws IOException {
		captureNumberBuffer = 0L;
		if (!readCaptureDigit())
			throw expected("digit");
		while (readCaptureDigit()) {
		}
		return captureNumberBuffer;
	}

	private long readFraction() throws IOException {
		captureNumberBuffer = 0L;
		fractionCount = 0;
		if (!readChar('.'))
			return -1;
		if (!readCaptureDigit())
			throw expected("digit");
		while (readCaptureDigit()) {
		}
		return captureNumberBuffer;
	}

	private short readExponent() throws IOException {
		if (!readChar('e') && !readChar('E')) {
			return 0;
		}
		boolean isExponentPositive = readSign();
		short exponent = (short) readInteger();
		return isExponentPositive ? exponent : (short) (-1 * exponent);
	}

	private boolean readChar(char ch) throws IOException {
		if (current == ch) {
			read();
			return true;
		}
		return false;
	}

	private boolean readDigit() throws IOException {
		if (isDigit()) {
			read();
			return true;
		}
		return false;
	}

	private void skipWhiteSpace() throws IOException {
		while (isWhiteSpace())
			read();
	}

	private void read() throws IOException {
		if (isEndOfText())
			throw error("Unexpected end of input");
		if (index == fill) {
			if (captureStart != -1) {
				captureStringBuffer.append(buffer, captureStart, fill - captureStart);
				captureStart = 0;
			}
			bufferOffset += fill;
			fill = reader.read(buffer, 0, buffer.length);
			index = 0;
			if (fill == -1) {
				current = -1;
				return;
			}
		}
		if (current == '\n') {
			line++;
			lineOffset = bufferOffset + index;
		}
		current = buffer[index++];
	}

	private void startStringCapture() {
		captureStringBuffer = captureStringBuffer == null ? new StringBuilder() : captureStringBuffer;
		captureStart = index - 1;
	}

	private void pauseStringCapture() {
		int end = current == -1 ? index : index - 1;
		captureStringBuffer.append(buffer, captureStart, end - captureStart);
		captureStart = -1;
	}

	private JsonStringCached endStringCapture(
		 Map<JsonStringCached.Key, JsonStringCached> cache,
		 int cacheStringIfLengthLowerOrEqualThan
	) {
		int end = current == -1 ? index : index - 1;
		JsonStringCached captured;

		boolean shouldCacheString = (end - captureStart) < cacheStringIfLengthLowerOrEqualThan;
		if (!captureStringBuffer.isEmpty()) {
			captureStringBuffer.append(buffer, captureStart, end - captureStart);
			String s = captureStringBuffer.toString();

			captured = new JsonStringCached(s);

			if (shouldCacheString) {
				cache.put(captured.createCachedJsonStringKey(), captured);
			}

			captureStringBuffer.setLength(0);
		} else {
			reusedCachedJsonStringKey.attach(buffer, captureStart, end - captureStart);

			JsonStringCached cachedJsonString = null;

			if (shouldCacheString) {
				cachedJsonString = cache.get(reusedCachedJsonStringKey);
			}

			if (cachedJsonString == null) {
				createdCachedItems++;
				cachedJsonString = reusedCachedJsonStringKey.toCachedJsonString();

				if (shouldCacheString) {
					cache.put(cachedJsonString.createCachedJsonStringKey(), cachedJsonString);
				}
			} else {
				savedStringAllocation++;
			}

			reusedCachedJsonStringKey.detach();

			captured = cachedJsonString;
		}
		captureStart = -1;
		return captured;
	}

	private ParseException expected(String expected) {
		if (isEndOfText()) {
			return error("Unexpected end of input");
		}
		return error("Expected " + expected);
	}

	private ParseException error(String message) {
		int absIndex = bufferOffset + index;
		int column = absIndex - lineOffset;
		int offset = isEndOfText() ? absIndex : absIndex - 1;
		return new ParseException(message, offset, line, column - 1);
	}

	private boolean isWhiteSpace() {
		return current == ' ' || current == '\t' || current == '\n' || current == '\r';
	}

	private boolean isDigit() {
		return current >= '0' && current <= '9';
	}

	private boolean isHexDigit() {
		return current >= '0' && current <= '9'
		       || current >= 'a' && current <= 'f'
		       || current >= 'A' && current <= 'F';
	}

	private boolean isEndOfText() {
		return current == -1;
	}

	private boolean readCaptureDigit() throws IOException {
		if (isDigit()) {
			captureNumberBuffer = captureNumberBuffer * 10 + Character.digit(current, 10);
			fractionCount++;
			read();
			return true;
		}
		return false;
	}

	public int getSavedStringAllocation() {
		return savedStringAllocation;
	}

	public void setSavedStringAllocation(int savedStringAllocation) {
		this.savedStringAllocation = savedStringAllocation;
	}

	public int getCreatedCachedItems() {
		return createdCachedItems;
	}

	public void setCreatedCachedItems(int createdCachedItems) {
		this.createdCachedItems = createdCachedItems;
	}
}
