/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Class InsertionSort.
 *
 * @param <X> the underlying comparable type.
 */
public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        // FIXME
//        for (int i, k = from; ++k < to; ) {
//            X xsi = xs[i = k];
//
//            if (xsi.compareTo(xs[i - 1]) < 0) {
//                while (--i >= from && xsi.compareTo(xs[i]) < 0) {
//                    xs[i + 1] = xs[i];
//                }
//                xs[i + 1] = xsi;
//            }
//        }
//        Arrays.sort();
        for (int k,i = from+1; i < to; i++) {
            k = i;
            while (k > from && helper.swapStableConditional(xs, k)) k--;
        }
        // END 
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    public static void main(String[] args) {
        /*int size = 20;
        Integer[] input = new Integer[size];
        Random rd = new Random();
        for (int j = 0; j < size; j++) {
            input[j] = rd.nextInt();
        }
        Integer[] inputSorted = Arrays.copyOf(input, size);
        Arrays.sort(inputSorted);
        Integer[] inputReversed = Arrays.copyOf(input, size);
        Arrays.sort(inputReversed, Collections.reverseOrder());
        Integer[] inputHalfSorted = Arrays.copyOf(input, size);
        Arrays.sort(inputHalfSorted, 0, size/2);
        System.out.println("Random");
        for (Integer integer : input) {
            System.out.println(integer);
        }
        System.out.println();
        System.out.println("Sorted");
        for (Integer integer : inputSorted) {
            System.out.println(integer);
        }
        System.out.println();
        System.out.println("Reversed");
        for (Integer integer : inputReversed) {
            System.out.println(integer);
        }
        System.out.println();
        System.out.println("HalfSorted");
        for (Integer integer : inputHalfSorted) {
            System.out.println(integer);
        }*/

        int size = 2500, m = 100;
        for(int i = 0; i < 6; i++) {
            // generate input
            size *= 2;
            Integer[] input = new Integer[size];
            Random rd = new Random();
            for (int j = 0; j < size; j++) {
                input[j] = rd.nextInt();
            }
            Integer[] inputSorted = Arrays.copyOf(input, size);
            Arrays.sort(inputSorted);
            Integer[] inputReversed = Arrays.copyOf(input, size);
            Arrays.sort(inputReversed, Collections.reverseOrder());
            Integer[] inputHalfSorted = Arrays.copyOf(input, size);
            Arrays.sort(inputHalfSorted, 0, size/2);
            Consumer<Integer[]> fRun = b -> InsertionSort.sort(b);
            UnaryOperator<Integer[]> fPre = b -> Arrays.copyOf(b, b.length);
            // test random
            String randomArrayDescription = "Test insertion sort on random array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_random = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun);
            double time_random = benchmark_timer_random.run(input, m);
            System.out.println(time_random);
            // test sorted
            String sortedArrayDescription = "Test insertion sort on sorted array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_sorted = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun);
            double time_sorted = benchmark_timer_sorted.run(inputSorted, m);
            System.out.println(time_sorted);
            // test reversed
            String reversedArrayDescription = "Test insertion sort on reversed array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_reversed = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun);
            double time_reversed = benchmark_timer_reversed.run(inputReversed, m);
            System.out.println(time_reversed);
            // test half sorted
            String halfSortedArrayDescription = "Test insertion sort on half sorted array. Size: " + size;
            Benchmark<Integer[]> benchmark_timer_half_sorted = new Benchmark_Timer<>(randomArrayDescription,fPre,fRun);
            double time_half_sorted = benchmark_timer_half_sorted.run(inputHalfSorted, m);
            System.out.println(time_half_sorted);
        }
    }
}
