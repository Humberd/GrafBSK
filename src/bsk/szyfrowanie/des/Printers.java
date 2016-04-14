package bsk.szyfrowanie.des;

import java.util.Arrays;

public class Printers {

    public static void printArray(int[][] array) {
        System.out.println("========");
        System.out.println(toString(array));
    }
    
    public static void printArray(int[] array) {
        System.out.println("--------");
        System.out.println(Arrays.toString(array));
    }

    public static String toString(int[][] array) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append(":").append(i).append(" -> ");
            builder.append(Arrays.toString(array[i]));
            if (i != array.length - 1) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
