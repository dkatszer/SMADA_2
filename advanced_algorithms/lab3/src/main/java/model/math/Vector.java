package model.math;

import java.util.Objects;

public class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Vector vectorMultiply(Vector other) {
        return new Vector(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public double scalarMultiply(Vector other) {
        return  this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public double angleBetween(Vector other){
        double scalarProduct = this.scalarMultiply(other);
        double lengthProduct = this.length() * other.length();
        double cosValueOfAngle = scalarProduct / lengthProduct;
        double acosValue = Math.acos(cosValueOfAngle);
        return Math.toDegrees(acosValue);
    }

    @Override
    public String toString() {
        return String.format("<%f, %f, %f>", x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0 &&
                Double.compare(vector.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
