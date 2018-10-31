package model;

import model.math.Wektor;

public class Face {
    private final Point v1;
    private final Point v2;
    private final Point v3;

    public Face(Point v1, Point v2, Point v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public double dist(Point point){
        Wektor normalToFace = normalToFace();
        return point.vectorToOtherPoint(v3).scalarMultiply(normalToFace).length() / normalToFace.length();
    }

    public double dist(Edge edge){
//        TODO - find in the internet !!
        return 0;
    }

    public double dist(Face other){
        if(isParallelTo(other)){
            return 0;
        }else {
            //TODO http://www.math.kit.edu/ianm2/lehre/am22016s/media/distance-harvard.pdf?fbclid=IwAR16Q-clp40kgJ2PNy5tqNt_nVqcnl_d_H-JPuHe__jeXHFul0xMc-_Yv2U
            return 0;
        }
    }

    private boolean isParallelTo(Face other) {
        return false;
    }

    private Wektor normalToFace() {
//        TODO
        return null;
    }
}
