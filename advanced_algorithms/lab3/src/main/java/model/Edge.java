package model;

import model.math.Vector;

public class Edge {
    private final Point v1;
    private final Point edgeMainPoint; // B

    public Edge(Point v1, Point v2) {
        this.v1 = v1;
        this.edgeMainPoint = v2;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "v1=" + v1 +
                ", v2=" + edgeMainPoint +
                '}';
    }

    public double dist(Point point){
        Vector pointToV2Vector = edgeMainPoint.vectorToOtherPoint(point);
        Vector edgeVector = edgeWektor();
        double angle = pointToV2Vector.angleBetween(edgeVector);
        double normal = pointToV2Vector.vectorMultiply(edgeVector).length() / edgeVector.length();
        return normal;
    }

    public double dist(Edge other){
        Vector edgeVectorsProduct = edgeWektor().vectorMultiply(other.edgeWektor());
        return edgeMainPoint.vectorToOtherPoint(other.edgeMainPoint).scalarMultiply(edgeVectorsProduct) / edgeVectorsProduct.length();
    }

    /**
     * EDGE(A,B) - edgeWektor = Vector from B to A
     */
    private Vector edgeWektor(){
        return edgeMainPoint.vectorToOtherPoint(v1);
    }
}
