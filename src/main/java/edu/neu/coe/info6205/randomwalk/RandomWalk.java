/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.Random;

public class RandomWalk {

//    public static int[][] directions = {{1,0},
//                                        {0,1},
//                                        {-1,0},
//                                        {0,-1}};

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // FIXME do move by replacing the following code
         x += dx;
         y += dy;
        // END 
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // FIXME
        for (int i = 0; i < m; i++) {
//            int direction = random.nextInt(4);
//            move(directions[direction][0], directions[direction][1]);
            randomMove();
        }
        // END 
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // FIXME by replacing the following code
         return Math.sqrt(x*x + y*y);
        // END 
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void main(String[] args) {
//        if (args.length == 0)
//            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
//        int m = Integer.parseInt(args[0]);
//        int n = 30;
//        if (args.length > 1) n = Integer.parseInt(args[1]);
//        double meanDistance = randomWalkMulti(m, n);
//        System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
        int m = 2;
        int n = 30;
//        for (int i = 0; i < 20; i++) {
//            double meanDistance = randomWalkMulti(m, n);
////            System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
////            System.out.println(m);
//            System.out.println(meanDistance);
//            m *= 2;
//        }
        n = 1000;
        for (m = 1000; m < 7000; m += 1000) {
            double meanDistance = randomWalkMulti(m, n);
            double predictDistance = 0.8578515987*Math.pow(m, 0.4998);
            System.out.println(m + " steps experiment distance: " + meanDistance + ", predict distance: " + predictDistance);
        }
    }

}
