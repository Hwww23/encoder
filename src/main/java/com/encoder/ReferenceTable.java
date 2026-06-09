package com.encoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the 44-character reference table used for encoding/decoding.
 *
 * <p>Lookup by character ({@link #indexOf}) uses a {@link HashMap} for O(1) average-case
 * performance. Lookup by index ({@link #getCharAt}) uses a plain array, also O(1).
 *
 * <p>Both structures are built once at construction time and never mutated.
 */
public class ReferenceTable {

    private static final char[] REFERENCE = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9',
        '(',')', '*','+',',','-','.','/'
    };

    private static final Map<Character, Integer> INDEX_MAP;

    static {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < REFERENCE.length; i++) {
            map.put(REFERENCE[i], i);
        }
        INDEX_MAP = Collections.unmodifiableMap(map);
    }

    public static final int SIZE = REFERENCE.length;

    /**
     * Returns the character at the given index.
     */
    public char getCharAt(int index) {
        return REFERENCE[index];
    }

    /**
     * Returns the index of the given character, or -1 if not in the table.
     */
    public int indexOf(char c) {
        return INDEX_MAP.getOrDefault(c, -1);
    }

    /**
     * Returns true if the given character is in the reference table.
     */
    public boolean contains(char c) {
        return INDEX_MAP.containsKey(c);
    }

    /**
     * Returns a copy of the underlying character array.
     */
    public char[] getTable() {
        return REFERENCE.clone();
    }
}