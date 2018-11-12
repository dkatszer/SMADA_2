package model;

import model.math.Dim;
import model.math.Vector;

import java.util.stream.DoubleStream;

public class Edge {
    private final Point p0;
    private final Point p1;

    public Edge(Point p0, Point v2) {
        this.p0 = p0;
        this.p1 = v2;
    }

    public Point getP0() {
        return p0;
    }

    public Point getP1() {
        return p1;
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
        if (intersectWith(other)) {
            return 0;
        }
        return DoubleStream.of(
                p0.dist(other.p0),
                p0.dist(other.p1),
                p1.dist(other.p0),
                p1.dist(other.p1),
                other.dist(p0),
                other.dist(p1),
                this.dist(other.p0),
                this.dist(other.p1)
        ).min().getAsDouble();
    }

    private boolean intersectWith(Edge other) {
        boolean xy = intersectWithIn2D(other, Dim.X, Dim.Y);
        boolean xz = intersectWithIn2D(other, Dim.X, Dim.Z);
        boolean yz = intersectWithIn2D(other, Dim.Y, Dim.Z);
        return (xy && xz && yz);
    }

    private boolean intersectWithIn2D(Edge other, Dim first, Dim second) {
        Vector r = edgeVector().castTo2D(first, second);
        Point p = p0.castTo2D(first, second);
        Vector s = other.edgeVector().castTo2D(first, second);
        Point q = other.p0.castTo2D(first, second);

        Vector qMinusP = p.vectorToOtherPoint(q);
        double rs = r.vectorMultiply2D(s, first, second);
        if (rs == 0 && qMinusP.vectorMultiply2D(r, first, second) == 0) { //collinear
            double rDotR = r.scalarMultiply(r);
            if (rDotR == 0) { //this is not included into stackoverflow but it is edge case when length of vector r = 0
                rDotR = 1;
            }
            double t0 = qMinusP.scalarMultiply(r) / rDotR;
            double t1 = t0 + s.scalarMultiply(r) / rDotR;
            double min = Math.min(t0, t1);
            double max = Math.max(t0, t1);
            return areRangesOverlapping(min, max, 0, 1);
        } else {
            double t = qMinusP.vectorMultiply2D(s, first, second) / rs;
            double u = qMinusP.vectorMultiply2D(r, first, second) / rs;
            return (0 <= t && t <= 1) && (0 <= u && u <= 1);
        }
    }

    private boolean areRangesOverlapping(double firstRangeMin, double firstRangeMax, double secondRangeMin, double secondRangeMax) {
        return Math.max(firstRangeMin, secondRangeMin) <= Math.min(firstRangeMax, secondRangeMax);
    }

    private double normal(Point point) {
        Vector edgeVector = edgeVector();
        return point.vectorToOtherPoint(p0).vectorMultiply(edgeVector).length() / edgeVector.length();
    }

    public Vector edgeVector() {
        return p0.vectorToOtherPoint(p1);
    }
}
