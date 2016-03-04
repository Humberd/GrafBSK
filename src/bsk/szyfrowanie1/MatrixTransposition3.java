package bsk.szyfrowanie1;

import bsk.exceptions.CipherException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MatrixTransposition3 implements Cipher {

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
        int rowsNumber = 0;
        int tempSum = 0;
        for (int i = 0; i < keyLength; i++) {
            if (tempSum < messageLength) {
//                System.out.print(tempSum + " + " + (keyArray[i] + 1) + " = ");
                tempSum += keyArray[i] + 1;
//                System.out.println(tempSum);
                rowsNumber++;
            } else {
                break;
            }
        }
        Character[][] messageArray = new Character[rowsNumber][keyLength];
        int counter = 0;
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < keyArray[i] + 1; j++) {
                if (counter < messageLength) {
                    messageArray[i][j] = message.charAt(counter++);
                }
            }
        }

//        for (int i = 0; i < rowsNumber; i++) {
//            System.out.println(Arrays.toString(messageArray[i]));
//        }
        char[] result = new char[messageLength];
        counter = 0;
        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                if (messageArray[j][keyArray[i]] != null) {
                    result[counter++] = messageArray[j][keyArray[i]];
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
        int rowsNumber = 0;
        int tempSum = 0;
        for (int i = 0; i < keyLength; i++) {
            if (tempSum < messageLength) {
//                System.out.print(tempSum + " + " + (keyArray[i] + 1) + " = ");
                tempSum += keyArray[i] + 1;
//                System.out.println(tempSum);
                rowsNumber++;
            } else {
                break;
            }
        }

        //wypelniam tablice w miejscach, w ktorych bedzie mozna wpisac liczby
        Character[][] messageArray = new Character[rowsNumber][keyLength];
        int counter = 0;
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < keyArray[i] + 1; j++) {
                if (counter < messageLength) {
                    messageArray[i][j] = '.';
                    counter++;
                }
            }
        }

        //wypelniam tablice [][] danymi
        counter = 0;
        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                if (messageArray[j][keyArray[i]] != null) {
                    messageArray[j][keyArray[i]] = message.charAt(counter++);
                }
            }
        }
        
//        for (int i = 0; i < rowsNumber; i++) {
//            System.out.println(Arrays.toString(messageArray[i]));
//        }
        
        char[] result = new char[messageLength];
        counter = 0;
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (messageArray[i][j] != null) {
                    result[counter++] = messageArray[i][j];
                } else {
                    break;
                }
            }
        }

        return new String(result);
    }

    @Override
    public String getCipherName() {
        return "Matrix Transposition (Key - Word)(Rows)";
    }

    @Override
    public String getTemplateMessage() {
        return "HERE IS A SECRET MESSAGE ENCIPHERED BY TRANSPOSITION";
//        return "HEREISASECRETMESSAGEENCIPHEREDBYTRANSPOSITION";
//        return "HEESPNIRRSSEESEIYASCBTEMGEPNANDICTRTAHSOIEERO";
    }

    @Override
    public String getTemplateKey() {
        return "CONVENIENCE";
    }

}
