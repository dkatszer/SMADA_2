package model;

import model.math.Dim;
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

    public Point castTo2D(Dim firstDim, Dim secondDim) {
        Dim missingDim = Dim.findMissingDim(firstDim, secondDim);
        switch (missingDim) {
            case X:
                return new Point(0, y, z);
            case Y:
                return new Point(x, 0, z);
            case Z:
                return new Point(x, y, 0);
            default:
                throw new IllegalStateException();
        }
    }

    public Vector toVectorFromZeroZeroZero(){
        return new Vector(x,y,z);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }

    public Point add(Vector vector) {
        return new Point(x + vector.getX() , y + vector.getY(), z + vector.getZ());
    }
}
