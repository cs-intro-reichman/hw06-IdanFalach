// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.

import java.awt.Color;

/**
 * A library of image processing functions.
 */
public class Runigram {

    public static void main(String[] args) {

        //// Hide / change / add to the testing code below, as needed.

        // Tests the reading and printing of an image:
        Color[][] tinypic = read("tinypic.ppm");
        print(tinypic);

        // Creates an image which will be the result of various
        // image processing operations:
        Color[][] imageOut;

        // Tests the horizontal flipping of an image:
        imageOut = flippedHorizontally(tinypic);
        System.out.println();
        print(imageOut);

        //// Write here whatever code you need in order to test your work.
        //// You can reuse / overide the contents of the imageOut array.

//		Color[][] verticalImage = flippedVertically(tinypic);
//		System.out.println();
//		print(verticalImage);

//		Color[][] greyScaledImage = grayScaled(tinypic);
//		System.out.println();
//		print(greyScaledImage);

//		Color[][] scaledImage = scaled(tinypic, 200, 150);
//		System.out.println();
//		print(scaledImage);

//        Color blendColor = blend(new Color(100, 40, 100), new Color(200, 20, 40), 0.25);
//        System.out.println();
//        System.out.println(blendColor.getRed() + ", " + blendColor.getGreen() + ", " + blendColor.getBlue());
    }

    /**
     * Returns a 2D array of Color values, representing the image data
     * stored in the given PPM file.
     */
    public static Color[][] read(String fileName) {
        In in = new In(fileName);
        // Reads the file header, ignoring the first and the third lines.
        in.readString();
        int numCols = in.readInt();
        int numRows = in.readInt();
        in.readInt();
        // Creates the image array
        Color[][] image = new Color[numRows][numCols];

        // Reads the RGB values from the file, into the image array.
        // For each pixel (i,j), reads 3 values from the file,
        // creates from the 3 colors a new Color object, and
        // makes pixel (i,j) refer to that object.
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int r = in.readInt();
                int g = in.readInt();
                int b = in.readInt();
                image[i][j] = new Color(r, g, b);
            }
        }
        return image;
    }

    // Prints the RGB values of a given color.
    private static void print(Color c) {
        System.out.print("(");
        System.out.printf("%3s,", c.getRed());   // Prints the red component
        System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s", c.getBlue());  // Prints the blue component
        System.out.print(")  ");
    }

    // Prints the pixels of the given image.
    // Each pixel is printed as a triplet of (r,g,b) values.
    // This function is used for debugging purposes.
    // For example, to check that some image processing function works correctly,
    // we can apply the function and then use this function to print the resulting image.
    private static void print(Color[][] image) {
        int rows = image.length;
        int columns = image[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                print(image[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Returns an image which is the horizontally flipped version of the given image.
     */
    public static Color[][] flippedHorizontally(Color[][] image) {
        int rows = image.length;
        int columns = image[0].length;
        Color[][] horizontalFlippedImage = new Color[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                horizontalFlippedImage[i][j] = image[i][columns - 1 - j];
            }
        }
        return horizontalFlippedImage;
    }

    /**
     * Returns an image which is the vertically flipped version of the given image.
     */
    public static Color[][] flippedVertically(Color[][] image) {
        int rows = image.length;
        int columns = image[0].length;
        Color[][] verticalFlippedImage = new Color[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                verticalFlippedImage[i][j] = image[rows - 1 - i][j];
            }
        }
        return verticalFlippedImage;
    }

    // Computes the luminance of the RGB values of the given pixel, using the formula
    // lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
    // the three values r = lum, g = lum, b = lum.
    public static Color luminance(Color pixel) {
        int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
        return new Color(lum, lum, lum);
    }

    /**
     * Returns an image which is the grayscaled version of the given image.
     */
    public static Color[][] grayScaled(Color[][] image) {
        int rows = image.length;
        int columns = image[0].length;
        Color[][] grayScaledImage = new Color[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Color greyScaledColor = luminance(image[i][j]);
                grayScaledImage[i][j] = greyScaledColor;
            }
        }
        return grayScaledImage;
    }

    /**
     * Returns an image which is the scaled version of the given image.
     * The image is scaled (resized) to have the given width and height.
     */
    public static Color[][] scaled(Color[][] image, int width, int height) {
        int h0 = image.length;
        int w0 = image[0].length;

        Color[][] scaledImage = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int sourceI = (int) (i * ((double) h0 / height));
                int sourceJ = (int) (j * ((double) w0 / width));
                scaledImage[i][j] = image[sourceI][sourceJ];
            }
        }

        return scaledImage;
    }

    /**
     * Computes and returns a blended color which is a linear combination of the two given
     * colors. Each r, g, b, value v in the returned color is calculated using the formula
     * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
     * values in the two input color.
     */
    public static Color blend(Color c1, Color c2, double alpha) {
        int r = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
        int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
        int b = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
        return new Color(r, g, b);
    }

    /**
     * Cosntructs and returns an image which is the blending of the two given images.
     * The blended image is the linear combination of (alpha) part of the first image
     * and (1 - alpha) part the second image.
     * The two images must have the same dimensions.
     */
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        int rows = image1.length;
        int columns = image1[0].length;
        Color[][] blendImage = new Color[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Color blendColor = blend(image1[i][j], image2[i][j], alpha);
                blendImage[i][j] = blendColor;
            }
        }
        return blendImage;
    }

    /**
     * Morphs the source image into the target image, gradually, in n steps.
     * Animates the morphing process by displaying the morphed image in each step.
     * Before starting the process, scales the target image to the dimensions
     * of the source image.
     */
    public static void morph(Color[][] source, Color[][] target, int n) {
        Color[][] scaledTarget = scaled(target, source[0].length, source.length);

        for (int i = 0; i <= n; i++) {
            Color[][] newImage = blend(source, scaledTarget, (double) (n - i) / n);

            display(newImage);
            StdDraw.pause(500);
        }
    }

    /**
     * Creates a canvas for the given image.
     */
    public static void setCanvas(Color[][] image) {
        StdDraw.setTitle("Runigram 2023");
        int height = image.length;
        int width = image[0].length;
        StdDraw.setCanvasSize(height, width);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
        // the StdDraw.show function is called.
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Displays the given image on the current canvas.
     */
    public static void display(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Sets the pen color to the pixel color
                StdDraw.setPenColor(image[i][j].getRed(),
                        image[i][j].getGreen(),
                        image[i][j].getBlue());
                // Draws the pixel as a filled square of size 1
                StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
            }
        }
        StdDraw.show();
    }
}

