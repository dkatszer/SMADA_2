package util;

import model.Face;
import model.Point;
import model.Solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {
    private static final String DELIMITER = ";";

    public List<Solid> createSolids(File file) throws FileNotFoundException {
        List<Solid> solids = new ArrayList<>();
        try(var scanner = new Scanner(file)) {
            List<Face> faces = new ArrayList<>();
            String line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                List<Point> points = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    line = scanner.nextLine();
                    if (line.contains("SOLID")) {
                        line = scanner.nextLine();
                        solids.add(new Solid(faces));
                        faces = new ArrayList<>();
                    }
                    String[] xyz = line.split(DELIMITER);
                    double x = Double.parseDouble(xyz[0]);
                    double y = Double.parseDouble(xyz[1]);
                    double z = Double.parseDouble(xyz[2]);
                    points.add(new Point(x, y, z));
                }
                Face face = new Face(points.get(0), points.get(1), points.get(2));
                faces.add(face);
                scanner.nextLine();
            }
            solids.add(new Solid(faces));
            return solids;
        }
    }

}
