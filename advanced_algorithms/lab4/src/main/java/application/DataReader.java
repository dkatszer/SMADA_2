package application;

import model.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataReader {
    private static final String DELIMITER = ";";

    public ArrayList<Matrix> readMatrices(File file) throws FileNotFoundException {
        ArrayList<Matrix> matrices = new ArrayList<>();
        try(var scanner = new Scanner(file)){
            StringBuilder sb = new StringBuilder();
            int rowsNo = 0, colsNo = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("Matrix")){
                    sb = new StringBuilder();
                    Pattern pattern = Pattern.compile("(\\d)\\s+x\\s+(\\d)");
                    Matcher matcher = pattern.matcher(line);
                    matcher.find();
                    rowsNo = Integer.valueOf(matcher.group(1));
                    colsNo = Integer.valueOf(matcher.group(2));
                }else if(line.contains("---------------")){
                    matrices.add(parseStringToMatrix(sb.toString(), rowsNo, colsNo));
                } else{
                    sb.append(line);
                }
            }
        }
        return matrices;
    }

    private Matrix parseStringToMatrix(String string, int rowsNo, int colsNo) {
        Scanner scanner = new Scanner(string);
        Matrix.Row[] rows = new Matrix.Row[rowsNo];
        for(int i = 0 ; i < rowsNo ; i++){
            double[] values = new double[colsNo];
            for(int j = 0 ; j <colsNo ; j++){
                String next = scanner.next();
                String number = next.replaceAll("[\\[,\\]]", "");
                values[j] = Double.valueOf(number);
            }
            rows[i] = Matrix.Row.of(values);
        }
        return Matrix.of(rows);
    }
}
