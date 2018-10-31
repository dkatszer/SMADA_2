package model;

import model.math.Wektor;

public class Edge {
    private final Point v1;
    private final Point edgeMainPoint; // B

    public Edge(Point v1, Point v2) {
        this.v1 = v1;
        this.edgeMainPoint = v2;
    }

    public double dist(Point p0){
        Wektor pointToV2Vector = p0.vectorToOtherPoint(edgeMainPoint);
        Wektor edgeVector = edgeWektor();
        return pointToV2Vector.vectorMultiply(edgeVector).length() / edgeVector.length();
    }

    public double dist(Edge other){
        Wektor edgeVectorsProduct = edgeWektor().vectorMultiply(other.edgeWektor());
        return edgeMainPoint.vectorToOtherPoint(other.edgeMainPoint).scalarMultiply(edgeVectorsProduct).length() / edgeVectorsProduct.length();
    }

    /**
     * EDGE(A,B) - edgeWektor = Wektor from B to A
     */
    private Wektor edgeWektor(){
        return edgeMainPoint.vectorToOtherPoint(v1);
    }
}
