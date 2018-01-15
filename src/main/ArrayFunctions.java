package main;

public class ArrayFunctions {
	public static int min (double[] array) {
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

	public static double max (double[] array) {
		double largestElement = array[0];

		for (double element : array) {
			largestElement = Math.min(element, largestElement);
		}

		return largestElement;
	}
}
