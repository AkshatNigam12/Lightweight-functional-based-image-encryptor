import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Decryptor {

    public void decryptImage(String encryptedImagePath, String key) throws IOException {
        BufferedImage encryptedImage = ImageIO.read(new File(encryptedImagePath));
        if (encryptedImage == null) {
            System.out.println("Could not read the encrypted image file.");
            return;
        }

        int width = encryptedImage.getWidth();
        int height = encryptedImage.getHeight();
        BufferedImage decryptedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int keyHash = key.hashCode();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int encryptedRGB = encryptedImage.getRGB(x, y);
                int decryptedRGB = encryptedRGB ^ keyHash;
                decryptedImage.setRGB(x, y, decryptedRGB);
            }
        }

        saveImage(decryptedImage, encryptedImagePath);
    }

    private void saveImage(BufferedImage image, String encryptedImagePath) throws IOException {
        int dotIndex = encryptedImagePath.lastIndexOf('.');
        String baseName;
        if (dotIndex > 0) {
            baseName = encryptedImagePath.substring(0, dotIndex);
        } else {
            baseName = encryptedImagePath;
        }
        baseName = baseName.replace("_encrypted", "");
        String outputImagePath = baseName + "_decrypted.png";

        ImageIO.write(image, "png", new File(outputImagePath));
    }
}
