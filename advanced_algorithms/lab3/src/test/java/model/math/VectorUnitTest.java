package model.math;

import org.assertj.core.data.Offset;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


public class VectorUnitTest {

    @Test
    public void should_calculateAngleBetweenVectorsCorrectly() {
        //given
        Vector vector = new Vector(4, 3, 0);
        Vector vector2 = new Vector(2, 5, 0);
        //when
        double angle = vector.angleBetween(vector2);
        //then
        assertThat(angle).isCloseTo(31.3, Offset.offset(0.1));
    }

    @Test
    public void should_calculateScalarProductCorrectly() {
        //given
        Vector vector = new Vector(5, 1, 0);
        Vector vector2 = new Vector(-2, 4, 0);
        //when
        double result = vector.scalarMultiply(vector2);
        //then
        assertThat(result).isEqualTo(-6);
    }

    @Test
    public void should_calculateVectorProductCorrectly() {
        //given
        Vector vector = new Vector(1, 2, 3);
        Vector vector2 = new Vector(4, 5, 6);
        //when
        Vector result = vector.vectorMultiply(vector2);
        //then
        assertThat(result).isEqualTo(new Vector(-3,6,-3));
    }
}