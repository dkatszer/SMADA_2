package model;

import java.util.ArrayList;
import java.util.List;

public class Solid {
    private final List<Face> f;

    public Solid(List<Face> f) {
        this.f = new ArrayList<>(f);
    }

    public double dist(Solid other){
//     TODO - check if it works and if this is what we are supposed to implement ??
        return f.stream().flatMap(face -> other.f.stream().map(face::dist)).min(Double::compareTo).get();
    }


}
