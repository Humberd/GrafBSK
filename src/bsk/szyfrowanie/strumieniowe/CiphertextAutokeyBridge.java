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
            int value = 0;
            try {
                String foo = new String(new char[]{seed.charAt(i)});
                value = Integer.parseInt(foo);
            } catch (Exception ex) {
                throw new CipherException("Cannot parse into the Integer");
            }
            if (value != 0 && value != 1) {
                throw new CipherException("Each value must be between 0 and 1");
            }
            result[i] = value;
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
        String result = "";
        for (int i = 0; i < cipheredMessage.length; i++) {
            result += cipheredMessage[i];
        }
        return result;
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
        String result = "";
        for (int i = 0; i < cipheredMessage.length; i++) {
            result += cipheredMessage[i];
        }
        return result;
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
