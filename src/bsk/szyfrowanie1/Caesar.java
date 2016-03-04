package bsk.szyfrowanie1;

import bsk.exceptions.CipherException;

public class Caesar implements Cipher {

    private int validateKey(String keyText) throws CipherException {
        int key = 0;
        try {
            keyText = keyText.trim();
            key = Integer.parseInt(keyText);
        } catch (NumberFormatException ex) {
            throw new CipherException("Key value is not a number");
        }

        if (key <= 0) {
            throw new CipherException("Key value must be higher than 0");
        }

        return key;
    }

    private int gcd(int a, int b) {
        int t;
        while (b != 0) {
            t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    private boolean areRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    @Override
    public String encrypt(String message, String keyText) throws CipherException {
        int key = validateKey(keyText);

        return message;
    }

    @Override
    public String decrypyt(String message, String keyText) throws CipherException {
        int key = validateKey(keyText);

        return message;
    }

    @Override
    public String getCipherName() {
        return "Caesar (Key-number)";
    }

    @Override
    public String getTemplateMessage() {
        return "CRYPTOGRAPHY";
    }

    @Override
    public String getTemplateKey() {
        return "3";
    }

}
