import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.List;

public class FordFulkersonResolver {
    private Graph<Integer, DefaultWeightedEdge> origin;
    private Graph<Integer, DefaultWeightedEdge> editedGraph;
    private Graph<Integer, DefaultWeightedEdge> residualNetwork = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

    private BFSAlgorithm bfsAlgorithm;

    public FordFulkersonResolver(Graph<Integer, DefaultWeightedEdge> graph) {
        this.origin = graph;
        this.editedGraph = copy(graph);

        this.bfsAlgorithm = new BFSAlgorithm(editedGraph);
    }

    List<Integer> calculateMaxFlow(Integer source, Integer target) {
        List<Integer> path = bfsAlgorithm.findPathBetween(source, target);
        if (path != null && !path.isEmpty()) {
            int min = path.stream().mapToInt(Integer::intValue).min().getAsInt();
            substractFlow(path, min);
            updateResidualNetworkWithPathFlow(path, min);
        }
        return null;//TODO
    }

    private void updateResidualNetworkWithPathFlow(List<Integer> path, int min) {
        for (int i = 0; i < path.size() - 1; i++) {
//            TODO
        }

    }

    private void substractFlow(List<Integer> path, int subtrahend) {
        for (int i = 0; i < path.size() - 1; i++) {
            DefaultWeightedEdge edge = editedGraph.getEdge(path.get(i), path.get(i + 1));
            double edgeWeight = editedGraph.getEdgeWeight(edge);
            double newEdgeWeight = edgeWeight - subtrahend;
            if (newEdgeWeight <= 0) {
                editedGraph.removeEdge(edge);
            } else {
                editedGraph.setEdgeWeight(edge, newEdgeWeight);
            }
        }
    }

    private Graph<Integer, DefaultWeightedEdge> copy(Graph<Integer, DefaultWeightedEdge> graph) {
        var result = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graph.vertexSet().forEach(vertex -> result.addVertex(vertex.intValue()));//integer copy
        graph.edgeSet().forEach(edge -> {
            DefaultWeightedEdge resultEdge = result.addEdge(graph.getEdgeSource(edge).intValue(), graph.getEdgeTarget(edge).intValue());
            result.setEdgeWeight(resultEdge, graph.getEdgeWeight(edge));
        });
        return result;
    }
}
