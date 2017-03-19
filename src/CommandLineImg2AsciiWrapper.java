import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is a wrapper class for using the Img2Ascii class from the command line.
 *
 * @author Pat Manning
 */
public class CommandLineImg2AsciiWrapper {
    public static void main(String[] args) {
        String fileIn = args[0];
        String fileOut = args[1];

        try {
            BufferedImage image = ImageIO.read(new File(fileIn));

            char[][] charOut = Img2Ascii.convertImg2Ascii(image);

            PrintWriter writer = new PrintWriter(fileOut, "US-ASCII");
            for(int i = 0; i < charOut.length; i++) {
                for(int j = 0; j < charOut[i].length; j++) {
                    writer.print(charOut[i][j]);
                }
                if(i != charOut.length - 1) {
                    writer.println();
                }
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error running wrapper class. " + e.getMessage());
        }
    }
}
