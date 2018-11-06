package model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class EdgeUnitTest {
    @Test
    public void should_calculate_distance_to_point_when_is_on_left_to_edge() {
        //given
        Point p = new Point(-2,5,0);
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        //when
        double result = edge.dist(p);
        //then
        assertThat(result).isEqualTo(p.dist(new Point(0,0,0)));
    }
    @Test
    public void should_calculate_distance_to_point_when_is_on_right_to_edge() {
        //given
        Point p = new Point(10,5,0);
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        //when
        double result = edge.dist(p);
        //then
        assertThat(result).isEqualTo(p.dist(new Point(5,0,0)));
    }

    @Test
    public void should_calculate_distance_to_point_when_is_above_edge() {
        //given
        Point p = new Point(3,3,0);
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        //when
        double result = edge.dist(p);
        //then
        assertThat(result).isEqualTo(3);
    }
}