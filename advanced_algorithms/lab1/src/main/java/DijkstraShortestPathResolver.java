import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class DijkstraShortestPathResolver {
    private final Graph<Integer, DefaultWeightedEdge> graph;

    public DijkstraShortestPathResolver(Graph<Integer, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public PathInGraph findShortestPathBetween(Integer source, Integer target) {
        Map<Integer, Integer> distanceFromSourceTable = initDijkstraTable(source);
        Map<Integer, Integer> previousVertexInShortestPath = initPreviousVertexTable(source);
        Set<Integer> unvisitedVertexes = new HashSet<>(distanceFromSourceTable.keySet());
        Integer examinedVertex = source;
        while (!unvisitedVertexes.isEmpty()) {
            unvisitedVertexes.remove(examinedVertex);
            updateDistanceTable(distanceFromSourceTable, previousVertexInShortestPath, examinedVertex);
            Optional<Integer> min = findNextVisitedNodeWithShortestDistance(distanceFromSourceTable, unvisitedVertexes);
            if(!min.isPresent()){
                break;
            }
            examinedVertex = min.get();
        }
        return traversPath(source, target, previousVertexInShortestPath);
    }

    private PathInGraph traversPath(Integer source, Integer target, Map<Integer, Integer> previousVertexInShortestPath) {
        List<Integer> pathBackwards = new ArrayList<>();
        pathBackwards.add(target);
        Integer examinedVertex = target;
        while (!examinedVertex.equals(source)) {
            Integer previousVertex = previousVertexInShortestPath.get(examinedVertex);
            pathBackwards.add(previousVertex);
            examinedVertex = previousVertex;
        }
        int sum = 0;
        for (int i = 0; i < pathBackwards.size() - 1; i++) {
            Integer edgeTarget = pathBackwards.get(i);
            Integer edgeSource = pathBackwards.get(i + 1);
            DefaultWeightedEdge edge = graph.getEdge(edgeSource, edgeTarget);
            int edgeWeight = (int) graph.getEdgeWeight(edge);
            sum += edgeWeight;
        }
        Collections.reverse(pathBackwards);
        return new PathInGraph(pathBackwards, sum);
    }

    private Map<Integer, Integer> initPreviousVertexTable(Integer source) {
        Map<Integer, Integer> result = new HashMap<>();
        for (Integer vertex : graph.vertexSet()) {
            result.put(vertex, Integer.MAX_VALUE);
        }
        result.put(source, source);
        return result;
    }

    private Optional<Integer> findNextVisitedNodeWithShortestDistance(Map<Integer, Integer> distanceFromSourceTable, Set<Integer> unvisitedVertexes) {
        return distanceFromSourceTable.entrySet().stream()
                .filter(e -> unvisitedVertexes.contains(e.getKey()))
                .min(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }

    private void updateDistanceTable(Map<Integer, Integer> distanceFromSourceTable, Map<Integer, Integer> previousVertexInShortestPath, Integer examinedVertex) {
        Set<DefaultWeightedEdge> outgoingEdges = graph.outgoingEdgesOf(examinedVertex);
        Integer edgeSourceDistance = distanceFromSourceTable.get(examinedVertex);
        for (DefaultWeightedEdge edge : outgoingEdges) {
            Integer edgeTarget = graph.getEdgeTarget(edge);
            int edgeWeight = (int) graph.getEdgeWeight(edge);
            Integer edgeTargetDistance = distanceFromSourceTable.get(edgeTarget);
            if (edgeSourceDistance + edgeWeight < edgeTargetDistance) {
                distanceFromSourceTable.put(edgeTarget, (edgeSourceDistance + edgeWeight));
                previousVertexInShortestPath.put(edgeTarget, examinedVertex);
            }
        }
    }

    private Map<Integer, Integer> initDijkstraTable(Integer source) {
        Map<Integer, Integer> dijkstraTable = new HashMap<>();
        for (Integer vertex : graph.vertexSet()) {
            dijkstraTable.put(vertex, Integer.MAX_VALUE);
        }
        dijkstraTable.put(source, 0);
        return dijkstraTable;
    }
}
