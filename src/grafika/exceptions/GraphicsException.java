package grafika.exceptions;

public class GraphicsException extends Exception{
    
    public GraphicsException() {
        super("Unknown error");
    }
    
    public GraphicsException(String exceptionText) {
        super(exceptionText);
    }
}
