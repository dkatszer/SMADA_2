package model;

import model.math.Vector;

public class Point {
    private final double x;
    private final double y;
    private final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dist(Point o) {
        Vector vector = vectorToOtherPoint(o);
        return vector.length();
    }

    public Vector vectorToOtherPoint(Point other) {
        return new Vector(other.x - this.x, other.y - this.y, other.z - this.z);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }
}
