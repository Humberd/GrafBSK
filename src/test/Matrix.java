/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Sawik
 */
public class Matrix {

    public String szyfruj(String text, int d, int[] tab) {
        String newText = "";
        int size = text.length();

        //int[] tab = {2,0,3,1};
        int x = 0;
        int rows = (int) Math.ceil((double) size / d);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < d; j++) {
                if (tab[j] + x < size) {
                    newText += text.charAt(tab[j] + x);
                }
            }
            x += d;
        }

        return newText;
    }

    public String deszyfruj(String text, int d) {
        String newText = "";
        int size = text.length();

        int[] tab = {2, 0, 3, 1};
        int[] tab2 = {2, 0, 3, 1};
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (tab[j] == i) {
                    tab2[i] = j;
                }
            }
        }
        for (int j = 0; j < d; j++) {
            tab[j] = tab2[j];
        }
        int x = 0;
        int rows = (int) Math.ceil((double) size / d);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < d; j++) {

                if (i == rows - 1 && rows * d > size && d - (d * rows - size) >= 3) {
                    if (d - (d * rows - size) == 3) {
                        newText = newText + text.charAt(x + 1) + text.charAt(x + 2) + text.charAt(x);
                    }
                    if (d - (d * rows - size) == 4) {
                        newText = newText + text.charAt(x + 2) + text.charAt(x + 3) + text.charAt(x) + text.charAt(x + 1);
                    }
                    break;
                }
                if (tab[j] + x < size) {
                    newText += text.charAt(tab[j] + x);
                }
            }
            x += d;
        }

        return newText;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Matrix mat = new Matrix();

        Scanner sc = new Scanner(System.in);
        System.out.println("Szyfracja=1 / Deszyfracja=2");
        int wybierz = sc.nextInt();
        if (wybierz == 1) {
            System.out.println("Podaj tekst do szyfracji:");
            String s1 = sc.next();
            System.out.println("Podaj d:");
            int i1 = sc.nextInt();
            System.out.println("Podaj klucz:");
            
            sc.nextLine();
            String text = sc.nextLine();

            text = text.replaceAll(" ", "");
            String[] keysString = text.split("-");
            int[] keyArray = new int[keysString.length];
            int counter = 0;
            for (String key : keysString) {
                try {
                    keyArray[counter++] = Integer.parseInt(key);
                } catch (Exception e) {
                }
            }
            System.out.println(Arrays.toString(keyArray));

            System.out.println("Wynik:");
            System.out.println(mat.szyfruj(s1, i1, keyArray));
        } else if (wybierz == 2) {
            System.out.println("Podaj tekst do deszyfracji");
            String s1 = sc.next();
            System.out.println("Podaj d:");
            int i1 = sc.nextInt();
            System.out.println("Wynik:");
            System.out.println(mat.deszyfruj(s1, i1));
        }
    }

}
