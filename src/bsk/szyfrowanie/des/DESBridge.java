package bsk.szyfrowanie.des;

import bsk.exceptions.CipherException;
import bsk.szyfrowanie.transpozycja.Cipher;
import java.util.Arrays;

public class DESBridge implements Cipher {

    private DES des = new DES();
    private int messageEncoding;
    private int keyEncoding;
    private int resultEncoding;

    @Override
    public String encrypt(String message, String key) throws CipherException {
        int[] newMessage = decodeString(message, this.messageEncoding);
        int[] newKey = decodeString(key, this.keyEncoding);
        return encodeBin(des.encrypt(newMessage, newKey), this.resultEncoding);
    }

    @Override
    public String decrypyt(String message, String key) throws CipherException {
        int[] newMessage = decodeString(message, this.messageEncoding);
        int[] newKey = decodeString(key, this.keyEncoding);
        return encodeBin(des.decrypt(newMessage, newKey), this.resultEncoding);
    }

    private int[] decodeString(String message, int encoding) throws CipherException {
        int[] arr = null;
        switch (encoding) {
            case Cipher.BINARY:
                arr = Converters.binStringToBinInts(message);
                break;
            case Cipher.HEX:
                arr = Converters.hexStringToBinInts(message);
                break;
            case Cipher.ASCII:
                arr = Converters.asciiStringToBinInts(message);
                break;
            default:
                throw new CipherException("Cannot find a valid encoding");
        }
        return arr;
    }

    private String encodeBin(int[] arr, int encoding) throws CipherException {
        String message = null;
        switch (encoding) {
            case Cipher.BINARY:
                message = Converters.binIntsToBinString(arr);
                break;
            case Cipher.HEX:
                message = Converters.binIntsToHexString(arr);
                break;
            case Cipher.ASCII:
                message = Converters.binIntsToAsciiString(arr);
                break;
            default:
                throw new CipherException("Cannot find a valid encoding");
        }
        return message;
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
    public void setKeyEncoding(int encoding) {
        System.out.println(encoding);
        this.keyEncoding = encoding;
    }

    @Override
    public void setMessageEncoding(int encoding) {
        this.messageEncoding = encoding;
    }

    @Override
    public void setResultEncoding(int encoding) {
        this.resultEncoding = encoding;
    }
}
