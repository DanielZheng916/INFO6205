package edu.neu.coe.info6205.sort;

import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortRelationTest {

    private static Config config;

    public static void testQuickSort() throws IOException {
        int size = 5000;
        int m = 20;
        for(int i = 0; i < 5; i++) {
            // generate input
            size *= 2;
            Integer[] input = new Integer[size];
            Random rd = new Random();
            for (int j = 0; j < size; j++) {
                input[j] = rd.nextInt();
            }
            // without instrumenting
            final Config config0 = Config.setupConfig("false", "0", "0", "40", "");
            final Helper<Integer> helper0 = HelperFactory.create("quick sort without Instrumenting", size, config0);
            QuickSort<Integer> sorter0 = new QuickSort_DualPivot<>(helper0);
            Consumer<Integer[]> fRun0 = b -> sorter0.sort(b);
            UnaryOperator<Integer[]> fPre0 = b -> {
                return Arrays.copyOf(b, b.length);
            };
            String randomArrayDescription0 = "Test quick sort dual pivot without instrumenting. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random0 = new Benchmark_Timer<>(randomArrayDescription0,fPre0,fRun0);
            double time_random0 = benchmark_timer_random0.run(input, m);
            System.out.println("Timing without instrumenting: " + time_random0);
            // with instrumenting
            final Config config = Config.setupConfig("true", "0", "0", "40", "");
            final Helper<Integer> helper = HelperFactory.create("fsadg", size, config);
            QuickSort<Integer> sorter = new QuickSort_DualPivot<>(helper);
            Consumer<Integer[]> fRun = b -> sorter.sort(b);
            UnaryOperator<Integer[]> fPre = b -> {
                helper.preProcess(b);
                return Arrays.copyOf(b, b.length);
            };
            Consumer<Integer[]> fPost = b -> {
                Arrays.sort(b);
                helper.postProcess(b);
            };
            // test random
            String randomArrayDescription = "Test insertion sort on random array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun, fPost);
            double time_random = benchmark_timer_random.run(input, m);
            System.out.println("Timing with instrumenting: " + time_random);
            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
            System.out.println("--------");
            System.out.println(statPack);
            System.out.println("COMPARES:" + (int)statPack.getStatistics(InstrumentedHelper.COMPARES).mean());
            System.out.println("SWAPS:" + (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean());
            System.out.println("FIXES:" + (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean());
            System.out.println("HITS:" + (int) statPack.getStatistics(InstrumentedHelper.HITS).mean());
            System.out.println("COPIES:" + (int) statPack.getStatistics(InstrumentedHelper.COPIES).mean());
            System.out.println("--------");
        }
    }

    public static void testMergeSort() throws IOException {
        int size = 5000;
        int m = 20;
        for(int i = 0; i < 5; i++) {
            // generate input
            size *= 2;
            Integer[] input = new Integer[size];
            Random rd = new Random();
            for (int j = 0; j < size; j++) {
                input[j] = rd.nextInt();
            }
            // without instrumenting
            final Config config0 = Config.setupConfig("false", "0", "0", "40", "");
            final Helper<Integer> helper0 = HelperFactory.create("merge sort without Instrumenting", size, config0);
            MergeSort<Integer> sorter0 = new MergeSort<>(helper0);
            Consumer<Integer[]> fRun0 = b -> sorter0.sort(b);
            UnaryOperator<Integer[]> fPre0 = b -> {
                return Arrays.copyOf(b, b.length);
            };
            String randomArrayDescription0 = "Test merge sort dual pivot without instrumenting. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random0 = new Benchmark_Timer<>(randomArrayDescription0,fPre0,fRun0);
            double time_random0 = benchmark_timer_random0.run(input, m);
            System.out.println("Timing without instrumenting: " + time_random0);
            // with instrumenting
            final Config config = Config.setupConfig("true", "0", "0", "40", "");
            final Helper<Integer> helper = HelperFactory.create("fsadg", size, config);
            MergeSort<Integer> sorter = new MergeSort<>(helper);
            Consumer<Integer[]> fRun = b -> sorter.sort(b);
            UnaryOperator<Integer[]> fPre = b -> {
                helper.preProcess(b);
                return Arrays.copyOf(b, b.length);
            };
            Consumer<Integer[]> fPost = b -> {
                Arrays.sort(b);
                helper.postProcess(b);
            };
            // test random
            String randomArrayDescription = "Test insertion sort on random array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun, fPost);
            double time_random = benchmark_timer_random.run(input, m);
            System.out.println("Timing with instrumenting: " + time_random);
            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
            System.out.println("--------");
            System.out.println(statPack);
            System.out.println("COMPARES:" + (int)statPack.getStatistics(InstrumentedHelper.COMPARES).mean());
            System.out.println("SWAPS:" + (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean());
            System.out.println("FIXES:" + (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean());
            System.out.println("HITS:" + (int) statPack.getStatistics(InstrumentedHelper.HITS).mean());
            System.out.println("COPIES:" + (int) statPack.getStatistics(InstrumentedHelper.COPIES).mean());
            System.out.println("--------");
        }
    }

    public static void testHeapSort() throws IOException {
        int size = 5000;
        int m = 20;
        for(int i = 0; i < 5; i++) {
            // generate input
            size *= 2;
            Integer[] input = new Integer[size];
            Random rd = new Random();
            for (int j = 0; j < size; j++) {
                input[j] = rd.nextInt();
            }
            // without instrumenting
            final Config config0 = Config.setupConfig("false", "0", "0", "40", "");
            final Helper<Integer> helper0 = HelperFactory.create("heap sort without Instrumenting", size, config0);
            HeapSort<Integer> sorter0 = new HeapSort<>(helper0);
            Consumer<Integer[]> fRun0 = b -> sorter0.sort(b);
            UnaryOperator<Integer[]> fPre0 = b -> {
                return Arrays.copyOf(b, b.length);
            };
            String randomArrayDescription0 = "Test heap sort without instrumenting. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random0 = new Benchmark_Timer<>(randomArrayDescription0,fPre0,fRun0);
            double time_random0 = benchmark_timer_random0.run(input, m);
            System.out.println("Timing without instrumenting: " + time_random0);
            // with instrumenting
            final Config config = Config.setupConfig("true", "0", "0", "40", "");
            final Helper<Integer> helper = HelperFactory.create("fsadg", size, config);
            HeapSort<Integer> sorter = new HeapSort<>(helper);
            Consumer<Integer[]> fRun = b -> sorter.sort(b);
            UnaryOperator<Integer[]> fPre = b -> {
                helper.preProcess(b);
                return Arrays.copyOf(b, b.length);
            };
            Consumer<Integer[]> fPost = b -> {
                Arrays.sort(b);
                helper.postProcess(b);
            };
            // test random
            String randomArrayDescription = "Test insertion sort on random array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun, fPost);
            double time_random = benchmark_timer_random.run(input, m);
            System.out.println("Timing with instrumenting: " + time_random);
            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
            System.out.println("--------");
            System.out.println(statPack);
            System.out.println("COMPARES:" + (int)statPack.getStatistics(InstrumentedHelper.COMPARES).mean());
            System.out.println("SWAPS:" + (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean());
            System.out.println("FIXES:" + (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean());
            System.out.println("HITS:" + (int) statPack.getStatistics(InstrumentedHelper.HITS).mean());
            System.out.println("COPIES:" + (int) statPack.getStatistics(InstrumentedHelper.COPIES).mean());
            System.out.println("--------");
        }
    }

    public static void main(String[] args) throws IOException {
        testHeapSort();
    }

}
