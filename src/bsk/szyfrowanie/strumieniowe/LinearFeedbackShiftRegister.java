package bsk.szyfrowanie.strumieniowe;

import bsk.exceptions.CipherException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.SimpleThreadPool;

public class LinearFeedbackShiftRegister {

    private Dflipflop[] dffs;
    private int[] selectedFlipFlops;

    public LinearFeedbackShiftRegister(int[] selectedFlipFlops, int[] seed) throws CipherException {
        Arrays.sort(selectedFlipFlops);
        if (selectedFlipFlops[0] <= 0) {
            throw new CipherException("All numbers of selected flip flops must be higher than 0");
        }
        dffs = new Dflipflop[selectedFlipFlops[selectedFlipFlops.length - 1]];

        if (seed.length != dffs.length) {
            int[] seedTemp = new int[dffs.length];
            for (int i = 0; i < seedTemp.length; i++) {
                seedTemp[i] = seed[i % seed.length];
            }
            seed = seedTemp;
        }

        for (int i = 0; i < dffs.length; i++) {
            dffs[i] = new Dflipflop(seed[i]);
        }

        for (int i = 0; i < selectedFlipFlops.length; i++) {
            selectedFlipFlops[i]--;
        }

        this.selectedFlipFlops = selectedFlipFlops;
        System.out.println(Arrays.toString(dffs));
    }

    public int clockTick() {
        int[] valuesToXor = new int[selectedFlipFlops.length];

        int counter = 0;
        int valueToPush = dffs[0].getValue();

        if (selectedFlipFlops[counter] == 0) {
            valuesToXor[counter++] = valueToPush;
        }
        for (int i = 1; i < dffs.length; i++) {
            valueToPush = dffs[i].pushValue(valueToPush);
            if (selectedFlipFlops[counter] == i) {
                valuesToXor[counter++] = valueToPush;
            }
        }
        valueToPush = xor(valuesToXor);
        dffs[0].pushValue(valueToPush);

        return valueToPush;
    }

    public int[] clockTick(int ticks) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int[] results = new int[ticks];
        for (int i = 0; i < ticks; i++) {
            int tempI = i;
            executor.submit(() -> {
                results[tempI] = clockTick();
                System.out.println(Arrays.toString(results));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LinearFeedbackShiftRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    public int xor(int a, int b) {
        return (a == b) ? 0 : 1;
    }

    public int xor(int[] values) {
        if (values.length == 1) {
            return values[0];
        }
        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            result = xor(result, values[i]);
        }
        return result;
    }

    public static void main(String[] args) throws CipherException {
        LinearFeedbackShiftRegister sh = new LinearFeedbackShiftRegister(new int[]{1, 4}, new int[]{1, 0, 0, 1});
        sh.clockTick(10);
    }
}
