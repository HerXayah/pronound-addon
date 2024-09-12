package com.funkeln.pronouns.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import static java.lang.System.arraycopy;

/**
 * The array slicing class I made some while ago.
 *
 * @author Lucielle R. H. <a href=https://github.com/lunarydess/>~ lunarydess</a>.
 * @version 1.0.0
 * @apiNote This file is part of <a href=https://github.com/lunarydess/Library-Lilith-JVM/tree/develop>Lilith</a>.
 */
@SuppressWarnings("unused")
public final class ArraySlice {
	ArraySlice() {}

	public static boolean[] sliceFrom(
		 final boolean[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static boolean[] sliceBehind(
		 final boolean[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static boolean[] slice(
		 final boolean[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new boolean[0];
		}

		final var objs = new boolean[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static byte[] sliceFrom(
		 final byte[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static byte[] sliceBehind(
		 final byte[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static byte[] slice(
		 final byte[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new byte[0];
		}

		final var objs = new byte[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static char[] sliceFrom(
		 final char[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static char[] sliceBehind(
		 final char[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static char[] slice(
		 final char[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new char[0];
		}

		final var objs = new char[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static short[] sliceFrom(
		 final short[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static short[] sliceBehind(
		 final short[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static short[] slice(
		 final short[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new short[0];
		}

		final var objs = new short[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static int[] sliceFrom(
		 final int[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static int[] sliceBehind(
		 final int[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static int[] slice(
		 final int[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new int[0];
		}

		final var objs = new int[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static float[] sliceFrom(
		 final float[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static float[] sliceBehind(
		 final float[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static float[] slice(
		 final float[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new float[0];
		}

		final var objs = new float[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static double[] sliceFrom(
		 final double[] array,
		 final int start
	) {
		return slice(array, start, array.length - 1);
	}

	public static double[] sliceBehind(
		 final double[] array,
		 final int end
	) {
		return slice(array, 0, end);
	}

	public static double[] slice(
		 final double[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return new double[0];
		}

		final var objs = new double[array.length - (min + (array.length - 1 - max))];
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static <T> T[] sliceFrom(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final int start
	) {
		return slice(factory, array, start, array.length - 1);
	}

	public static <T> T[] sliceBehind(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final int end
	) {
		return slice(factory, array, 0, end);
	}

	public static <T> T[] slice(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final int start,
		 final int end
	) {
		final int min = Math.min(start, end), max = Math.max(start, end);
		if ((max - min) == 0) {
			return factory.apply(0);
		}

		final var objs = factory.apply(array.length - (min + (array.length - 1 - max)));
		arraycopy(array, min, objs, 0, objs.length);

		return objs;
	}

	public static boolean[] merge(
		 final boolean[] array1,
		 final boolean[] array2
	) {
		final var objs = new boolean[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static byte[] merge(
		 final byte[] array1,
		 final byte[] array2
	) {
		final var objs = new byte[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static char[] merge(
		 final char[] array1,
		 final char[] array2
	) {
		final var objs = new char[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static short[] merge(
		 final short[] array1,
		 final short[] array2
	) {
		final var objs = new short[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static int[] merge(
		 final int[] array1,
		 final int[] array2
	) {
		final var objs = new int[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static long[] merge(
		 final long[] array1,
		 final long[] array2
	) {
		final var objs = new long[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static float[] merge(
		 final float[] array1,
		 final float[] array2
	) {
		final var objs = new float[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static double[] merge(
		 final double[] array1,
		 final double[] array2
	) {
		final var objs = new double[array1.length + array2.length];

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static <T> T[] merge(
		 final IntFunction<T[]> factory,
		 final T[] array1,
		 final T[] array2
	) {
		final var objs = factory.apply(array1.length + array2.length);

		arraycopy(array1, 0, objs, 0, array1.length);
		arraycopy(array2, 0, objs, array1.length, array2.length);

		return objs;
	}

	public static boolean[] reverse(final boolean[] array) {
		final var objs = new boolean[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static byte[] reverse(final byte[] array) {
		final var objs = new byte[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static char[] reverse(final char[] array) {
		final var objs = new char[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static short[] reverse(final short[] array) {
		final var objs = new short[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static int[] reverse(final int[] array) {
		final var objs = new int[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static long[] reverse(final long[] array) {
		final var objs = new long[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static float[] reverse(final float[] array) {
		final var objs = new float[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static double[] reverse(final double[] array) {
		final var objs = new double[array.length];
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static <T> T[] reverse(
		 final IntFunction<T[]> factory,
		 final T[] array
	) {
		final var objs = factory.apply(array.length);
		int index = 0;
		for (int i = array.length - 1 ; i >= 0 ; i--) {
			objs[index++] = array[i];
		}
		return objs;
	}

	public static boolean[] add(
		 final boolean[] array,
		 final boolean adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static boolean[] addAt(
		 final boolean[] array,
		 final boolean adding,
		 final int index
	) {
		final var result = new boolean[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new boolean[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static byte[] add(
		 final byte[] array,
		 final byte adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static byte[] addAt(
		 final byte[] array,
		 final byte adding,
		 final int index
	) {
		final var result = new byte[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new byte[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static char[] add(
		 final char[] array,
		 final char adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static char[] addAt(
		 final char[] array,
		 final char adding,
		 final int index
	) {
		final var result = new char[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new char[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static short[] add(
		 final short[] array,
		 final short adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static short[] addAt(
		 final short[] array,
		 final short adding,
		 final int index
	) {
		final var result = new short[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new short[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static int[] add(
		 final int[] array,
		 final int adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static int[] addAt(
		 final int[] array,
		 final int adding,
		 final int index
	) {
		final var result = new int[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new int[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static long[] add(
		 final long[] array,
		 final long adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static long[] addAt(
		 final long[] array,
		 final long adding,
		 final int index
	) {
		final var result = new long[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new long[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static float[] add(
		 final float[] array,
		 final float adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static float[] addAt(
		 final float[] array,
		 final float adding,
		 final int index
	) {
		final var result = new float[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new float[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static double[] add(
		 final double[] array,
		 final double adding
	) {
		return addAt(array, adding, array.length - 1);
	}

	public static double[] addAt(
		 final double[] array,
		 final double adding,
		 final int index
	) {
		final var result = new double[array.length + 1];
		if (index < -1 || index > array.length - 1) {
			return new double[0];
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static <T> T[] add(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final T adding
	) {
		return addAt(factory, array, adding, array.length - 1);
	}

	public static <T> T[] addAt(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final T adding,
		 final int index
	) {
		final var result = factory.apply(array.length + 1);
		if (result.length == 0 || index < -1 || index > array.length - 1) {
			return factory.apply(0);
		}

		arraycopy(array, 0, result, 0, index + 1);

		final int addIndex = Math.max(index, 0);
		arraycopy(array, addIndex, result, addIndex + 1, array.length - addIndex);
		result[index + 1] = adding;

		return result;
	}

	public static boolean[] remove(
		 final boolean[] array,
		 final boolean removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static boolean[] removeAt(
		 final boolean[] array,
		 final int index
	) {
		final var result = new boolean[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new boolean[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static byte[] remove(
		 final byte[] array,
		 final byte removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static byte[] removeAt(
		 final byte[] array,
		 final int index
	) {
		final var result = new byte[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new byte[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static char[] remove(
		 final char[] array,
		 final char removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static char[] removeAt(
		 final char[] array,
		 final int index
	) {
		final var result = new char[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new char[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static short[] remove(
		 final short[] array,
		 final short removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static short[] removeAt(
		 final short[] array,
		 final int index
	) {
		final var result = new short[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new short[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static int[] remove(
		 final int[] array,
		 final int removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static int[] removeAt(
		 final int[] array,
		 final int index
	) {
		final var result = new int[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new int[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static long[] remove(
		 final long[] array,
		 final long removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static long[] removeAt(
		 final long[] array,
		 final int index
	) {
		final var result = new long[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new long[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static float[] remove(
		 final float[] array,
		 final float removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static float[] removeAt(
		 final float[] array,
		 final int index
	) {
		final var result = new float[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new float[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static double[] remove(
		 final double[] array,
		 final double removing
	) {
		return removeAt(array, indexOf(array, removing));
	}

	public static double[] removeAt(
		 final double[] array,
		 final int index
	) {
		final var result = new double[array.length - 1];
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return new double[0];
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static <T> T[] remove(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final T removing
	) {
		return removeAt(factory, array, indexOf(array, removing));
	}

	public static <T> T[] removeAt(
		 final IntFunction<T[]> factory,
		 final T[] array,
		 final int index
	) {
		var result = factory.apply(array.length - 1);
		if (result.length == 0 || index < 0 || index > array.length - 1) {
			return factory.apply(0);
		}

		arraycopy(array, 0, result, 0, index);
		arraycopy(array, index + 1, result, index, array.length - (index + 1));

		return result;
	}

	public static int indexOf(
		 final boolean[] array,
		 final boolean searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final byte[] array,
		 final byte searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final char[] array,
		 final char searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final short[] array,
		 final short searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final int[] array,
		 final int searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final long[] array,
		 final long searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final float[] array,
		 final float searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(
		 final double[] array,
		 final double searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == searching) {
				return i;
			}
		}
		return -1;
	}

	public static <T> int indexOf(
		 final T[] array,
		 final T searching
	) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i].equals(searching)) {
				return i;
			}
		}
		return -1;
	}

	public static int[] indicesOf(
		 final boolean[] array,
		 final boolean... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final byte[] array,
		 final byte... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final char[] array,
		 final char... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final short[] array,
		 final short... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final int[] array,
		 final int... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final long[] array,
		 final long... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final float[] array,
		 final float... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	public static int[] indicesOf(
		 final double[] array,
		 final double... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	@SafeVarargs
	public static <T> int[] indicesOf(
		 final T[] array,
		 final T... searching
	) {
		final int[] indices = new int[searching.length];

		for (int i = 0 ; i < searching.length ; i++) {
			indices[i] = indexOf(array, searching[i]);
		}

		return indices;
	}

	@SafeVarargs
	public static <T> String toString(final T... values) {
		return toString(T::toString, values);
	}

	@SafeVarargs
	public static <T> String toString(
		 final Function<T, String> stringFunc,
		 final T... values
	) {
		if (values.length == 0)
			return "[]";

		final StringBuilder returnVal = new StringBuilder(String.format("[%s", values[0]));
		if (values.length != 1) {
			for (int i = 1 ; i < values.length ; i++) {
				final T appending = values[i];
				returnVal.append(String.format(", %s", appending == null ? "null" : stringFunc.apply(appending)));
			}
		}

		return returnVal.append("]").toString();
	}

	@SafeVarargs
	public static <T, L extends List<T>> L asList(
		 final Supplier<L> factory,
		 final T[]... arrays
	) {
		final L list = factory.get();
		for (final T[] target : arrays)
			list.addAll(List.of(target));
		return list;
	}
}
