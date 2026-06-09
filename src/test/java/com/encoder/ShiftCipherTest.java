package com.encoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ShiftCipher encode/decode logic.
 * Covers the specification examples, edge cases, and OOP contracts.
 */
@DisplayName("ShiftCipher Tests")
class ShiftCipherTest {

    private ReferenceTable referenceTable;

    @BeforeEach
    void setUp() {
        referenceTable = new ReferenceTable();
    }

    @Test
    @DisplayName("Spec example 1: offset 'B' encodes 'HELLO WORLD' to 'BGDKKN VNQKC'")
    void testEncodeWithOffsetB() {
        Cipher cipher = new ShiftCipher('B');
        String result = cipher.encode("HELLO WORLD");
        assertEquals("BGDKKN VNQKC", result);
    }

    @Test
    @DisplayName("Spec example 2: offset 'F' encodes 'HELLO WORLD' correctly")
    void testEncodeWithOffsetF() {
        Cipher cipher = new ShiftCipher('F');
        String result = cipher.encode("HELLO WORLD");
        assertEquals("FC/GGJ RJMG.", result);
    }

    @Test
    @DisplayName("Decode reverses encode for offset 'B'")
    void testDecodeWithOffsetB() {
        Cipher cipher = new ShiftCipher('B');
        assertEquals("HELLO WORLD", cipher.decode("BGDKKN VNQKC"));
    }

    @Test
    @DisplayName("Decode reverses encode for offset 'F'")
    void testDecodeWithOffsetF() {
        Cipher cipher = new ShiftCipher('F');
        assertEquals("HELLO WORLD", cipher.decode("FC/GGJ RJMG."));
    }

    // ------------------------------------------------------------------
    // Round-trip tests
    // ------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'F', 'Z', '0', '9', '(', '/', '+'})
    @DisplayName("Encode then decode returns original plaintext for various offsets")
    void testRoundTrip(char offset) {
        Cipher cipher = new ShiftCipher(offset);
        String plainText = "HELLO WORLD";
        assertEquals(plainText, cipher.decode(cipher.encode(plainText)));
    }

    @Test
    @DisplayName("Round-trip preserves characters not in the reference table")
    void testRoundTripWithPassThroughChars() {
        Cipher cipher = new ShiftCipher('B');
        String input = "Hello, World! 123 @#$";
        assertEquals(input, cipher.decode(cipher.encode(input)));
    }

    // ------------------------------------------------------------------
    // Wrap-around behaviour
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Characters wrap around when shifted past index 0 of the table")
    void testWrapAround() {
        Cipher cipher = new ShiftCipher('B');
        String encoded = cipher.encode("A");
        assertEquals("B/", encoded);
        assertEquals("A", cipher.decode(encoded));
    }

    // ------------------------------------------------------------------
    // Edge cases
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Empty string encodes to just the offset character")
    void testEncodeEmptyString() {
        Cipher cipher = new ShiftCipher('B');
        assertEquals("B", cipher.encode(""));
    }

    @Test
    @DisplayName("Decoding a string with only the offset character returns empty string")
    void testDecodeOffsetOnly() {
        Cipher cipher = new ShiftCipher('B');
        assertEquals("", cipher.decode("B"));
    }

    @Test
    @DisplayName("Encode throws on null input")
    void testEncodeNullThrows() {
        Cipher cipher = new ShiftCipher('B');
        assertThrows(IllegalArgumentException.class, () -> cipher.encode(null));
    }

    @Test
    @DisplayName("Decode throws on null input")
    void testDecodeNullThrows() {
        Cipher cipher = new ShiftCipher('B');
        assertThrows(IllegalArgumentException.class, () -> cipher.decode(null));
    }

    @Test
    @DisplayName("Decode throws on empty string (no offset character present)")
    void testDecodeEmptyStringThrows() {
        Cipher cipher = new ShiftCipher('B');
        assertThrows(IllegalArgumentException.class, () -> cipher.decode(""));
    }

    @Test
    @DisplayName("Constructor throws when offset character is not in the reference table")
    void testInvalidOffsetCharThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ShiftCipher('!'));
        assertThrows(IllegalArgumentException.class, () -> new ShiftCipher('a'));
        assertThrows(IllegalArgumentException.class, () -> new ShiftCipher(' '));
    }

    // ------------------------------------------------------------------
    // ReferenceTable tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Reference table has exactly 44 characters")
    void testReferenceTableSize() {
        assertEquals(44, ReferenceTable.SIZE);
    }

    @Test
    @DisplayName("Reference table indexOf returns correct indices for known characters")
    void testReferenceTableIndexOf() {
        assertEquals(0,  referenceTable.indexOf('A'));
        assertEquals(1,  referenceTable.indexOf('B'));
        assertEquals(25, referenceTable.indexOf('Z'));
        assertEquals(26, referenceTable.indexOf('0'));
        assertEquals(35, referenceTable.indexOf('9'));
        assertEquals(43, referenceTable.indexOf('/'));
        assertEquals(-1, referenceTable.indexOf('a'));
    }
}
