package model;

import model.math.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class Face {
    private final Point v1;
    private final Point v2;
    private final Point v3;
    private Edge v1ToV2;
    private Edge v1ToV3;
    private Edge v2ToV3;

    public Face(Point v1, Point v2, Point v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        v1ToV2 = new Edge(v1, v2);
        v1ToV3 = new Edge(v1, v3);
        v2ToV3 = new Edge(v2, v3);
    }

    //    TODO -test it
    public double dist(Point point) {
        Vector normalToFace = normalToFace();
        double perpendicular = Math.abs(v3.vectorToOtherPoint(point).scalarMultiply(normalToFace) / normalToFace.length());
        List<Double> distances = new ArrayList<>(List.of(
                v1ToV2.dist(point),
                v1ToV3.dist(point),
                v2ToV3.dist(point)));
        if (perpendicular == 0) {
            if(isPointOnFace(point)){
                return 0;
            }
        }else{
            distances.add(perpendicular);
        }
        return distances.stream().mapToDouble(d->d).min().getAsDouble();
    }

    private boolean isPointOnFace(Point point) {
        double a = new Face(v1, v2, point).calculateArea();
        double b = new Face(v1, v3, point).calculateArea();
        double c = new Face(v2, v3, point).calculateArea();
        return calculateArea() == a + b + c;
    }

    private double calculateArea() {
        return normalToFace().length() / 2;
    }

    private List<Edge> getEdges() {
        return List.of(v1ToV2, v1ToV3, v2ToV3);
    }

    //    TODO - test it
    public double dist(Edge edge) {
        if (intersectsWithEdge(edge)) {
            return 0;
        }
        Edge v1ToV2 = new Edge(v1, v2);
        Edge v1ToV3 = new Edge(v1, v3);
        Edge v2ToV3 = new Edge(v2, v3);
        return DoubleStream.of(
                v1ToV2.dist(edge),
                v1ToV3.dist(edge),
                v2ToV3.dist(edge),
                dist(edge.getP0()),
                dist(edge.getP1())
        ).min().getAsDouble();
    }

    private boolean intersectsWithEdge(Edge edge) {
        Point v0 = v3;
        Vector n = normalToFace();
        double s = n.scalarMultiply(edge.getP0().vectorToOtherPoint(v0)) / n.scalarMultiply(edge.edgeVector());
        return 0 <= s && s <= 1;
    }

    private boolean intersectsWithFace(Face face) {
        return false; //TODO - implement it
    }

    public double dist(Face other) {
        if (intersectsWithFace(other)) {
            return 0;
        } else {
            List<Edge> thisEdges = getEdges();
            List<Edge> otherEdges = other.getEdges();

            List<Double> distances = new ArrayList<>();
            //between all edges
            thisEdges.forEach(thisEdge -> otherEdges.forEach(otherEdge -> distances.add(thisEdge.dist(otherEdge))));
            // between all point to surface
            distances.addAll(List.of(
                    other.dist(v1),
                    other.dist(v2),
                    other.dist(v3)));
            distances.addAll(List.of(
                    this.dist(other.v1),
                    this.dist(other.v2),
                    this.dist(other.v3)));
            return distances.stream().mapToDouble(d -> d).min().getAsDouble();
        }
    }


    private Vector normalToFace() {
        Vector u = v3.vectorToOtherPoint(v2);
        Vector v = v3.vectorToOtherPoint(v1);
        return u.vectorMultiply(v);
    }
}
