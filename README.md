# Shift Cipher Encoder / Decoder

A Java implementation of a shift cipher that encodes and decodes text using a configurable offset character from a 44-character reference table.

## Reference Table

The table contains 44 characters in this order:

| Index | Char | | Index | Char | | Index | Char |
|-------|------|-|-------|------|-|-------|------|
| 0     | A    | | 15    | P    | | 30    | 4    |
| 1     | B    | | 16    | Q    | | 31    | 5    |
| 2     | C    | | 17    | R    | | 32    | 6    |
| 3     | D    | | 18    | S    | | 33    | 7    |
| 4     | E    | | 19    | T    | | 34    | 8    |
| 5     | F    | | 20    | U    | | 35    | 9    |
| 6     | G    | | 21    | V    | | 36    | (    |
| 7     | H    | | 22    | W    | | 37    | )    |
| 8     | I    | | 23    | X    | | 38    | *    |
| 9     | J    | | 24    | Y    | | 39    | +    |
| 10    | K    | | 25    | Z    | | 40    | ,    |
| 11    | L    | | 26    | 0    | | 41    | -    |
| 12    | M    | | 27    | 1    | | 42    | .    |
| 13    | N    | | 28    | 2    | | 43    | /    |
| 14    | O    | | 29    | 3    | |       |      |

## Encoding Logic

1. Choose any character from the reference table as the **offset** (e.g. `B` = index 1, `F` = index 5).
2. The **first character** of the encoded message is always the offset character.
3. Each plaintext character found in the table is shifted by **subtracting** the offset index (with wrap-around). Characters **not** in the table pass through unchanged.
4. To decode, read the first character as the offset and **add** the shift back.

### Examples

**Offset `B` (shift = 1):**
```
Plaintext:  H  E  L  L  O     W  O  R  L  D
Encoded:  B G  D  K  K  N     V  N  Q  K  C
```

**Offset `F` (shift = 5):**
```
Plaintext:  H  E  L  L  O     W  O  R  L  D
Encoded:  F C  /  G  G  J     R  J  M  G  .
```

## Project Structure

```
src/
├── main/java/com/encoder/
│   ├── Cipher.java          # Interface defining encode() and decode()
│   ├── ShiftCipher.java     # Core implementation
│   ├── ReferenceTable.java  # Encapsulates the 44-char table
│   └── Main.java            # Demo / entry point
└── test/java/com/encoder/
    └── ShiftCipherTest.java # JUnit tests
```

## OOP Design

| Concept        | Where applied |
|----------------|---------------|
| **Abstraction** | `Cipher` interface — callers depend on the interface, not the implementation |
| **Encapsulation** | `ReferenceTable` encapsulates table data and lookup; `ShiftCipher` hides shift arithmetic |
| **Single Responsibility** | Each class has one clear job |

## Build & Run

Requires **Java 21+** and **Maven 3.9+**.

```bash
# Compile and run all tests
mvn test

# Run the demo
javac -d target/classes src/main/java/com/encoder/*.java
java -cp target/classes com.encoder.Main
```

### Expected demo output

```
=== Shift Cipher Encoder/Decoder Demo ===

--- Offset 'B' (shift = 1) ---
Plaintext : HELLO WORLD
Encoded   : BGDKKN VNQKC
Decoded   : HELLO WORLD

--- Offset 'F' (shift = 5) ---
Plaintext : HELLO WORLD
Encoded   : FC/GGJ RJMG.
Decoded   : HELLO WORLD

--- Non-table chars pass through ---
Plaintext : Hello, World! 123
Encoded   : AHello, World! 123
Decoded   : Hello, World! 123

--- Symbols and digits ---
Plaintext : ABC123()+
Encoded   : ZTUVCDELMO
Decoded   : ABC123()+
...
```
