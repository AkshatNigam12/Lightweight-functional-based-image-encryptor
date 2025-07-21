import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option:");
        System.out.println("1. Encrypt an image");
        System.out.println("2. Decrypt an image");
        System.out.print("Enter your choice (1 or 2): ");
        String choice = scanner.nextLine();

        try {
            if (choice.equals("1")) {
                System.out.print("Enter the path to the image file to encrypt: ");
                String imagePath = scanner.nextLine();
                Encryptor encryptor = new Encryptor();
                String key = encryptor.generateKey();
                System.out.println("Generated Key: " + key);
                encryptor.storeKey(key);
                encryptor.encryptImage(imagePath, key);
                System.out.println("Image encrypted and saved successfully.");
            } else if (choice.equals("2")) {
                System.out.print("Enter the path to the encrypted image file to decrypt: ");
                String encryptedImagePath = scanner.nextLine();
                System.out.print("Enter the key used for encryption: ");
                String key = scanner.nextLine();
                Decryptor decryptor = new Decryptor();
                decryptor.decryptImage(encryptedImagePath, key);
                System.out.println("Image decrypted and saved successfully.");
            } else {
                System.out.println("Invalid choice. Exiting.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
