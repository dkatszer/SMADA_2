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

    @Test
    public void should_return_dist_between_left_points_when_lines_of_edges_would_croos_on_right() {
        //given
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        Edge edge2 = new Edge(new Point(0,2,0), new Point(5,10,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(new Point(0,0,0).dist(new Point(0,2,0)));
    }
    @Test
    public void should_return_dist_between_right_points_when_lines_of_edges_would_croos_on_right() {
        //given
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        Edge edge2 = new Edge(new Point(0,10,0), new Point(5,2,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(new Point(5,0,0).dist(new Point(5,2,0)));
    }

    @Test
    public void should_return_dist_between_first_right_and_second_lefts_when_first_edge_is_on_left_to_second() {
        //given
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        Edge edge2 = new Edge(new Point(-5,2,0), new Point(-2,2,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(new Point(0,0,0).dist(new Point(-2,2,0)));
    }

    @Test
    public void should_return_dist_between_edge_and_second_left_when_they_are_normal_to_each_other() {
        //given
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        Edge edge2 = new Edge(new Point(3,5,0), new Point(3,10,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(edge.dist(new Point(3,5,0)));
        assertThat(dist).isEqualTo(5);
    }

    @Test
    public void should_return_zero_when_edges_intersect() {
        //given
//        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
//        Edge edge2 = new Edge(new Point(3,3,0), new Point(3,-3,0));
        Edge edge = new Edge(new Point(-3,0,0), new Point(1,0,0));
        Edge edge2 = new Edge(new Point(-1,-2,0), new Point(1,2,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(0);
    }
    @Test
    public void should_return_zero_when_edges_intersect_perpendicularly() {
        //given
        Edge edge = new Edge(new Point(0,0,0), new Point(5,0,0));
        Edge edge2 = new Edge(new Point(3,3,0), new Point(3,-3,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(0);
    }

    @Test
    public void should_return_zero_when_edges_are_collinear_and_overlaping() {
        //given
        Edge edge = new Edge(new Point(4,4,0), new Point(6,4,0));
        Edge edge2 = new Edge(new Point(-1,4,0), new Point(6,4,0));
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(0);
    }

    @Test
    public void should_return_non_zero_value_when_edges_are_collinear_and_non_overlapping(){
        //given
        Point e1p0 = new Point(4, 4, 0);
        Edge edge = new Edge(e1p0, new Point(6,4,0));
        Point e2p1 = new Point(2, 4, 0);
        Edge edge2 = new Edge(new Point(-1,4,0), e2p1);
        //when
        double dist = edge.dist(edge2);
        //then
        assertThat(dist).isEqualTo(2);
        assertThat(dist).isEqualTo(e1p0.dist(e2p1));
    }
}