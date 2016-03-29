package bsk.szyfrowanie.strumieniowe;

import bsk.exceptions.CipherException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LinearFeedbackShiftRegisterBridge implements StreamCipher {

    private int[] validatePolynomial(String polynomial) throws CipherException {
        polynomial = polynomial.replace(" ", "");
        String[] parts = polynomial.split("\\+");
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].contains("x")) {
                parts[i] = parts[i].replace("x", "");
                if (parts[i].isEmpty()) {
                    map.put(1, 1);
                } else {
                    int value = 0;
                    try {
                        value = Integer.parseInt(parts[i]);
                    } catch (Exception ex) {
                        throw new CipherException("Polynomial: (" + polynomial + ") is invalid");
                    }
                    map.put(value, value);
                }
            }
        }
        int[] result = new int[map.size()];
        int counter = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result[counter++] = entry.getKey();
        }
        return result;
    }

    private int[] validateSeed(String seed) throws CipherException {
        seed = seed.replace(" ", "");
        int[] result = new int[seed.length()];
        for (int i = 0; i < seed.length(); i++) {
            int value = 0;
            try {
                String foo = new String(new char[]{seed.charAt(i)});
                value = Integer.parseInt(foo);
            } catch (Exception ex) {
                throw new CipherException("Cannot parse seed into the Integer");
            }
            if (value != 0 && value != 1) {
                throw new CipherException("Each seed value must be between 0 and 1");
            }
            result[i] = value;
        }

        return result;
    }

    private int validateLength(String encryptionLength) throws CipherException {
        encryptionLength = encryptionLength.replace(" ", "");
        int value = 0;
        try {
            value = Integer.parseInt(encryptionLength);
        } catch (Exception ex) {
            throw new CipherException("Length must be a number");
        }
        if (value <= 0) {
            throw new CipherException("Length must be higher than 0");
        }
        return value;
    }

    @Override
    public String encrypt(String polynomial, String seed, String message, String encryptionLength) throws CipherException {
        int[] selectedFlipFlops = validatePolynomial(polynomial);
        int[] seeds = validateSeed(seed);
        int length = validateLength(encryptionLength);
//        System.out.println(Arrays.toString(selectedFlipFlops));
//        System.out.println(Arrays.toString(seeds));
//        System.out.println(length);
        
        LinearFeedbackShiftRegister register = new LinearFeedbackShiftRegister(selectedFlipFlops, seeds);
        int[] ticks = register.clockTicks(length);
        String result = "";
        for (int i=0; i<ticks.length; i++) {
            result += ticks[i];
        }
        
        return result;
    }

    @Override
    public String decrypt(String polynomial, String seed, String message) throws CipherException {
        return encrypt(polynomial, seed, message, "10");
    }

    @Override
    public String getCipherName() {
        return "Linear FeedBack Shift Register";
    }

    @Override
    public String getTemplatePolynomial() {
        return "1+x+x4";
    }

    @Override
    public String getTemplateSeed() {
        return "1001";
    }

    @Override
    public String getTemplateMessage() {
        return "";
    }

    @Override
    public String getTemplateLength() {
        return "10";
    }

}
