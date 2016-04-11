package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;

public class Vigenere implements Cipher {

    private String validateKey(String message, String key) {
        key = key.toUpperCase();
        String returnKey = "";
        for (int i = 0, k = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                returnKey += key.charAt(k++ % key.length());
            }
        }
        return returnKey;
    }

    @Override
    public String encrypt(String message, String key) throws CipherException {
        message = message.toUpperCase();
        key = validateKey(message, key);
        String result = "";

        for (int i = 0, k = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            if (!Character.isLetter(messageChar)) {
                result += messageChar;
            } else {
                int messageInt = (int) messageChar;
                messageInt -= 65;
                int keyInt = (int) key.charAt(k++);
                keyInt -= 65;
                int resultInt = (messageInt + keyInt) % 26;
                result += (char) (resultInt + 65);
            }
        }
        return result;
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        message = message.toUpperCase();
        key = validateKey(message, key);
        String result = "";

        for (int i = 0, k = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            if (!Character.isLetter(messageChar)) {
                result += messageChar;
            } else {
                int messageInt = (int) messageChar;
                messageInt -= 65;
                int keyInt = (int) key.charAt(k++);
                keyInt -= 65;
                int resultInt = (messageInt + (26 - keyInt)) % 26;
                result += (char) (resultInt + 65);
            }
        }

        return result;
    }

    @Override
    public String getCipherName() {
        return "Vigenere";
    }

    @Override
    public String getTemplateMessage() {
        return "CRYPTOGRAPHY";
    }

    @Override
    public String getTemplateKey() {
        return "BREAK";
    }

    @Override
    public void setEncoding(int encoding) {
    }

}
