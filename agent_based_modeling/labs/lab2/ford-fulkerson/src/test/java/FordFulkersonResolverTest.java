import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FordFulkersonResolverTest {

    private FordFulkersonResolver objectUnderTest;

    @Test
    public void shouldCalculatewMaxFlowCorrectly() {
        //given
        var graph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        addVertexes(graph, 1, 2, 3, 4, 5, 6, 7);
        addEdges(graph,
                Edge.of(1, 2, 9),
                Edge.of(1, 5, 9),
                Edge.of(2, 3, 7),
                Edge.of(2, 4, 3),
                Edge.of(3, 4, 4),
                Edge.of(3, 7, 6),
                Edge.of(4, 6, 2),
                Edge.of(4, 7, 9),
                Edge.of(5, 4, 3),
                Edge.of(5, 6, 6),
                Edge.of(6, 7, 8)
        );
        objectUnderTest = new FordFulkersonResolver(graph);

        //when
        double result = objectUnderTest.calculateMaxFlow(1, 7);

        //then
        assertThat(result).isEqualTo(18);
    }

    private void addEdges(Graph<Integer, DefaultWeightedEdge> graph, Edge... edges) {
        for (Edge edge : edges) {
            DefaultWeightedEdge graphEdge = graph.addEdge(edge.source, edge.target);
            graph.setEdgeWeight(graphEdge, edge.weight);
        }
    }

    private void addVertexes(Graph<Integer, DefaultWeightedEdge> graph, int... vertexes) {
        for (int vertex : vertexes) {
            graph.addVertex(vertex);
        }
    }

    private static class Edge {
        private final int source;
        private final int target;
        private final double weight;

        public Edge(int source, int target, double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public static Edge of(int source, int target, double weight) {
            return new Edge(source, target, weight);
        }
    }
}