package model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class EdgeUnitTest {
    @Test
    public void should_calculate_distance_to_point_correctly() {
        //given
        Point p = new Point(2,3,1);
        Edge edge = new Edge(new Point(1,1,2), new Point(5,0,1));
        //when
        double result = edge.dist(p);
        //then
        assertThat(result).isEqualTo(Math.sqrt(140)/Math.sqrt(26)); //FIXME
    }

    @Test
    public void should_calculate_distance_to_other_edge_correctly() {
        //given
        Edge edge = new Edge(new Point(2,1,4), new Point(-1,1,0));
        Edge other = new Edge(new Point(-1,0,2), new Point(5,1,2));
        //when
        double result = edge.dist(other);
        //then
        assertThat(result).isEqualTo(4/Math.sqrt(44)); //FIXME
    }
}