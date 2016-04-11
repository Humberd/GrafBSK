package bsk.szyfrowanie.transpozycja;

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
        String result = "";
        int numberOfCharsInFullBlock = 0;
        for (int k : keyArray) {
            numberOfCharsInFullBlock += k + 1;
        }

        int numberOfFullBlocks = message.length() / numberOfCharsInFullBlock;
        int numberOfCharsInLastBlock = message.length() % numberOfCharsInFullBlock;

        char[][][] blocksArray = new char[numberOfCharsInLastBlock > 0 ? numberOfFullBlocks + 1 : numberOfFullBlocks][keyArray.length][keyArray.length];
        for (int i = 0, counter = 0; i < blocksArray.length; i++) {
            for (int j = 0; j < blocksArray[i].length; j++) {
                for (int k = 0; k < blocksArray[i][j].length; k++) {
                    if (counter < message.length()) {
//                        System.out.println(i + "/" + j + "/" + k + ": " + message.charAt(counter));
                        blocksArray[i][j][k] = message.charAt(counter++);

//                        System.out.println(keyArray[j] + "==" + k);
                        if (keyArray[j] == k) {
                            break;
                        }
                    }
                }
            }
        }

        for (int t = 0; t < keyArray.length; t++) {
            for (int i = 0; i < blocksArray.length; i++) {
                for (int j = 0; j < blocksArray[i].length; j++) {
                    if (Character.compare(Character.MIN_VALUE, blocksArray[i][j][keyArray[t]]) != 0) {
                        result += blocksArray[i][j][keyArray[t]];
                    }
                }
            }
        }

        for (char[][] ii : blocksArray) {
            for (char[] j : ii) {
                for (char k : j) {
                    System.out.print(k + ",");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println(Arrays.toString(keyArray));
        return result;
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        int[] keyArray = validateKey(key);
        String result = "";
        int numberOfCharsInFullBlock = 0;
        for (int k : keyArray) {
            numberOfCharsInFullBlock += k + 1;
        }

        int numberOfFullBlocks = message.length() / numberOfCharsInFullBlock;
        int numberOfCharsInLastBlock = message.length() % numberOfCharsInFullBlock;

        char[][][] blocksArray = new char[numberOfCharsInLastBlock > 0 ? numberOfFullBlocks + 1 : numberOfFullBlocks][keyArray.length][keyArray.length];
        for (int i = 0, counter = 0; i < blocksArray.length; i++) {
            for (int j = 0; j < blocksArray[i].length; j++) {
                for (int k = 0; k < blocksArray[i][j].length; k++) {
                    if (counter < message.length()) {
                        counter++;
                        blocksArray[i][j][k] = 'I';

                        if (keyArray[j] == k) {
                            break;
                        }
                    }
                }
            }
        }

        for (int t = 0, counter = 0; t < keyArray.length; t++) {
            for (int i = 0; i < blocksArray.length; i++) {
                for (int j = 0; j < blocksArray[i].length; j++) {
                    if (Character.compare('I', blocksArray[i][j][keyArray[t]]) == 0) {
                        blocksArray[i][j][keyArray[t]] = message.charAt(counter++);
                    }
                }
            }
        }

        for (int i = 0, counter = 0; i < blocksArray.length; i++) {
            for (int j = 0; j < blocksArray[i].length; j++) {
                for (int k = 0; k < blocksArray[i][j].length; k++) {
                    if (Character.compare(Character.MIN_VALUE, blocksArray[i][j][k]) != 0) {
                        result += blocksArray[i][j][k];
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getCipherName() {
        return "Matrix Transposition (Key - Word)(Rows)";
    }

    @Override
    public String getTemplateMessage() {
//        return "HERE IS A SECRET MESSAGE ENCIPHERED BY TRANSPOSITION";
//        return "HEREISASECRETMESSAGEENCIPHEREDBYTRANSPOSITION";
//        return "HEESPNIRRSSEESEIYASCBTEMGEPNANDICTRTAHSOIEERO";
        return "ALA MA KOTA KOT MA ALE";
//        return "ALA MA KOTA";
    }

    @Override
    public String getTemplateKey() {
//        return "CONVENIENCE";
        return "ALA";
    }

    @Override
    public void setEncoding(int encoding) {
    }

}
