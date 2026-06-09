package com.encoder;

public class ShiftCipher implements Cipher {

    private final ReferenceTable table;
    private final char offsetChar;

    /**
     * Constructs a ShiftCipher with the specified offset character.
     *
     * @param offsetChar any character present in the 44-character reference table
     * @throws IllegalArgumentException if offsetChar is not in the reference table
     */
    public ShiftCipher(char offsetChar) {
        this.table = new ReferenceTable();
        if (!table.contains(offsetChar)) {
            throw new IllegalArgumentException(
                "Offset character '" + offsetChar + "' is not in the reference table."
            );
        }
        this.offsetChar = offsetChar;
    }

    /**
     * Encodes the plaintext using this cipher's offset character.
     * The returned string always begins with the offset character.
     *
     * @param plainText the original text; must not be null
     * @return the encoded string prefixed with the offset character
     * @throws IllegalArgumentException if plainText is null
     */
    @Override
    public String encode(String plainText) {
        if (plainText == null) {
            throw new IllegalArgumentException("plainText must not be null.");
        }

        int shift = table.indexOf(offsetChar);
        StringBuilder sb = new StringBuilder();
        sb.append(offsetChar);

        for (char c : plainText.toCharArray()) {
            int idx = table.indexOf(c);
            if (idx >= 0) {
                int encodedIdx = (idx - shift + ReferenceTable.SIZE) % ReferenceTable.SIZE;
                sb.append(table.getCharAt(encodedIdx));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Decodes the encoded text. The first character is consumed as the offset key
     * and is not included in the returned plaintext.
     *
     * @param encodedText the encoded string; must not be null and must have at least 1 character
     * @return the original plaintext
     * @throws IllegalArgumentException if encodedText is null, empty, or has an invalid offset char
     */
    @Override
    public String decode(String encodedText) {
        if (encodedText == null) {
            throw new IllegalArgumentException("encodedText must not be null.");
        }
        if (encodedText.isEmpty()) {
            throw new IllegalArgumentException("encodedText must contain an offset character.");
        }

        char offset = encodedText.charAt(0);
        int shift = table.indexOf(offset);
        if (shift < 0) {
            throw new IllegalArgumentException(
                "First character '" + offset + "' is not a valid offset (not in the reference table)."
            );
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < encodedText.length(); i++) {
            char c = encodedText.charAt(i);
            int idx = table.indexOf(c);
            if (idx >= 0) {
                int decodedIdx = (idx + shift) % ReferenceTable.SIZE;
                sb.append(table.getCharAt(decodedIdx));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
