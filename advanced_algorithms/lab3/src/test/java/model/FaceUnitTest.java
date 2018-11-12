package model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class FaceUnitTest {
    @Test
    public void should_calculate_distance_to_point_correctly() {
        //given
        Face face = new Face(
                new Point(1.0, 1.0, 0.0),
                new Point(2.0, 2.0, 0.0),
                new Point(0.0, 2.0, 0.0));
        Point point = new Point(0, 1, 0);

        //when
        double dist = face.dist(point);

        //then
        assertThat(dist).isEqualTo(1);
    }

    @Test
    public void should_detect_intersection_with_edge_and_return_zero() {
        //given
        Point a = new Point(0,0,0);
        Point b = new Point(0,5,0);
        Point c = new Point(5,0,0);
        Face face = new Face(a,b,c);

        Point edgeV0 = new Point(1,1,-2);
        Point edgeV1 = new Point(1,1,2);
        Edge edge = new Edge(edgeV0, edgeV1);

        //when
        double dist = face.dist(edge);

        //then
        assertThat(dist).isEqualTo(0);
    }

    @Test
    public void should_return_normal_distance_to_edge_if_is_parallel_to_face_and_over_face() {
        //given
        Point a = new Point(0,0,0);
        Point b = new Point(0,5,0);
        Point c = new Point(5,0,0);
        Face face = new Face(a,b,c);

        Point edgeV0 = new Point(0,0,5);
        Point edgeV1 = new Point(5,5,5);
        Edge edge = new Edge(edgeV0, edgeV1);

        //when
        double dist = face.dist(edge);

        //then
        assertThat(dist).isEqualTo(5);
    }

    @Test
    public void should_return_normal_to_faces_if_they_are_parallel(){
        //given
        Point a = new Point(0,0,0);
        Point b = new Point(0,5,0);
        Point c = new Point(5,0,0);
        Face face = new Face(a,b,c);

        Point a2 = new Point(0,0,2);
        Point b2 = new Point(0,5,2);
        Point c2 = new Point(5,0,2);
        Face face2 = new Face(a2,b2,c2);

        //when
        double dist = face.dist(face2);

        //then
        assertThat(dist).isEqualTo(2);
    }
}