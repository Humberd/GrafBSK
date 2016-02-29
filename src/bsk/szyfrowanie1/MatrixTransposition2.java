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
        return encrypt(message, validateKey(key));
    }

    private String encrypt(String message, int[] keyArray) throws CipherException {
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

    private int[] generateDecryptKey(String keyText) throws CipherException {
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
    public String decrypyt(String message, String key) throws CipherException {
        int[] keyArray = generateDecryptKey(key);
        int keyLength = keyArray.length;
        int messageLength = message.length();
        int remainder = keyLength - (messageLength % keyLength);
        int rowsNumber = (messageLength % keyLength > 0) ? messageLength / keyLength + 1 : messageLength / keyLength;

        char[] result = new char[messageLength];
        int counter = 0;

        int amount = 0;
        for (int i = 0; i < keyLength; i++) {
            
            if ((keyArray[i] + remainder) > keyLength) {
                amount++;
            }
            System.out.println(keyArray[i]+" + "+remainder+" > "+keyLength);
            System.out.println(amount);

//            for (int j = 0; j < (amount > 0 ? rowsNumber - 1 : rowsNumber); j++) {
////                result[keyArray[i] * j - amount] = message.charAt()
//                System.out.println(j * rowsNumber + keyArray[i]);
//            }
//            System.out.println("________");
//            int addAmount = 0;
//            if (i + remainder > keyLength) {
//                addAmount = 0;
//            } else {
//                addAmount++;
//            }
//            for (int j = 0; j < rowsNumber; j++) {
//                if (addAmount > 0) {
//                    if (rowsNumber - 1 == j) {
//                        break;
//                    }
//                }
//                if (keyArray[i] * rowsNumber - addAmount < messageLength) {
//                    result[counter++] = message.charAt(keyArray[i] * rowsNumber - addAmount);
//                }
//                if (j * keyLength + i < messageLength) {
//                    result[counter++] = message.charAt(j * keyLength + keyArray[i]);
//                    System.out.println(j * keyLength + keyArray[i]);
//                }
//            }
        }
        return new String(result);
//        return encrypt(message, generateDecryptKey(key));
    }

    @Override
    public String getCipherName() {
        return "Matrix Transposition (Key - Word)";
    }

    @Override
    public String getTemplateMessage() {
//        return "HEREISASECRETMESSAGEENCIPHEREDBYTRANSPOSITION";
        return "HECRNCEYIISEPSGDIRNTOAAESRMPNSSROEEBTETIAEEHS";
    }

    @Override
    public String getTemplateKey() {
        return "CONVENIENCE";
    }

}
