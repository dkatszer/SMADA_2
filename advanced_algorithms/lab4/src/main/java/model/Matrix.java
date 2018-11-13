package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix {
    private double[][] matrix;

    private Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public static Matrix of(Row... rows){
        double[][] matrixResult = new double[rows.length][rows[0].values.length];
        int columnsNo = rows[0].values.length;
        for(int rowId = 0; rowId < rows.length ; rowId++){
            double[] rowValues = rows[rowId].values;
            if(rowValues.length!=columnsNo){
                throw new RuntimeException("rows have different number of columns");
            }
            for(int colId = 0; colId< rowValues.length ; colId++){
                matrixResult[rowId][colId] = rowValues[colId];
            }
        }
        return new Matrix(matrixResult);
    }

    public Matrix multiply(Matrix other){
        if(getColumnsNo()!=other.getRowsNo()){
            String message = "These matrixes cannot be multiplied."
                    + System.lineSeparator()
                    + this.toString()
                    + System.lineSeparator()
                    + other.toString();
            throw new IllegalArgumentException(message);
        }
        double[][] result = new double[getRowsNo()][other.getColumnsNo()];
        for(int k = 0 ; k < other.getColumnsNo() ; k++) {
            for (int i = 0; i < getRowsNo(); i++) {
                double sum = 0;
                for (int j = 0; j < getColumnsNo(); j++) {
                    sum += matrix[i][j] * other.matrix[j][k];
                }
                result[i][k] = sum;
            }
        }
        return new Matrix(result);
    }

    public int getColumnsNo(){
        return matrix[0].length;
    }

    public int getRowsNo(){
        return matrix.length;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return Arrays.deepEquals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row: matrix) {
            sb.append(Arrays.toString(row))
                .append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static class Row {

        private double[] values;

        private Row(double[] values) {
            this.values = values;
        }

        public static Row of(double ... values){
            return new Row(values);
        }
    }
}
