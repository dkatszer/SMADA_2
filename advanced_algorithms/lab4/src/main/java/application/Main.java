package application;

import model.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    private static DataReader dataReader = new DataReader();
    private static ParallelMatrixMultiply matrixMultiplier = new ParallelMatrixMultiply();
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(DataReader.class.getClassLoader().getResource("matrices.txt").getFile());
        ArrayList<Matrix> matrices = dataReader.readMatrices(inputFile);

        Matrix result  = matrixMultiplier.multiply(matrices);
        System.out.println(result);
    }
}
