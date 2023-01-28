package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;

import java.util.*;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();
        // FIXME : for each candidate, test if a[i] + a[j] + a[k] = 0.
        int i = j - 1, k = j + 1;
        while (i >= 0 && k < length) {
            int sum = a[i] + a[j] + a[k];
            if (sum == 0) {
                triples.add(new Triple(a[i], a[j], a[k]));
                i--;
                k++;
            } else if (sum > 0) {
                i--;
            } else {
                k++;
            }
        }
        // END 
        return triples;
    }

    private final int[] a;
    private final int length;

    public static void main(String[] args) {
        int inputSize = 300;
        int tries = 5;
        for (int times = 0; times < 8; times++) {
            inputSize = inputSize * 2;
            int[] input = new int[inputSize];
            Random random = new Random();
            // generate data
            for (int i = 0; i < inputSize; i++) {
                input[i] = random.nextInt(2000000001) - 1000000000;
            }
            // sort data
            Arrays.sort(input);
            // init ThreeSum
            ThreeSum threeSum1 = new ThreeSumCubic(input);
            ThreeSum threeSum2 = new ThreeSumQuadrithmic(input);
            ThreeSum threeSum3 = new ThreeSumQuadratic(input);
            ThreeSum threeSum4 = new ThreeSumQuadraticWithCalipers(input);
            // Run
            try(Stopwatch stopwatch = new Stopwatch()) {
                for (int i = 0; i < tries; i++) {
                    threeSum1.getTriples();
                }
                System.out.println("Size: " + inputSize + "    ThreeSumCubic time:" + stopwatch.lap()/tries);
            }
            try(Stopwatch stopwatch = new Stopwatch()) {
                for (int i = 0; i < tries; i++) {
                    threeSum2.getTriples();
                }
                System.out.println("Size: " + inputSize + "    ThreeSumQuadrithmic time:" + stopwatch.lap()/tries);
            }
            try(Stopwatch stopwatch = new Stopwatch()) {
                for (int i = 0; i < tries; i++) {
                    threeSum3.getTriples();
                }
                System.out.println("Size: " + inputSize + "    ThreeSumQuadratic time:" + stopwatch.lap()/tries);
            }
            try(Stopwatch stopwatch = new Stopwatch()) {
                for (int i = 0; i < tries; i++) {
                    threeSum4.getTriples();
                }
                System.out.println("Size: " + inputSize + "    ThreeSumQuadraticWithCalipers time:" + stopwatch.lap()/tries);
            }

        }
//        ThreeSumQuadratic threeSumQuadratic = new ThreeSumQuadratic(new int[]{-2,0,2});
//        System.out.println(threeSumQuadratic.getTriples());
    }
}
