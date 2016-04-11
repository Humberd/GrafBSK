package bsk.szyfrowanie.des;

import bsk.exceptions.CipherException;
import bsk.szyfrowanie.transpozycja.Cipher;
import java.util.Arrays;

public class DESBridge implements Cipher {

    private DES des = new DES();
    private int encoding;

    private int[] binStringToBinInts(String message) throws CipherException {
        int[] result = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '0') {
                result[i] = 0;
            } else if (message.charAt(i) == '1') {
                result[i] = 1;
            } else {
                throw new CipherException("Cannot convert '" + message.charAt(i) + "' to binary value");
            }
        }

        return result;
    }
////////////////////////////////////////////

    private int[] hexStringToBinInts(String message) throws CipherException {
        int[] result = new int[message.length() * 4];
        for (int i = 0; i < message.length(); i++) {
            System.arraycopy(hexCharToBinInts(message.charAt(i)), 0, result, 4 * i, 4);
        }
        return result;
    }

    private String binIntsToHexString(int[] arr) throws CipherException{
        if (arr.length % 4 != 0) {
            throw new CipherException("Number of bits are not dividable by 4 (HEX)");
        }
        StringBuilder result = new StringBuilder(arr.length / 4);
        //TODO
        return result.toString();
    }

    private int[] hexCharToBinInts(char hex) throws CipherException {
        int hexInt = (int) hex;
        hexInt -= 48;
        if (hexInt < 0) {
            throw new CipherException("Cannot convert '" + String.valueOf(hex) + "' to hex value");
        }
        if (hexInt > 9) {
            hexInt -= 7;
            if (hexInt < 10 || hexInt > 15) {
                throw new CipherException("Cannot convert '" + String.valueOf(hex) + "' to hex value");
            }
        }
        int[] result = new int[4];
        for (int i = 0; i < result.length; i++) {
            result[result.length - 1 - i] = hexInt & 1;
            hexInt >>= 1;
        }
        return result;
    }

    private char binIntsToHexChar(int[] arr) throws CipherException {
        if (arr.length != 4) {
            throw new CipherException("Cannot convert '" + Arrays.toString(arr) + "' bits to hex char");
        }

        int hexInt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0 && arr[i] != 1) {
                throw new CipherException("Cannot convert '" + Arrays.toString(arr) + "' bits to hex char");
            }
            hexInt <<= 1;
            hexInt += arr[i];
        }

        hexInt += 48;
        if (hexInt > 57) {
            hexInt += 7;
        }
        return (char) hexInt;
    }
///////////////////////////////////////////////////////    

    private int[] asciiStringToBinInts(String message) throws CipherException {
        int[] result = new int[message.length()];

        return result;
    }

    @Override
    public String encrypt(String message, String key) throws CipherException {
        int[] newMessage = null;
        int[] newKey = null;
        switch (encoding) {
            case Cipher.BINARY:
                newMessage = binStringToBinInts(message);
                newKey = binStringToBinInts(key);
                break;
            case Cipher.HEX:
                newMessage = hexStringToBinInts(message);
                newKey = hexStringToBinInts(key);
                break;
            case Cipher.ASCII:
                newMessage = asciiStringToBinInts(message);
                newKey = hexStringToBinInts(key);
                break;
            default:
                throw new CipherException("Cannot find a valid encoding");
        }
        return Arrays.toString(newMessage);
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        return "test";
    }

    @Override
    public String getCipherName() {
        return "Data Encryption Standard";
    }

    @Override
    public String getTemplateMessage() {
        return "0123456789ABCDEF";
    }

    @Override
    public String getTemplateKey() {
        return "133457799BBCDFF1";
    }

    @Override
    public void setEncoding(int encoding) {
        this.encoding = encoding;
    }

}
