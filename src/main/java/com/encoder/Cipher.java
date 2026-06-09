package com.encoder;

public interface Cipher {

    /**
     * Encodes the given plaintext into an obfuscated string.
     *
     * @param plainText the original text to encode
     * @return the encoded string (first character is the offset character)
     * @throws IllegalArgumentException if plainText is null
     */
    String encode(String plainText);

    /**
     * Decodes the given encoded text back into the original plaintext.
     * The first character of the encoded text is used as the offset key.
     *
     * @param encodedText the encoded string to decode
     * @return the original plaintext
     * @throws IllegalArgumentException if encodedText is null or has no offset character
     */
    String decode(String encodedText);
}
