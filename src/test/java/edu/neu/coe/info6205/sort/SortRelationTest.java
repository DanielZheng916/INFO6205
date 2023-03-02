package edu.neu.coe.info6205.sort;

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

    public static void main(String[] args) throws IOException {
//        config = Config.load(SortRelationTest.class);
//        final Config config = Config.setupConfig("false", "0", "0", "40", "");
//        final BaseHelper<Integer> helper = new InstrumentedHelper<>("test", config);
//        QuickSort<Integer> sorter = new QuickSort_DualPivot<>(helper);
        int size = 2500;
        int m = 100;
        for(int i = 0; i < 1; i++) {
            // generate input
            size *= 2;
            Integer[] input = new Integer[size];
            Random rd = new Random();
            for (int j = 0; j < size; j++) {
                input[j] = rd.nextInt(10000);
            }
            System.out.println("aaaaaaaa");
            final Config config = Config.setupConfig("true", "0", "0", "40", "");
            final Helper<Integer> helper = HelperFactory.create("fsadg", size, config);
            QuickSort<Integer> sorter = new QuickSort_DualPivot<>(helper);
            Consumer<Integer[]> fRun = b -> sorter.sort(b);
            UnaryOperator<Integer[]> fPre = b -> {
//                System.out.println("pre: ");
//                for (int a : b) {
//                    System.out.println(a);
//                }
                helper.preProcess(b);
                return Arrays.copyOf(b, b.length);
            };
            Consumer<Integer[]> fPost = b -> {
//                System.out.println("post: ");
                Arrays.sort(b);
//                for (int a : b) {
//                    System.out.println(a);
//                }
                helper.postProcess(b);
            };
            // test random
            String randomArrayDescription = "Test insertion sort on random array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun, fPost);
            double time_random = benchmark_timer_random.run(input, m);
            System.out.println(time_random);
            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
            System.out.println("--------");
            System.out.println(statPack);
            System.out.println("COMPARES:" + (int)statPack.getStatistics(InstrumentedHelper.COMPARES).mean());
            System.out.println("SWAPS:" + (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean());
            System.out.println("FIXES:" + (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean());
            System.out.println("HITS:" + (int) statPack.getStatistics(InstrumentedHelper.HITS).mean());
            System.out.println("--------");
        }
    }

}
