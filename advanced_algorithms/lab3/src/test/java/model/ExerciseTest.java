package model;

import org.assertj.core.data.Offset;
import org.junit.Test;
import util.DataReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExerciseTest {
    @Test
    public void exercise_2a() {
        //given
        Face face = new Face(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 0));
        Face face_2 = new Face(new Point(100, 0, 0.13), new Point(0, 100, 0.13), new Point(-100, -100, 0.13));
        //when
        double dist = face.dist(face_2);
        //then
        assertThat(dist).isEqualTo(0.13);
    }

    @Test
    public void exercise_2b() throws FileNotFoundException {
        //given
        File inputFile = new File(DataReader.class.getClassLoader().getResource("solid_data.txt").getFile());
        DataReader dataReader = new DataReader();
        List<Solid> solids = dataReader.createSolids(inputFile);
        Solid solid_2 = solids.get(1);
        Solid solid_1 = solids.get(0);

        //when
        double dist = solid_1.dist(solid_2);
        //then
        assertThat(dist).isEqualTo(8.766524, Offset.offset(0.00001));
    }
}