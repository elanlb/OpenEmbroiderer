package main;

public class ArrayFunctions {
	public static int min (double[] array) throws ArrayIndexOutOfBoundsException {
		try {
			double smallestElement = array[0];
			int index = 0;

			for (int i = 0; i < array.length; i++) {
				if (array[i] < smallestElement) {
					smallestElement = array[i];
					index = i;
				}
			}
			return index;
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			return -1;
		}
	}

	public static double max (double[] array) throws ArrayIndexOutOfBoundsException {
		try {
			double largestElement = array[0];
			int index = 0;

			for (int i = 0; i < array.length; i++) {
				if (array[i] < largestElement) {
					largestElement = array[i];
					index = i;
				}
			}

			return index;
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			return -1;
		}
	}
}
