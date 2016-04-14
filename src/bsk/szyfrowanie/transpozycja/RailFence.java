package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;

public class RailFence implements Cipher {

    public RailFence() {

    }

    private int parseInt(String text) throws CipherException {
        int number;
        try {
            number = Integer.parseInt(text);
        } catch (Exception e) {
            throw new CipherException("Key value cannot be converted into the Integer");
        }

        return number;
    }

    private String removeWhiteSpaces(String text) {
        return text.replaceAll(" ", "");
    }

    private int validateKey(String text) throws CipherException {
        int key = parseInt(removeWhiteSpaces(text));
        if (key <= 0) {
            throw new CipherException("Key value must be higher than 0");
        }
        return key;
    }

    @Override
    public String encrypt(String message, String keyText) throws CipherException {
        int key = validateKey(keyText);
        if (key == 1) {
            return message;
        }
        
        int length = message.length();

        char[] result = new char[length];
        int counter = 0;

        int firstRowLength = length / (2 * key - 2);
        firstRowLength += length % (2 * key - 2) > 0 ? 1 : 0;

        for (int i = 0; i < firstRowLength; i++) {
            result[counter++] = message.charAt((2 * key - 2) * i);
        }
        for (int j = 0; j < key - 2; j++) {
            for (int i = 0; i < firstRowLength + 1; i++) {//+1 wirtualne odpalenie
                if (((2 * key - 2) * i - (j + 1)) > 0 && ((2 * key - 2) * i - (j + 1)) < length) {
                    result[counter++] = message.charAt((2 * key - 2) * i - (j + 1));
                }
                if (((2 * key - 2) * i + (j + 1)) > 0 && ((2 * key - 2) * i + (j + 1)) < length) {
                    result[counter++] = message.charAt((2 * key - 2) * i + (j + 1));
                }
            }
        }
        for (int i = 0; i < firstRowLength; i++) {
            if ((((2 * key - 2) * i) + key - 1) < length) {
                result[counter++] = message.charAt((((2 * key - 2) * i) + key - 1));
            }
        }

        return new String(result);
    }

    @Override
    public String decrypyt(String message, String keyText) throws CipherException {
        int key = validateKey(keyText);
        if (key == 1) {
            return message;
        }
        
        int length = message.length();

        int[] result = new int[length];
        int counter = 0;

        int firstRowLength = length / (2 * key - 2);
        firstRowLength += length % (2 * key - 2) > 0 ? 1 : 0;

        for (int i = 0; i < firstRowLength; i++) {
            result[counter++] = (2 * key - 2) * i;
        }
        for (int j = 0; j < key - 2; j++) {
            for (int i = 0; i < firstRowLength + 1; i++) {//+1 wirtualne odpalenie
                if (((2 * key - 2) * i - (j + 1)) > 0 && ((2 * key - 2) * i - (j + 1)) < length) {
                    result[counter++] = (2 * key - 2) * i - (j + 1);
                }
                if (((2 * key - 2) * i + (j + 1)) > 0 && ((2 * key - 2) * i + (j + 1)) < length) {
                    result[counter++] = (2 * key - 2) * i + (j + 1);
                }
            }
        }
        for (int i = 0; i < firstRowLength; i++) {
            if ((((2 * key - 2) * i) + key - 1) < length) {
                result[counter++] = (((2 * key - 2) * i) + key - 1);
            }
        }

        char[] finalResult = new char[length];
        for (int i = 0; i < length; i++) {
            finalResult[result[i]] = message.charAt(i);
        }

        return new String(finalResult);
    }

    @Override
    public String getCipherName() {
        return "Rail Fence";
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
