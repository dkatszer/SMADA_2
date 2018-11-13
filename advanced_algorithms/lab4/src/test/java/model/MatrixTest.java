package model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class MatrixTest {

    @Test
    public void name() {
        //given
        //when
        Matrix matrix = Matrix.of(
                Matrix.Row.of(1, 2, 3),
                Matrix.Row.of(4, 5, 6)
        );
        System.out.println(matrix);
        System.out.println("cols no = " + matrix.getColumnsNo());
        System.out.println("rows no = " + matrix.getRowsNo());
        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSizesAreNotAllowedForMultiply() {
        //given
        Matrix matrix_1 = Matrix.of(
                Matrix.Row.of(1, 2, 3),
                Matrix.Row.of(4, 5, 6)
        );
        Matrix matrix_2 = Matrix.of(
                Matrix.Row.of(1, 2, 3),
                Matrix.Row.of(4, 5, 6)
        );
        //when
        matrix_1.multiply(matrix_2);
        //then
    }

    @Test
    public void shouldMultiplyMatrixesCorrectly() {
        //given
        Matrix matrix_1 = Matrix.of(
                Matrix.Row.of(1, 0, 2),
                Matrix.Row.of(-1, 3, 1)
        );
        Matrix matrix_2 = Matrix.of(
                Matrix.Row.of(3, 1),
                Matrix.Row.of(2, 1),
                Matrix.Row.of(1, 0)
        );
        //when
        Matrix result = matrix_1.multiply(matrix_2);

        //then
        assertThat(result).isEqualTo(Matrix.of(
                Matrix.Row.of(5, 1),
                Matrix.Row.of(4, 2)
        ));
    }
}