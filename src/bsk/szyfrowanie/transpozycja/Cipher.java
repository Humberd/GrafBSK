package bsk.szyfrowanie.transpozycja;

import bsk.exceptions.CipherException;

public interface Cipher {
    public String encrypt(String message, String key) throws CipherException;
    public String decrypyt(String message, String key) throws CipherException;
    public String getCipherName();
    public String getTemplateMessage();
    public String getTemplateKey();
}
