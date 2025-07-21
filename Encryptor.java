import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Encryptor {

    private static final String VERIFICATION_STRING = "ImageEncryptionVerification";

    public String generateKey() {
        try {
            // Generate a random string
            byte[] randomBytes = new byte[16];
            new Random().nextBytes(randomBytes);
            String randomString = new String(randomBytes, StandardCharsets.UTF_8);

            // Hash the random string using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(randomString.getBytes(StandardCharsets.UTF_8));

            // Take first 4 bytes (32 bits) of the hash and convert to hex string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append(String.format("%02x", hashBytes[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Fallback key
            return "deadbeef";
        }
    }

    public void storeKey(String key) {
        try (FileWriter writer = new FileWriter("keys.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            // Get current timestamp
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            out.println(timestamp + " - " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encryptImage(String imagePath, String key) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        if (image == null) {
            System.out.println("Could not read the image file.");
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage encryptedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int keyHash = key.hashCode();

        // Encrypt the image pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int encryptedRGB = rgb ^ keyHash;
                encryptedImage.setRGB(x, y, encryptedRGB);
            }
        }

        saveImage(encryptedImage, imagePath);

        // Save encrypted verification string
        saveEncryptedVerificationString(key, imagePath);
    }

    private void saveImage(BufferedImage image, String originalImagePath) throws IOException {
        String encryptedImagePath = originalImagePath.substring(0, originalImagePath.lastIndexOf('.'))
                + "_encrypted.png";
        ImageIO.write(image, "png", new File(encryptedImagePath));
    }

    private void saveEncryptedVerificationString(String key, String originalImagePath) {
        try {
            int keyHash = key.hashCode();
            StringBuilder encryptedVerification = new StringBuilder();
            for (char c : VERIFICATION_STRING.toCharArray()) {
                encryptedVerification.append((char)(c ^ keyHash));
            }
            String verificationFilePath = originalImagePath.substring(0, originalImagePath.lastIndexOf('.'))
                    + "_encrypted_verification.txt";
            try (FileWriter writer = new FileWriter(verificationFilePath)) {
                writer.write(encryptedVerification.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
