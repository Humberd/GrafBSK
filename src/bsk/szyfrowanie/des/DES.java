package bsk.szyfrowanie.des;

import bsk.exceptions.CipherException;
import java.util.Arrays;

public class DES {

    public DES() {

    }

    public int[] encrypt(int[] message, int[] key) throws CipherException {
        int numberOfMessageBlocks = message.length / 64;
        int messageRemainder = message.length % 64;
        numberOfMessageBlocks += 1;

        int[] paddingMessage = new int[numberOfMessageBlocks * 64];
        System.arraycopy(message, 0, paddingMessage, 0, message.length);
        paddingMessage[(numberOfMessageBlocks - 1) * 64 + messageRemainder] = 1;

        int[] result = new int[numberOfMessageBlocks * 64];
        int[][] keys = generateKeyParts(key);

        for (int i = 0; i < numberOfMessageBlocks; i++) {
            int[] messagePart = new int[64];
            System.arraycopy(paddingMessage, i * 64, messagePart, 0, 64);
            System.arraycopy(mainAlgorithm(messagePart, keys), 0, result, i * 64, 64);
        }

        return result;
    }

    public int[] decrypt(int[] message, int[] key) throws CipherException {
        int numberOfMessageBlocks = message.length / 64;
        int messageRemainder = message.length % 64;
        int[] result = new int[numberOfMessageBlocks * 64];
        int[][] keys = Converters.invertArray(generateKeyParts(key));

        for (int i = 0; i < numberOfMessageBlocks; i++) {
            int[] messagePart = new int[64];
            System.arraycopy(message, i * 64, messagePart, 0, 64);
            System.arraycopy(mainAlgorithm(messagePart, keys), 0, result, i * 64, 64);
        }

        int paddingPositionCut = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            if (result[i] == 1) {
                paddingPositionCut = i;
                break;
            }
        }
        int[] paddedResult = new int[paddingPositionCut];
        System.arraycopy(result, 0, paddedResult, 0, paddingPositionCut);
        return paddedResult;
    }

    public int[] mainAlgorithm(int[] message, int[][] keys) throws CipherException {
        message = permutate(message, DESTables.IP);
        int[][] messageParts = split(message, 2);
        int[] left = messageParts[0];
        int[] right = messageParts[1];

        for (int i = 0; i < keys.length; i++) {
            int[] newRight = xor(left, functionF(right, keys[i]));
            int[] newLeft = right;
            right = newRight;
            left = newLeft;
        }

        message = merge(right, left);
        message = permutate(message, DESTables.IP_1);
        return message;
    }

    public int[] functionF(int[] right, int[] keyPart) throws CipherException {
        right = permutate(right, DESTables.E);
        int[] xoredValue = xor(right, keyPart);
        int[][] sBoxInputParts = split(xoredValue, 8);
        int[][] sBoxOutputParts = new int[8][];

        for (int i = 0; i < 8; i++) {
            sBoxOutputParts[i] = SBox(sBoxInputParts[i], DESTables.S[i]);
        }

        int[] result = merge(sBoxOutputParts);
        result = permutate(result, DESTables.P);
        return result;
    }

    public int[] SBox(int[] array, int[] table) throws CipherException {
        if (array == null || table == null) {
            throw new CipherException("Cannot perform SBox operation on a null array");
        }
        if (array.length != 6) {
            throw new CipherException("Cannot perform SBox operation - input array length: '" + array.length + "' is different than :'6'");
        }
        int[] rowNumberArray = new int[]{array[0], array[5]};
        int[] columnNumberArray = new int[4];
        for (int i = 0; i < columnNumberArray.length; i++) {
            columnNumberArray[i] = array[i + 1];
        }
        int rowNumber = Converters.binIntsToInt(rowNumberArray);
        int columnNumber = Converters.binIntsToInt(columnNumberArray);

        int[] result = Converters.changeBinIntsLength(Converters.intToBinInts(table[rowNumber * 16 + columnNumber]), 4);

        return result;
    }

    public int[][] generateKeyParts(int[] key) throws CipherException {
        int[][][] keyParts = new int[17][2][];
        keyParts[0] = split(permutate(key, DESTables.PC1));
        for (int i = 1; i < keyParts.length; i++) {
            keyParts[i][0] = leftShift(keyParts[i - 1][0], DESTables.KEY_LEFT_SHIFTS[i - 1]);
            keyParts[i][1] = leftShift(keyParts[i - 1][1], DESTables.KEY_LEFT_SHIFTS[i - 1]);
        }
        int[][] keys = new int[16][];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = permutate(merge(keyParts[i + 1]), DESTables.PC2);
        }
        return keys;
    }

    public int[] leftShift(int[] arr, int times) {
        for (int i = 0; i < times; i++) {
            arr = leftShift(arr);
        }
        return arr;
    }

    public int[] leftShift(int[] arr) {
        int[] newArr = new int[arr.length];
        newArr[newArr.length - 1] = arr[0];
        System.arraycopy(arr, 1, newArr, 0, arr.length - 1);

        return newArr;
    }

    public int[][] split(int[] arr) throws CipherException {
        return split(arr, 2);
    }

    public int[][] split(int[] arr, int parts) throws CipherException {
        if (parts == 0) {
            return new int[0][0];
        } else if (parts == 1) {
            return new int[][]{arr};
        }
        if (arr.length % parts != 0) {
            throw new CipherException("Cannot split array - the length: '" + arr.length + "' is not dividable by: '" + parts + "'");
        }
        int length = arr.length;
        int[][] result = new int[parts][length / parts];
        for (int i = 0; i < parts; i++) {
            for (int j = 0; j < length / parts; j++) {
                result[i][j] = arr[i * length / parts + j];
            }
        }

        return result;
    }

    public int[] merge(int[][] arr) {
        if (arr.length == 0) {
            return merge(new int[0], new int[0]);
        } else if (arr.length == 1) {
            return merge(arr[0], new int[0]);
        }
        int[] result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            result = merge(result, arr[i]);
        }
        return result;
    }

    public int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public int[] permutate(int[] message, int[] table) throws CipherException {
        int[] result = new int[table.length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] - 1 >= message.length) {
                throw new CipherException("Permutation table contains index: '" + table[i] + "', that exceeds the message length: '" + message.length + "'");
            }
            result[i] = message[table[i] - 1];
        }
        return result;
    }

    public int[] xor(int[] a, int[] b) {
        if (a.length > b.length) {
            int[] tab = new int[a.length];
            System.arraycopy(b, 0, tab, a.length - b.length, b.length);
            b = tab;
        } else if (a.length < b.length) {
            int[] tab = new int[b.length];
            System.arraycopy(a, 0, tab, b.length - a.length, a.length);
            a = tab;
        }
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = xor(a[i], b[i]);
        }

        return result;
    }

    public int xor(int a, int b) {
        return (a == b) ? 0 : 1;
    }
}
