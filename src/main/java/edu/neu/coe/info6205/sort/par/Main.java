package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        testCutoff(32000000);
    }

    private static void testCutoff(int size) {
        //        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        // customize thread pool
        int threadCount = 32;
        ForkJoinPool myPool = new ForkJoinPool(threadCount);
        Random random = new Random();
        int[] array = new int[size];
        System.out.println("Warm Up Start");
        for (int j = 50; j < 60; j++) {
            ParSort.cutoff = 10000 * (j + 1);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length, myPool);
            }
        }
        System.out.println("Warm Up End");
        ArrayList<Long> timeList = new ArrayList<>();
        ParSort.cutoff = 2000;
        while (ParSort.cutoff < size) {
            ParSort.cutoff *= 2;
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length, myPool);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);


            System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");

        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result_cutoff_"+size+".csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 1;
            for (long i : timeList) {
                String content = (double) 4000 * j + "," + (double) i / 10 + "\n";
                j*=2;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testThread() {
        ParSort.cutoff = 250000;
        Random random = new Random();
        int[] array = new int[2000000];
        System.out.println("Warm Up Start");
        for (int threadCount = 1; threadCount <= 256; threadCount *= 2) {
            ForkJoinPool myPool = new ForkJoinPool(threadCount);
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length, myPool);
            }

        }
        System.out.println("Warm Up End");
        ArrayList<Long> timeList = new ArrayList<>();
        for (int threadCount = 1; threadCount <= 4096; threadCount *= 2) {
            ForkJoinPool myPool = new ForkJoinPool(threadCount);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length, myPool);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);


            System.out.println("Thread：" + (threadCount) + "\t\t10times Time:" + time + "ms");

        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result_thread.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 1;
            for (long i : timeList) {
                String content = (int) j + "," + (double) i / 10 + "\n";
                j *= 2;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
