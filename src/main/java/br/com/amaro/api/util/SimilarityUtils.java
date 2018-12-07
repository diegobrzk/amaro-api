package br.com.amaro.api.util;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.toPrimitive;

public class SimilarityUtils {

    public static Double calculateSimilarity(final double[] v1, final double[] v2) {
        return 1 / (1 + new EuclideanDistance().compute(v1, v2));
    }

    public static double[] convertSimilarityArray(final List<Integer> array) {
        List<Double> l2 = array.stream().map(Integer::doubleValue).collect(toList());
        return toPrimitive(l2.toArray(new Double[l2.size()]));
    }

}
