/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsk.szyfrowanie1;

import bsk.exceptions.CipherException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *
 * @author Sawik
 */
public class MatrixTransposition2 implements Cipher {

    private int[] validateKey(String keyText) throws CipherException {
        if (!Pattern.matches("[a-zA-Z]+", keyText)) {
            throw new CipherException("Key value can contain only letters");
        }

        int[] keyArray = new int[keyText.length()];
        int alphabetLength = 25;
        int firstLetter = 65;
        keyText = keyText.toUpperCase();
        int counter = 0;

        for (int i = 0; i < alphabetLength; i++) {
            for (int k = 0; k < keyText.length(); k++) {
                if ((int) keyText.charAt(k) == firstLetter + i) {
                    keyArray[k] = counter++;
                }
            }
        }

        return keyArray;
    }

    @Override
    public String encrypt(String message, String key) throws CipherException {
        int[] keyArray = validateKey(key);
        int keyLength = keyArray.length;
        int messageLength = message.length();
        int remainder = messageLength % keyLength;
        int rowsNumber = (remainder > 0) ? messageLength / keyLength + 1 : messageLength / keyLength;

        char[] result = new char[messageLength];
        int counter = 0;

        char[][] messageArray = new char[rowsNumber][keyLength];

        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < keyLength; j++) {
                messageArray[i][j] = message.charAt(counter++);
            }
        }
        counter = 0;
        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < keyLength; j++) {
//                if ()
            }
        }
        return "dupa";
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        return message;
    }

    @Override
    public String getCipherName() {
        return "Matrix Transposition (Key - Word)";
    }

    @Override
    public String getTemplateMessage() {
        return "HEREISASECRETMESSAGEENCIPHEREDBYTRANSPOSITION";
    }

    @Override
    public String getTemplateKey() {
        return "CONVENIENCE";
    }

}
