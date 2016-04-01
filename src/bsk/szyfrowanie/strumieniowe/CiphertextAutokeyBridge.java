package bsk.szyfrowanie.strumieniowe;

import bsk.exceptions.CipherException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CiphertextAutokeyBridge implements StreamCipher {

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

    private int[] validateBinaryStream(String seed) throws CipherException {
        seed = seed.replace(" ", "");
        int[] result = new int[seed.length()];
        for (int i = 0; i < seed.length(); i++) {
                char x = seed.charAt(i);
                if (x == '0') {
                    result[i] = 0;
                } else if (x == '1') {
                    result[i] = 1;
                } else {
                    throw new CipherException("Parsing Error");
                }
        }

        return result;
    }

    @Override
    public String encrypt(String polynomial, String seed, String message, String encryptionLength) throws CipherException {
        int[] selectedFlipFlops = validatePolynomial(polynomial);
        int[] seeds = validateBinaryStream(seed);
        int[] messages = validateBinaryStream(message);
//        System.out.println(Arrays.toString(selectedFlipFlops));
//        System.out.println(Arrays.toString(seeds));
//        System.out.println(Arrays.toString(messages));
        
        LinearFeedbackShiftRegister register = new LinearFeedbackShiftRegister(selectedFlipFlops, seeds);
        int[] cipheredMessage = register.cipherAutokeyMessage(messages);
        StringBuilder builder = new StringBuilder(cipheredMessage.length);
        for (int i: cipheredMessage) {
            builder.append(i);
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String polynomial, String seed, String message) throws CipherException {
        int[] selectedFlipFlops = validatePolynomial(polynomial);
        int[] seeds = validateBinaryStream(seed);
        int[] messages = validateBinaryStream(message);
//        System.out.println(Arrays.toString(selectedFlipFlops));
//        System.out.println(Arrays.toString(seeds));
//        System.out.println(Arrays.toString(messages));
        
        LinearFeedbackShiftRegister register = new LinearFeedbackShiftRegister(selectedFlipFlops, seeds);
        int[] cipheredMessage = register.decipherAutokeyMessage(messages);
        StringBuilder builder = new StringBuilder(cipheredMessage.length);
        for (int i: cipheredMessage) {
            builder.append(i);
        }
        return builder.toString();
    }

    @Override
    public String getCipherName() {
        return "Ciphertext Autokey";
    }

    @Override
    public String getTemplatePolynomial() {
        return "1+x+x4";
    }

    @Override
    public String getTemplateSeed() {
        return "1100";
    }

    @Override
    public String getTemplateMessage() {
        return "1101";
    }

    @Override
    public String getTemplateLength() {
        return "";
    }

}
