package colorMatcher;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ColorMatcher {
	public static Image matchColors (Image startImage, ArrayList<Color> availableColors) {

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
				double alpha = currentColor.getOpacity();

				double[] distances = new double[availableColors.size()]; // distances to each color based on the index

				for (int i = 0; i < availableColors.size(); i++) {
					double distance = Math.sqrt(
							Math.pow(availableColors.get(i).getRed() - red, 2) +
									Math.pow(availableColors.get(i).getGreen() - green, 2) +
									Math.pow(availableColors.get(i).getBlue() - blue, 2) +
									Math.pow(availableColors.get(i).getOpacity() - alpha, 2)
					); // pythagorean theorem in 3d

					distances[i] = distance; // set the distance of each color in the same order the colors came in
				}

				/* get the index of the smallest distance and then get the color from the availableColors array */
				int index = ArrayFunctions.min(distances);

				pixelWriter.setColor(x, y, availableColors.get(index));
			}
		}

		return writableImage;
	}
}
