package bsk.szyfrowanie.strumieniowe;

public class Dflipflop {

    private int value;

    public Dflipflop(int seed) {
        this.value = seed;
    }

    public int pushValue(int newValue) {
        int oldValue = this.value;
        this.value = newValue;
        return oldValue;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value + "";
    }

}
