package model;

import java.util.ArrayList;
import java.util.List;

public class Solid {
    private final List<Face> f;

    public Solid(List<Face> f) {
        this.f = new ArrayList<>(f);
    }

    private List<Face> getFaces() {
        return f;
    }
    public double dist(Solid other){
        return getFaces().stream()
                .flatMap(face -> other.getFaces().stream().map(face::dist))
                .min(Double::compareTo)
                .get();
    }


}
