package com.funkeln.pronouns.util.json;

import java.io.IOException;

@SuppressWarnings("unused")
class OptimizedJsonNumber extends JsonValue {

	private final boolean isPositive;
	private final long integerPart;
	private final long fractionPart;
	private final byte fractionScale;
	private final short exponentPart;

	OptimizedJsonNumber(
		 long integerPart, long fractionPart, byte fractionScale, short exponentPart,
		 boolean isPositive
	) {
		this.isPositive = isPositive;
		this.integerPart = isPositive ? integerPart : integerPart * -1;
		this.fractionPart = fractionPart;
		this.fractionScale = fractionScale;
		this.exponentPart = exponentPart;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!isPositive)
			sb.append('-');
		sb.append(Math.abs(integerPart));
		if (fractionScale > 0) {
			sb.append('.');
			String fractionStr = String.valueOf(fractionPart);
			sb.append("0".repeat(Math.max(0, fractionScale - fractionStr.length())));
			sb.append(fractionStr);
		}
		if (exponentPart != 0) {
			sb.append('e');
			sb.append(String.valueOf(exponentPart));
		}
		return sb.toString();
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
		return (int) asLong();
	}

	@Override
	public long asLong() {
		return (long) (exponentPart == 0 ? integerPart :
		               fractionScale > 0 ? fullDouble() :
		               integerPart * Math.pow(10, exponentPart));
	}

	@Override
	public float asFloat() {
		return (float) asDouble();
	}

	@Override
	public double asDouble() {
		if (exponentPart == 0) {
			if (fractionScale > 0) {
				if (isPositive) {
					return integerPart + fractionPart * Math.pow(10, -fractionScale);
				} else {
					return -1 * (Math.abs(integerPart) + fractionPart * Math.pow(10, -fractionScale));
				}
			} else {
				return integerPart;
			}
		}

		if (fractionScale > 0) {
			return fullDouble();
		}

		return integerPart * Math.pow(10, exponentPart);
	}

	private double fullDouble() {
		double v = fractionPart * Math.pow(10, exponentPart - fractionScale);
		return isPositive ? integerPart * Math.pow(10, exponentPart) + v : -1 * (Math.abs(integerPart) * Math.pow(10, exponentPart) + v);
	}

	@Override
	public int hashCode() {
		return 31 * (31 * (31 * (31 * super.hashCode() + exponentPart) + fractionScale) + Long.hashCode(fractionPart)) + Long.hashCode(integerPart);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		OptimizedJsonNumber that = (OptimizedJsonNumber) o;
		return exponentPart == that.exponentPart &&
		       fractionPart == that.fractionPart &&
		       fractionScale == that.fractionScale &&
		       integerPart == that.integerPart;
	}
}
