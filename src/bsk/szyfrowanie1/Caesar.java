package bsk.szyfrowanie1;

import bsk.exceptions.CipherException;
import java.util.Arrays;

public class Caesar implements Cipher {

    private int[] validateKey(String keyText) throws CipherException {
        int[] key = new int[2];
        keyText = keyText.replaceAll(" ", "");
        String[] keyArray = keyText.split(",");
        int alphabetLength = 26;
        if (keyArray.length != 2) {
            throw new CipherException("Key values must be seperated by ','");
        }

        for (int i = 0; i < 2; i++) {
            try {
                key[i] = Integer.parseInt(keyArray[i]);
            } catch (NumberFormatException ex) {
                throw new CipherException("Key value '" + keyArray[i] + "' cannot be parsed into the Integer");
            }

            if (key[i] <= 0) {
                throw new CipherException("Key value '" + key[i] + "' must be higher than 0");
            }

            if (!areRelativelyPrime(key[i], alphabetLength)) {
                throw new CipherException("Key value '" + key[i] + "' is not relatively prime to '" + alphabetLength + "'");
            }
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
        int[] key = validateKey(keyText);
        int messageLength = message.length();
        message = message.toUpperCase();
        int k0 = key[0];
        int k1 = key[1];

        Character[] result = new Character[messageLength];
        int[] resultInt = new int[messageLength];

//        for (int i = 0; i < result.length; i++) {
//            resultInt[i] = 
//        }

        return Arrays.toString(key);
    }

    @Override
    public String decrypyt(String message, String keyText) throws CipherException {
        int[] key = validateKey(keyText);

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
        return "3,5";
    }

}
