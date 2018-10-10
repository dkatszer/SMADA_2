import java.util.Arrays;
import java.util.List;

public class PathInGraph {
    private List<Integer> vertexes;
    private Integer value;

    public PathInGraph(List<Integer> vertexes, Integer value) {
        this.vertexes = vertexes;
        this.value = value;
    }

    public List<Integer> getVertexes() {
        return vertexes;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PathInGraph{" +
                "vertexes=" + Arrays.toString(vertexes.toArray()) +
                ", value=" + value +
                '}';
    }
}
