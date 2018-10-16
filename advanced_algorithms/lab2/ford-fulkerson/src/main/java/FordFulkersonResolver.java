import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.List;
import java.util.Set;

public class FordFulkersonResolver {
    private Graph<Integer, DefaultWeightedEdge> origin;
    private Graph<Integer, DefaultWeightedEdge> editedGraph;
    private Graph<Integer, DefaultWeightedEdge> residualNetwork = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

    private BFSAlgorithm bfsAlgorithm;

    public FordFulkersonResolver(Graph<Integer, DefaultWeightedEdge> graph) {
        this.origin = graph;
        this.editedGraph = copy(graph);
        this.bfsAlgorithm = new BFSAlgorithm(editedGraph);
        addVertexes(residualNetwork, graph.vertexSet());
    }
    private void addVertexes(Graph<Integer, DefaultWeightedEdge> graph, Set<Integer> vertexes) {
        for (int vertex : vertexes) {
            graph.addVertex(vertex);
        }
    }

    public double calculateMaxFlow(Integer source, Integer target) {
        double result = 0;
        double flowUpdate = 0;
        do {
            flowUpdate = updateFlowByOnePathBetween(source, target);
            result += flowUpdate;
        } while (flowUpdate != 0);
        return result;
    }

    private double updateFlowByOnePathBetween(Integer source, Integer target) {
        List<Integer> path = bfsAlgorithm.findPathBetween(source, target);
        if (path != null && !path.isEmpty()) {
            double minWeightValue = findMinEdgeValueOfPath(path);
            substractFlow(path, minWeightValue);
            updateResidualNetworkWithPathFlow(path, minWeightValue);
            return minWeightValue;
        }
        return 0;
    }

    private double findMinEdgeValueOfPath(List<Integer> path) {
        double minEdgeWeight = Double.MAX_VALUE;
        for (int i = 0; i < path.size() - 1; i++) {
            DefaultWeightedEdge edge = editedGraph.getEdge(path.get(i), path.get(i + 1));
            double edgeWeight = editedGraph.getEdgeWeight(edge);
            if (minEdgeWeight > edgeWeight) {
                minEdgeWeight = edgeWeight;
            }
        }
        return minEdgeWeight;
    }

    private void updateResidualNetworkWithPathFlow(List<Integer> path, double weightToUpdate) {
        for (int i = 0; i < path.size() - 1; i++) {
            DefaultWeightedEdge edge = residualNetwork.getEdge(path.get(i), path.get(i + 1));
            if (edge != null) {
                double currentWeight = residualNetwork.getEdgeWeight(edge);
                residualNetwork.setEdgeWeight(edge, currentWeight + weightToUpdate);
            } else {
                edge = residualNetwork.addEdge(path.get(i), path.get(i + 1));
                residualNetwork.setEdgeWeight(edge, weightToUpdate);
            }
        }

    }

    private void substractFlow(List<Integer> path, double subtrahend) {
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
