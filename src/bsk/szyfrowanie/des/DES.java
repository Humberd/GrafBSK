package bsk.szyfrowanie.des;

import bsk.exceptions.CipherException;
import java.util.Arrays;

public class DES {

    public DES() {

    }

    public int[] encrypt(int[] message, int[] key) throws CipherException {
        int[][] keys = generateKeyParts(key);
        return message;
    }

    public int[] decrypt(int[] message, int[] key) throws CipherException {

        return message;
    }

    public int[][] generateKeyParts(int[] key) throws CipherException {
        int[][][] keyParts = new int[17][2][];
        keyParts[0] = split(permutate(key, DESTables.PC1));
        for (int i = 1; i < keyParts.length; i++) {
            System.out.println(i - 1);
            keyParts[i][0] = leftShift(keyParts[i - 1][0], DESTables.KEY_LEFT_SHIFTS[i - 1]);
            System.out.println(Converters.binIntsToBinString(keyParts[i-1][0]));
            keyParts[i][1] = leftShift(keyParts[i - 1][1], DESTables.KEY_LEFT_SHIFTS[i - 1]);
            System.out.println(Converters.binIntsToBinString(keyParts[i-1][1]));
        }
        int[][] keys = new int[16][];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = permutate(merge(keyParts[i + 1]), DESTables.PC2);
            System.out.println(Converters.binIntsToBinString(keys[i]));
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
        if (arr.length % 2 != 0) {
            throw new CipherException("Cannot split array - the length: '" + arr.length + "' is an odd number");
        }
        int[][] result = new int[2][arr.length / 2];
        for (int i = 0; i < arr.length / 2; i++) {
            result[0][i] = arr[i];
            result[1][i] = arr[arr.length / 2 + i];
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
