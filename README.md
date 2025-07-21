# Lightweight-functional-based-image-encryptor
Securely encrypt and decrypt images using a 32-bit hash key with XOR encryption. The project stores keys with timestamps, verifies keys during decryption, and provides a simple user interface. It includes Main.java, Encryptor.java, and Decryptor.java for seamless image security management.

# Image Encryption and Decryption Project

This Java project provides functionality to encrypt and decrypt images using a simple XOR-based encryption method with a generated key. The project consists of three main classes:

- `Main.java`: The main entry point that interacts with the user, allowing them to choose between encrypting or decrypting an image.
- `Encryptor.java`: Contains the logic for generating a secure 32-bit hash key, encrypting the image, storing the key with a timestamp, and saving an encrypted verification string for key validation.
- `Decryptor.java`: Contains the logic for decrypting the image using the provided key and verifying the key correctness before decryption.

## Features

- Generates a secure 32-bit hash key for encryption.
- Stores keys with timestamps in `keys.txt` for easy identification.
- Verifies the correctness of the decryption key before decrypting the image.
- Provides user-friendly prompts for encryption and decryption.
- Saves encrypted and decrypted images with appropriate filenames.

## How to Use

1. Compile the Java files:

   ```bash
   javac Main.java Encryptor.java Decryptor.java
   ```

2. Run the program:

   ```bash
   java Main
   ```

3. Follow the on-screen prompts to choose encryption or decryption and provide the required file paths and keys.

## Notes

- The encryption method uses XOR with the generated key's hash code.
- A verification string is encrypted and saved during encryption to ensure the correct key is used during decryption.
- The project currently supports PNG images.

## License

This project is open source and free to use.




