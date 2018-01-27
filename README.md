# Color Matcher
JavaFX based program to reduce the colors in an image.

**How to Use**

A tutorial video will come soon, although it is very self explanatory. Select an image, choose the colors, and click the match colors button.

**Download**

The compiled JAR is available [here](https://github.com/elanlb/color-matcher/tree/master/out/artifacts/color_matcher/color_matcher.jar).

To run the program you need Java JDK ≥ 8 installed. Everything required for the program is included by default. Download Java JDK SE [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

**FAQ**

JAR file does not run or throws an error.

- Try running the file from terminal with `java -jar color_matcher.jar` to get more detailed error info.
- Reinstall JDK- the application is written in Java and needs Java to run!
- Check System Preferences or Windows Defender to check if it was blocked (it's not signed).

Program generates a blank image.

- A transparent color is included by default the first time the color selector window is opened. In the future there will be an option to disable transparency.

The application freezes or stops responding when generating the image.

- Because of the complexity of images and colors, it can take a while to finish the operation, depending on the size of the image and the amount of colors specified.

**Bugs and Feature Requests**

If there is an issue with the program or you would like to request a new feature, please make a new issue in the issue tracker.

**Contribute**

To compile the program yourself, you don't need anything except for the JDK and a Java IDE. If you would like to edit 
the style of the program, the CSS is in src/main/css. Refer to the JavaFX CSS documentation for tags and available modifiers.

I would appreciate it if someone would make .app and .exe executables (possibly bundled with Java) so that other users can install the application more easily.
