package model;

import model.math.Vector;

import java.util.stream.DoubleStream;

public class Edge {
    private final Point p0;
    private final Point p1;

    public Edge(Point p0, Point v2) {
        this.p0 = p0;
        this.p1 = v2;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "p0=" + p0 +
                ", v2=" + p1 +
                '}';
    }

    public double dist(Point point) {
        Vector edgeVector = edgeVector();
        Vector p0ToPoint = p0.vectorToOtherPoint(point);
        if (p0ToPoint.angleBetween(edgeVector) >= 90) {
            return p0ToPoint.length();
        }
        Vector p1ToPoint = p1.vectorToOtherPoint(point);
        if (p1ToPoint.angleBetween(edgeVector) <= 90) {
            return p1ToPoint.length();
        }
        return normal(point);
    }
    public double dist(Edge other) {
        return DoubleStream.of(
                p0.dist(other.p0),
                p0.dist(other.p1),
                p1.dist(other.p0),
                p1.dist(other.p1)
        ).min().getAsDouble();
    }

    private double normal(Point point){
        Vector edgeVector = edgeVector();
        return point.vectorToOtherPoint(p0).vectorMultiply(edgeVector).length() / edgeVector.length();
    }

    private Vector edgeVector() {
        return p0.vectorToOtherPoint(p1);
    }
}
