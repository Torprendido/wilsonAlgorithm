
package com.control;

/**
 *
 * @author torpre
 */
public class EuclideanDistance {
    
    public EuclideanDistance() {
    }
    
    public static double distance(Object[] i, Object[] j) {
        double sumatory = 0;
        for (int index = 0; index < i.length; index++)
            sumatory += Math.pow((double) i[index] - (double) j[index], 2);
        Double distance = Math.sqrt(sumatory);
        return distance;
    }
    
    public static double distance(Double[] i, Double[] j) {
        double sumatory = 0;
        for (int index = 0; index < i.length - 1; index++)
            sumatory += Math.pow(i[index] - j[index], 2);
        Double distance = Math.sqrt(sumatory);
        return distance;
    }
}
