import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class BFSAlgorithm {
    private Graph<Integer, DefaultWeightedEdge> graph;

    public BFSAlgorithm(Graph<Integer, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public List<Integer> findPathBetween(Integer source, Integer target){
        Map<Integer, Integer> previousVertexTable = createReversedPathTableOfSearch(source, target);
        return extractPath(source, target, previousVertexTable);

    }

    private List<Integer> extractPath(Integer source, Integer target, Map<Integer, Integer> previousVertexTable) {
        List<Integer> result = new ArrayList<>();
        Integer pathPointer = target;
        while(!pathPointer.equals(source)){
            result.add(pathPointer);
            pathPointer = previousVertexTable.get(pathPointer);
        }
        result.add(source);
        Collections.reverse(result);
        return result;
    }

    private Map<Integer, Integer> createReversedPathTableOfSearch(Integer source, Integer target) {
        Map<Integer, Integer> previousVertexTable = initPreviousVertex(source);
        Set<Integer> visitedVertexes = new HashSet<>();
        Queue<Integer> toVisitVertexes = new LinkedList<>();

        toVisitVertexes.add(source);

        while (!toVisitVertexes.isEmpty()){
            Integer underConsideration = toVisitVertexes.element();
            visitedVertexes.add(underConsideration);
            Set<DefaultWeightedEdge> outgoingEdgesOfSource = graph.outgoingEdgesOf(source);
            for(DefaultWeightedEdge edge: outgoingEdgesOfSource){
                Integer neighbour = graph.getEdgeTarget(edge);
                if(!visitedVertexes.contains(neighbour)){
                    toVisitVertexes.add(neighbour);
                    previousVertexTable.put(neighbour, underConsideration);
                }
                if(neighbour.equals(target)){
                    break;
                }
            }
        }
        return previousVertexTable;
    }

    private Map<Integer, Integer> initPreviousVertex(Integer source) {
        HashMap<Integer, Integer> result = new HashMap<>();
        graph.vertexSet().forEach(v -> result.put(v ,Integer.MAX_VALUE));
        result.put(source,source);
        return result;
    }
}
