package grafika.PPM;

public class P3 extends PPMType {

    public P3() {
        super();
        super.setState(new P3NotStarted());
    }

    @Override
    public void readLine(String line) {
        line = line.trim();
        if (line.charAt(0) != '#') {
            getState().interpret(line);
        }
    }

    @Override
    public void saveLine() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class P3NotStarted implements PPMState {

    @Override
    public void interpret(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class P3Header implements PPMState {

    @Override
    public void interpret(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class P3ColRow implements PPMState {

    @Override
    public void interpret(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class P3BitLength implements PPMState {

    @Override
    public void interpret(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class P3Content implements PPMState {

    @Override
    public void interpret(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class P3Ended implements PPMState {

    @Override
    public void interpret(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
