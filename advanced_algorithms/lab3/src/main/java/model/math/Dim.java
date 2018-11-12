package model.math;

import java.util.Arrays;
import java.util.List;

public enum Dim {
    X,
    Y,
    Z;

    public static Dim findMissingDim(Dim first, Dim second){
        List<Dim> presentDims = List.of(first, second);
        return Arrays.stream(Dim.values())
                .filter(dim -> !presentDims.contains(dim))
                .findAny()
                .get();
    }
}
