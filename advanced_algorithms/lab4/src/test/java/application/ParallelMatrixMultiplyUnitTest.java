package application;

import model.Matrix;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.*;

public class ParallelMatrixMultiplyUnitTest {
    private ParallelMatrixMultiply objectUnderTest = new ParallelMatrixMultiply(ForkJoinPool.commonPool());
    @Test
    public void should_multiplyTwoMatricesCorrectly() {
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
        ArrayList<Matrix> matrices = new ArrayList<>();
        matrices.add(matrix_1);
        matrices.add(matrix_2);

        //when
        Matrix result = objectUnderTest.multiply(matrices);

        //then
        assertThat(result).isEqualTo(Matrix.of(
                Matrix.Row.of(5, 1),
                Matrix.Row.of(4, 2)
        ));
    }

    @Test
    public void should_multiplyManyMatricesCorrectly() {
        //given
        Matrix matrix_1 = Matrix.of(
                Matrix.Row.of(1, 0),
                Matrix.Row.of(-1, 3)
        );
        Matrix matrix_2 = Matrix.of(
                Matrix.Row.of(3, 1),
                Matrix.Row.of(2, 1)
        );
        Matrix matrix_3 = Matrix.of(
                Matrix.Row.of(5, -2),
                Matrix.Row.of(2, 1)
        );
        Matrix matrix_4 = Matrix.of(
                Matrix.Row.of(-3, 2),
                Matrix.Row.of(-3, 1)
        );
        ArrayList<Matrix> matrices = new ArrayList<>();
        matrices.add(matrix_1);
        matrices.add(matrix_2);
        matrices.add(matrix_3);
        matrices.add(matrix_4);

        //when
        Matrix result = objectUnderTest.multiply(matrices);

        //then
        assertThat(result).isEqualTo(Matrix.of(
                Matrix.Row.of(-36, 29),
                Matrix.Row.of(-45, 34)
        ));
    }

    @Test
    public void should_preserveTheOrderAndDontThrowExceptionAboutIllegalMultiplicationBasingOnInvalidSizes() {
        //given
        //when
        //then
    }
}