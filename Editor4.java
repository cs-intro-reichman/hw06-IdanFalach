import java.awt.Color;

/**
 * Morphing an image into its grayScaled version.
 * The program recieves two command-line arguments: a string representing the name
 * of the PPM file of a source image and the number of morphing steps (an int).
 * For example, to morph Thor image into a black and white Thor image in 50 steps, use:
 * java Editor4 thor.ppm 50
 */
public class Editor4 {

    public static void main(String[] args) {
        String source = args[0];
        int n = Integer.parseInt(args[1]);
        Color[][] sourceImage = Runigram.read(source);
        Color[][] targetImage = Runigram.grayScaled(sourceImage);
        Runigram.setCanvas(sourceImage);
        Runigram.morph(sourceImage, targetImage, n);
    }
}
