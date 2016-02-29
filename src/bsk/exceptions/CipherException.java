package bsk.exceptions;

public class CipherException extends Exception{
    
    public CipherException() {
        super("Unknown error");
    }
    
    public CipherException(String exceptionText) {
        super(exceptionText);
    }
}
