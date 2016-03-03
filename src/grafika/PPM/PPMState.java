package grafika.PPM;

import grafika.exceptions.FileException;

public interface PPMState {
    public void interpret(String line, PPMType source) throws FileException;
}
