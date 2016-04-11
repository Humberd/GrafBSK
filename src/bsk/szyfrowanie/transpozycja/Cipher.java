package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;

public interface Cipher {
    public static final int BINARY = 0;
    public static final int HEX = 1;
    public static final int ASCII = 2;
    
    public String encrypt(String message, String key) throws CipherException;
    public String decrypyt(String message, String key) throws CipherException;
    public String getCipherName();
    public String getTemplateMessage();
    public String getTemplateKey();
    public void setEncoding(int encoding);
}
