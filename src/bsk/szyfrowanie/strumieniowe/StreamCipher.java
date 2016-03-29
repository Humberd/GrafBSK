package bsk.szyfrowanie.strumieniowe;

import bsk.exceptions.CipherException;

public interface StreamCipher{
    public String encrypt(String polynomial, String seed, String message, String encryptionLength) throws CipherException;
    public String decrypt(String polynomial, String seed, String message) throws CipherException;
    public String getCipherName();
    public String getTemplatePolynomial();
    public String getTemplateSeed();
    public String getTemplateMessage();
    public String getTemplateLength();
}
