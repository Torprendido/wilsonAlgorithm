
package com.control;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author torpre
 */
public class Wilson {
    
    private final int k;
    private final Object[][] sample;
    private final ArrayList<Object[]> newSample;
    
    public Wilson(Object[][] sample, int k) {
        if (k % 2 == 1 & k > 0) {
            this.sample = sample;
            this.k = k;
            this.newSample = new ArrayList<>();
            calculateAlgoritm();
        } else
            throw new IllegalArgumentException("K no es un numero válido: " + k);
    }
    
    private void calculateAlgoritm() {
        Double eucDistance;
        Distance distance;
        ArrayList<Distance> distances;
        for (Object[] i: sample) {
            distances = new ArrayList<>();
            for (Object[] j: sample)
                if (i != j) {
                    eucDistance = EuclideanDistance.distance(
                            Arrays.copyOfRange(i, 1, i.length - 1),
                            Arrays.copyOfRange(j, 1, j.length - 1)
                    );
                    distance = new Distance(eucDistance, j[j.length - 1]);
                    distances.add(distance);
                }
            int success = 0;
            String labelI = i[i.length - 1] + "";
            String labelAux;
            for (int index = 0; index < k; index++) {
                distance = obtainMin(distances);
                distances.remove(distance);
                labelAux = distance.getLabel() + "";
                if (labelI.compareTo(labelAux) == 0)
                    success ++;
            }
            if (success > k/2)
                newSample.add(i);
                
        }
    }
    
    private Distance obtainMin(ArrayList<Distance> distances) {
        Double min = Double.MAX_VALUE;
        Distance dis = null;
        for (Distance i : distances)
            if (i.getDistance() < min) {
                min = i.getDistance();
                dis = i;
            }
        return dis;
    }
    
    public  Object[][] getNewSample() {
        Object[][] array = new Object[newSample.size()][sample[0].length];
        for (int i = 0; i < newSample.size(); i++) {
            array[i] = newSample.get(i);
        }
        return array;
    }
    
    private class Distance {
        
        private final Double distance;
        private final Object label;

        public Distance(Double distance, Object label) {
            this.distance = distance;
            this.label = label;
        }

        public Double getDistance() {
            return distance;
        }

        public Object getLabel() {
            return label;
        }
        
    }
    
}
