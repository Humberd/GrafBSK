package grafika.exceptions;

public class FileException extends Exception{
    
    public FileException() {
        super("Unknown error");
    }
    
    public FileException(String exceptionText) {
        super(exceptionText);
    }
    
}
