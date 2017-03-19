import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * This class is a non-instantiable class used to convert images to arrays of Ascii characters
 *
 * @author Pat Manning
 */
public class Img2Ascii {

    // Private constructor used to prevent instantiation.  All methods are static.
    private Img2Ascii(){}

    /**
     * This method takes an image and reurns an array of Ascii characters that when viewed
     * in an output as a monospace font resemble the original picture.
     *
     * @param img This is the image that the output Ascii array will represent
     * @return A two-dimensional array of characters representing the input image as text
     */
    public static char[][] convertImg2Ascii(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster wr = (WritableRaster) img.getData();

        float[][] outImg = new float[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                Color c = new Color(img.getRGB(j, i));
                float[] hsbvals = new float[3];
                Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbvals);
                if(hsbvals[2] >= 0.5) {
                    outImg[i][j] = 0;
                } else {
                    outImg[i][j] = 1;
                }
            }
        }

        char[][] charOut = new char[height / 2][width / 2];

        for (int i = 0; i < height - 2; i+=2) {
            for (int j = 0; j < width - 2; j+=2) {
                charOut[i/2][j/2] = getAscii(outImg[i][j], outImg[i+1][j], outImg[i][j+1], outImg[i+1][j+1]);
            }
        }

        return charOut;
    }

    /**
     * This method takes a 2x2 of pixels from an image, where each pixel is on or off,
     * and returns an Ascii character that most closely resembles the pixel configuration
     *
     * <p>This class takes 4 black and white pixels in a 2x2 arrangement.
     * A bitmask is created using the 4 inputs (as each is either a 1 or a 0) to facilitate
     * the use of a switch statement for determining which Ascii character best represents
     * the arrangement of 'on' pixels.</p>
     *
     * @param topLeft A float representing if the top left pixel is on or off
     * @param bottomLeft A float representing if the bottom left pixel is on or off
     * @param topRight A float representing if the top right pixel is on or off
     * @param bottomRight A float representing if the bottom right pixel is on or off
     * @return The Ascii character best representing the 2x2 of pixels
     */
    public static char getAscii(float topLeft, float bottomLeft, float topRight, float bottomRight) {
        String bitString = "" + (int)topLeft + (int)topRight + (int) bottomLeft + (int) bottomRight;
        int bitMask = Integer.parseInt(bitString, 2);
        switch(bitMask) {
            case 0:
                // 00
                // 00
                return ' ';
            case 1:
                // 00
                // 01
                return '.';
            case 2:
                // 00
                // 10
                return ',';
            case 3:
                // 00
                // 11
                return '_';
            case 4:
                // 01
                // 00
                return '\'';
            case 5:
                // 01
                // 01
                return ']';
            case 6:
                // 01
                // 10
                return '/';
            case 7:
                // 01
                // 11
                return 'd';
            case 8:
                // 10
                // 00
                return '`';
            case 9:
                // 10
                // 01
                return '\\';
            case 10:
                // 10
                // 10
                return '[';
            case 11:
                // 10
                // 11
                return 'L';
            case 12:
                // 11
                // 00
                return '^';
            case 13:
                // 11
                // 01
                return '?';
            case 14:
                // 11
                // 10
                return 'F';
            case 15:
                // 11
                // 11
                return '#';
            default:
                return ' '; // Error case print a space
        }
    }
}
