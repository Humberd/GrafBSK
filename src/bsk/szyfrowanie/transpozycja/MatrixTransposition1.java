/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;

/**
 *
 * @author Sawik
 */
public class MatrixTransposition1 implements Cipher {

    private int[] validateKey(String keyText) throws CipherException {
        String[] keyTextArray = keyText.split("-");
        int[] keyArray = new int[keyTextArray.length];
        for (int i = 0; i < keyTextArray.length; i++) {
            try {
                keyArray[i] = Integer.parseInt(keyTextArray[i]);
            } catch (NumberFormatException e) {
                throw new CipherException("Key value cannot be converted into the Integer");
            }
            if (keyArray[i] <= 0) {
                throw new CipherException("Key value must be higher than 0");
            }
            //tutaj obnizam klucz o 1, zeby pasowalo do indeksowania od 0
            keyArray[i]--;
        }
        return keyArray;
    }

    @Override
    public String encrypt(String message, String keyText) throws CipherException {
        int[] keyArray = validateKey(keyText);
        int keyLength = keyArray.length;
        int messageLength = message.length();
        int rowsNumber = (messageLength % keyLength > 0) ? messageLength / keyLength + 1 : messageLength / keyLength;

        char[] result = new char[messageLength];
        int counter = 0;

        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (keyArray[j] + (keyLength * i) < messageLength) {
                    result[counter++] = message.charAt(keyArray[j] + (keyLength * i));
                }

            }

        }
        return new String(result);
    }

    private String generateDecryptKey(String keyText) throws CipherException {
        int[] keyArray = validateKey(keyText);
        int[] intResult = new int[keyArray.length];
        for (int i = 0; i < intResult.length; i++) {
            for (int j = 0; j < keyArray.length; j++) {
                if (keyArray[j] == i) {
                    intResult[i] = j;
                    intResult[i]++;
                    break;
                }
            }

        }
        String[] stringResult = new String[intResult.length];
        for (int i = 0; i < stringResult.length; i++) {
            stringResult[i] = intResult[i] + "";
        }
        String result = String.join("-", stringResult);
        return result;
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        key = generateDecryptKey(key);
        return encrypt(message, key);
    }

    @Override
    public String getCipherName() {
        return "Matrix Transposition (Key - Number)";
    }

    @Override
    public String getTemplateMessage() {
        return "CRYPTOGRAPHYOSA";
    }

    @Override
    public String getTemplateKey() {
        return "3-1-4-2";
    }
}
