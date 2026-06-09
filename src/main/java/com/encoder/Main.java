package com.encoder;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Shift Cipher Encoder/Decoder Demo ===\n");

        System.out.println("--- Offset 'B' (shift = 1) ---");
        Cipher cipherB = new ShiftCipher('B');

        String plainText = "HELLO WORLD";
        String encoded = cipherB.encode(plainText);
        String decoded = cipherB.decode(encoded);

        System.out.println("Plaintext : " + plainText);
        System.out.println("Encoded   : " + encoded);
        System.out.println("Decoded   : " + decoded);
        System.out.println();

        System.out.println("--- Offset 'F' (shift = 5) ---");
        Cipher cipherF = new ShiftCipher('F');

        encoded = cipherF.encode(plainText);
        decoded = cipherF.decode(encoded);

        System.out.println("Plaintext : " + plainText);
        System.out.println("Encoded   : " + encoded);
        System.out.println("Decoded   : " + decoded);
        System.out.println();

        System.out.println("--- Non-table chars pass through ---");
        Cipher cipherA = new ShiftCipher('A');

        String mixed = "Hello, World! 123";
        encoded = cipherA.encode(mixed);
        decoded = cipherA.decode(encoded);

        System.out.println("Plaintext : " + mixed);
        System.out.println("Encoded   : " + encoded);
        System.out.println("Decoded   : " + decoded);
        System.out.println();

        System.out.println("--- Symbols and digits ---");
        Cipher cipherZ = new ShiftCipher('Z');

        String symbolText = "ABC123()+";
        encoded = cipherZ.encode(symbolText);
        decoded = cipherZ.decode(encoded);

        System.out.println("Plaintext : " + symbolText);
        System.out.println("Encoded   : " + encoded);
        System.out.println("Decoded   : " + decoded);
    }
}