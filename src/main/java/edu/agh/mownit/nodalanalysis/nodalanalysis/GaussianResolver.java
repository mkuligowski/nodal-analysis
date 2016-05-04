package edu.agh.mownit.nodalanalysis.nodalanalysis;

public class GaussianResolver {
    private static final double EPSILON = 1e-10;

    public static double[] calculate(double[][] matrix, double[] results) {
        int size  = results.length;

        for (int pivot = 0; pivot < size; pivot++) {

            int max = pivot;
            for (int i = pivot + 1; i < size; i++) {
                if (Math.abs(matrix[i][pivot]) > Math.abs(matrix[max][pivot])) {
                    max = i;
                }
            }
            double[] temp = matrix[pivot]; matrix[pivot] = matrix[max]; matrix[max] = temp;
            double   t    = results[pivot]; results[pivot] = results[max]; results[max] = t;


            if (Math.abs(matrix[pivot][pivot]) <= EPSILON) {
                throw new RuntimeException("Współczynnik macierzy jest równy 0 lub bliski zeru ");
            }


            for (int i = pivot + 1; i < size; i++) {
                double alpha = matrix[i][pivot] / matrix[pivot][pivot];
                results[i] -= alpha * results[pivot];
                for (int j = pivot; j < size; j++) {
                    matrix[i][j] -= alpha * matrix[pivot][j];
                }
            }
        }

        double[] x = new double[size];
        for (int i = size - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < size; j++) {
                sum += matrix[i][j] * x[j];
            }
            x[i] = (results[i] - sum) / matrix[i][i];
        }
        return x;
    }

}
