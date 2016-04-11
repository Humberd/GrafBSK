package bsk.szyfrowanie.des;

import bsk.exceptions.CipherException;

public class DES {

    public DES() {

    }

    public int[] encrypt(int[] message, int[] key) throws CipherException {
        
        return null;
    }

    public int[] decrytp(int[] message, int[] key) throws CipherException {
        
        return null;
    }

    public int xor(int a, int b) {
        return (a == b) ? 0 : 1;
    }

    public int xor(int[] values) {
        if (values.length == 1) {
            return values[0];
        }
        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            result = xor(result, values[i]);
        }
        return result;
    }
}
