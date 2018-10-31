package model.math;

public class Wektor {
    private double x;
    private double y;
    private double z;

    public Wektor(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double length(){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }

    public Wektor vectorMultiply(Wektor other){
        return new Wektor(
                this.y*other.z - this.z*other.y,
                this.z*other.x - this.x*other.z,
                this.x*other.y - this.y*other.x
        );
    }

    public Wektor scalarMultiply(Wektor other){
        return new Wektor(
            this.x * other.x,
            this.y * other.y,
            this.z * other.z
        );
    }
}
