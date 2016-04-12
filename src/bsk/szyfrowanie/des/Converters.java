package bsk.szyfrowanie.des;

import bsk.exceptions.CipherException;
import java.util.Arrays;

public class Converters {

    public static int[] binStringToBinInts(String message) throws CipherException {
        int[] result = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '0') {
                result[i] = 0;
            } else if (message.charAt(i) == '1') {
                result[i] = 1;
            } else {
                throw new CipherException("Cannot convert '" + message.charAt(i) + "' to binary value");
            }
        }

        return result;
    }

    public static String binIntsToBinString(int[] arr) throws CipherException {
        if (arr == null) {
            throw new CipherException("Array cannot be empty (binIntsToBinString)");
        }
        StringBuilder result = new StringBuilder(arr.length);

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                result.append('0');
            } else if (arr[i] == 1) {
                result.append('1');
            } else {
                throw new CipherException("'" + arr[i] + "' is not a binary value");
            }
        }

        return result.toString();
    }
////////////////////////////////////////////

    public static int[] hexStringToBinInts(String message) throws CipherException {
        int[] result = new int[message.length() * 4];
        for (int i = 0; i < message.length(); i++) {
            System.arraycopy(hexCharToBinInts(message.charAt(i)), 0, result, 4 * i, 4);
        }
        return result;
    }

    public static String binIntsToHexString(int[] arr) throws CipherException {
        if (arr == null) {
            throw new CipherException("Array cannot be empty (binIntsToHexString)");
        }
        if (arr.length % 4 != 0) {
            throw new CipherException("Number of bits are not dividable by 4 (HEX)");
        }
        StringBuilder result = new StringBuilder(arr.length / 4);
        int[] charArr = new int[4];
        for (int i = 0, length = arr.length / 4; i < length; i++) {
            for (int j = 0; j < charArr.length; j++) {
                charArr[j] = arr[i * 4 + j];
            }
            result.append(binIntsToHexChar(charArr));
        }
        return result.toString();
    }

    public static int[] hexCharToBinInts(char hex) throws CipherException {
        int hexInt = (int) hex;
        hexInt -= 48;
        if (hexInt < 0) {
            throw new CipherException("Cannot convert '" + String.valueOf(hex) + "' to hex value");
        }
        if (hexInt > 9) {
            hexInt -= 7;
            if (hexInt < 10 || hexInt > 15) {
                throw new CipherException("Cannot convert '" + String.valueOf(hex) + "' to hex value");
            }
        }
        int[] result = new int[4];
        for (int i = 0; i < result.length; i++) {
            result[result.length - 1 - i] = hexInt & 1;
            hexInt >>= 1;
        }
        return result;
    }

    public static char binIntsToHexChar(int[] arr) throws CipherException {
        if (arr.length != 4) {
            throw new CipherException("Cannot convert '" + Arrays.toString(arr) + "' bits to hex char");
        }

        int hexInt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0 && arr[i] != 1) {
                throw new CipherException("Cannot convert '" + Arrays.toString(arr) + "' bits to hex char");
            }
            hexInt <<= 1;
            hexInt += arr[i];
        }

        hexInt += 48;
        if (hexInt > 57) {
            hexInt += 7;
        }
        return (char) hexInt;
    }
///////////////////////////////////////////////////////    

    public static int[] asciiStringToBinInts(String message) throws CipherException {
        int[] result = new int[message.length() * 8];
        for (int i = 0; i < message.length(); i++) {
            System.arraycopy(asciiCharToBinInts(message.charAt(i)), 0, result, 8 * i, 8);
        }

        return result;
    }

    public static String binIntsToAsciiString(int[] arr) throws CipherException {
        if (arr == null) {
            throw new CipherException("Array cannot be empty (binIntsToAsciiString)");
        }
        if (arr.length % 8 != 0) {
            throw new CipherException("Number of bits are not dividable by 8 (ASCII)");
        }
        StringBuilder result = new StringBuilder(arr.length / 8);
        int[] charArr = new int[8];
        for (int i = 0, length = arr.length / 8; i < length; i++) {
            for (int j = 0; j < charArr.length; j++) {
                charArr[j] = arr[i * 8 + j];
            }
            result.append(binIntsToAsciiChar(charArr));
        }

        return result.toString();
    }

    public static int[] asciiCharToBinInts(char ascii) throws CipherException {
        int asciiInt = (int) ascii;
        int[] result = new int[8];
        for (int i = 0; i < result.length; i++) {
            result[result.length - 1 - i] = asciiInt & 1;
            asciiInt >>= 1;
        }
        return result;
    }

    public static char binIntsToAsciiChar(int[] arr) throws CipherException {
        if (arr.length != 8) {
            throw new CipherException("Requires 8 bits to convert to ascii, instead got: '" + Arrays.toString(arr) + "'");
        }
        int asciiInt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0 && arr[i] != 1) {
                throw new CipherException("Cannot convert '" + Arrays.toString(arr) + "' bits to ASCII char");
            }
            asciiInt <<= 1;
            asciiInt += arr[i];
        }
        return (char) asciiInt;
    }
///////////////////////////////////////////////

    public static int binIntsToInt(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result <<= 1;
            result += arr[i] & 1;
        }
        return result;
    }

    public static int[] intToBinInts(int value) throws CipherException {
        if (value < 0) {
            throw new CipherException("Cannot convert negative integer: '" + value + "' to binary values");
        }
        int power = 1;
        int powerOf2 = 2;
        while (true) {
            if (value < powerOf2) {
                break;
            }
            power++;
            powerOf2 *= 2;
        }
        int[] arr = new int[power];
        for (int i = 0; i < arr.length; i++) {
            arr[arr.length - 1 - i] = value & 1;
            value >>= 1;
        }
        return arr;
    }
}
