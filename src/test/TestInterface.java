package test;

public interface TestInterface {

    public void say();
    
    default void helloWorld() {
        System.out.println("HelloWorld");
    }
}
