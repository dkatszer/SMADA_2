package model;

import model.math.Vector;

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

        double normal = p0ToPoint.vectorMultiply(edgeVector).length() / edgeVector.length();
        return normal;
    }
    private double normal(Point point){
        Vector edgeVector = edgeVector();
        return point.vectorToOtherPoint(p0).vectorMultiply(edgeVector).length() / edgeVector.length();
    }

    public double dist(Edge other) {
        Vector edgeVectorsProduct = edgeVector().vectorMultiply(other.edgeVector());
        return p1.vectorToOtherPoint(other.p1).scalarMultiply(edgeVectorsProduct) / edgeVectorsProduct.length();
    }

    private Vector edgeVector() {
        return p0.vectorToOtherPoint(p1);
    }
}
