import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.ClosestFirstIterator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DijkstraShortestPathResolver {
    private final Graph<Integer, DefaultWeightedEdge> graph;

    public DijkstraShortestPathResolver(Graph<Integer, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public PathInGraph findShortestPathBetween(Integer source, Integer target){
        Map<Integer, Integer> dijkstraTable = initDijkstraTable(source);
        for(Integer currentNode : dijkstraTable.keySet()){
            Set<DefaultWeightedEdge> outgoingEdges = graph.outgoingEdgesOf(currentNode);
            for(DefaultWeightedEdge edge : outgoingEdges){
                Integer edgeTarget = graph.getEdgeTarget(edge);
                Integer edgeSource = graph.getEdgeSource(edge);
                if(dijkstraTable.get(ed))
            }
            if(dijkstraTable.get(currentNode) > outgoingEdges.)
        }
        graph.g
    }

    private Map<Integer, Integer> initDijkstraTable(Integer source) {
        Map<Integer,Integer> dijkstraTable = new HashMap<>();
        for(Integer vertex : graph.vertexSet()){
            dijkstraTable.put(vertex, Integer.MAX_VALUE);
        }
        dijkstraTable.put(source, 0);
        return dijkstraTable;
    }
}
