package model;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class PointUnitTest {

    @Test
    public void should_calculate_distance_between_points_correctly() {
        //given
        Point p = new Point(-5,2,4);
        Point q = new Point(-2,2,0);
        //when
        double result = p.dist(q);
        //then
        assertThat(result).isEqualTo(5);
    }

}