package test;

public class TestClass implements TestInterface{

    @Override
    public void say() {
        System.out.println("I say something");
    }

    @Override
    public void helloWorld() {
        System.out.println("I changed it");
    }
    
    
    
    public static void main(String[] args) {
        TestClass t = new TestClass();
        t.say();
        t.helloWorld();
    }

}
