package main;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Posterizer {
	private Color[] availableColors;

	public void setColors (Color[] userColors) {
		availableColors = userColors;
	}

	private Image startImage;

	public void setStartImage (Image image) {
		startImage = image;
	}

	public Image posterize () {
		PixelReader pixelReader = startImage.getPixelReader(); // make a pixelReader

		int width = (int) startImage.getWidth(); // get the width and height of the picture
		int height = (int) startImage.getHeight();

		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				/* find the distance to each available color and then set to the closest */
				Color currentColor = pixelReader.getColor(x, y);
				double red = currentColor.getRed();
				double green = currentColor.getGreen();
				double blue = currentColor.getBlue();

				double[] distances = new double[availableColors.length]; // distances to each color based on the index

				for (int i = 0; i < availableColors.length; i++) {
					double distance = Math.sqrt(
							Math.pow(availableColors[i].getRed() - red, 2) +
									Math.pow(availableColors[i].getGreen() - green, 2) +
									Math.pow(availableColors[i].getBlue() - blue, 2)
					); // pythagorean theorem in 3d

					distances[i] = distance; // set the distance of each color in the same order the colors came in
				}

				/* get the index of the smallest distance and then get the color from the availableColors array */
				int index = ArrayFunctions.min(distances);

				pixelWriter.setColor(x, y, availableColors[index]);
			}
		}

		return writableImage;
	}
}
