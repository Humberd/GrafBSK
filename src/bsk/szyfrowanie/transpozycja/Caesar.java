package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;
import java.math.BigInteger;
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

    //najwiekszy wspolny dzielnik
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

        String result = "";

        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            if (!Character.isLetter(messageChar)) {
                result += messageChar;
            } else {
                int messageInt = (int) messageChar;
                messageInt -= 65;
                int resultInt = (messageInt * k1 + k0) % 26;
                result += (char) (resultInt + 65);
            }
        }

        return result;
    }

    private int eulerFunction(int value) {
        int result = 0;

        for (int i = 1; i <= value; i++) {
            if (areRelativelyPrime(i, value)) {
                result++;
            }
        }

        return result;
    }

    @Override
    public String decrypyt(String message, String keyText) throws CipherException {
        int[] key = validateKey(keyText);
        int messageLength = message.length();
        message = message.toUpperCase();
        int k0 = key[0];
        int k1 = key[1];

        String result = "";
        int power = eulerFunction(26) - 1;
        long k1Powered = 1;
//        BigInteger k1Pow = new BigInteger(k1 + "");
//        k1Pow.modPow(new BigInteger(power + ""), new BigInteger("26"));
        for (int i = 0; i < power; i++) {
            k1Powered = (k1Powered * k1) % 26;
        }
//        System.out.println(k1Powered);
//        long k1Powered = (long) (Math.pow(k1, eulerFunction(26) - 1));
        for (int i = 0; i < messageLength; i++) {
            char messageChar = message.charAt(i);
            if (!Character.isLetter(messageChar)) {
                result += messageChar;
            } else {
                int messageInt = (int) messageChar;
                messageInt -= 65;
                long messageLong = (long) messageChar;
                messageLong -= 65;
//                long resultLong = ((messageLong + (26 - (long) k0)) * k1Pow.longValue()) % 26;
                long firstPart = (messageLong + (26 - (long) k0)) % 26;

                if (firstPart < 0) {
                    firstPart = 26 + firstPart;
                }
                System.out.println(firstPart);
//                System.out.println((messageLong + (26 - (long) k0)));
                long resultLong = (firstPart * k1Powered) % 26;
//                long resultLong = (((messageLong + (26 - (long) k0)) % 26) * k1Powered) % 26;
                result += (char) (resultLong + 65);
            }
        }

        return result;
    }

    @Override
    public String getCipherName() {
        return "Caesar (Key-number)";
    }

    @Override
    public String getTemplateMessage() {
//        return "alamakota";
        return "CRYPTOGRAPHY";
    }

    @Override
    public String getTemplateKey() {
        return "93,95";
    }

}
