package application;

import model.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static DataReader dataReader = new DataReader();
    private static ParallelMatrixMultiply matrixMultiplier = new ParallelMatrixMultiply(ForkJoinPool.commonPool());

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(DataReader.class.getClassLoader().getResource("matrices-2.txt").getFile());
        ArrayList<Matrix> matrices = dataReader.readMatrices(inputFile);

        Instant start_1 = Instant.now();
        Matrix expectedResult = matrices.stream().reduce(Matrix::multiply).get();
        Instant end_1 = Instant.now();
        System.out.println("Time: " + Duration.between(start_1, end_1).toMillis());
        System.out.println();
        System.out.println(expectedResult);

        Instant start_2 = Instant.now();
        Matrix result = matrixMultiplier.multiply(matrices);
        Instant end_2 = Instant.now();
        System.out.println("Time: " + Duration.between(start_2, end_2).toMillis());
        System.out.println();
        System.out.println(result);
    }
}
