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

        int[] keyResult = new int[keyArray.length];
        counter = 0;
        for (int i = 0; i < keyResult.length; i++) {
            for (int j = 0; j < keyArray.length; j++) {
                if (keyArray[j] == i) {
                    keyResult[counter++] = j;
                    break;
                }
            }
        }

        return keyResult;
    }

    @Override
    public String encrypt(String message, String key) throws CipherException {
        int[] keyArray = validateKey(key);
        int keyLength = keyArray.length;
        int messageLength = message.length();
        int rowsNumber = (messageLength % keyLength > 0) ? messageLength / keyLength + 1 : messageLength / keyLength;

        char[] result = new char[messageLength];
        int counter = 0;

        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                if (keyArray[i] + (keyLength * j) < messageLength) {
                    result[counter++] = message.charAt(keyArray[i] + (keyLength * j));
                }
            }
        }
        return new String(result);
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        int[] keyArray = validateKey(key);
        int keyLength = keyArray.length;
        int messageLength = message.length();
        int remainder = messageLength % keyLength;
        int rowsNumber = (messageLength % keyLength > 0) ? messageLength / keyLength + 1 : messageLength / keyLength;

        char[] result = new char[messageLength];
        int counter = 0;

        char[][] messageArray = new char[rowsNumber][keyLength];

        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                if (j == rowsNumber - 1) {
                    if (keyArray[i] < remainder) {
                        messageArray[j][keyArray[i]] = message.charAt(counter++);
                    }
                } else {
                    messageArray[j][keyArray[i]] = message.charAt(counter++);
                }
            }
        }

        counter = 0;
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (counter < result.length) {
                    result[counter++] = messageArray[i][j];
                }
            }
        }

        return new String(result);
    }

    @Override
    public String getCipherName() {
        return "Matrix Transposition (Key - Word)(Columns)";
    }

    @Override
    public String getTemplateMessage() {
        return "HERE IS A SECRET MESSAGE ENCIPHERED BY TRANSPOSITION";
//        return "HEREISASECRETMESSAGEENCIPHEREDBYTRANSPOSITION";
    }

    @Override
    public String getTemplateKey() {
        return "CONVENIENCE";
    }

}
