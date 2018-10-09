import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(Main.class.getClassLoader().getResource("graf.txt").getFile());
        Graph<Integer, DefaultWeightedEdge> graph = createGraphOfVertex(inputFile);
        initGraphEdges(graph, inputFile);

        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath(graph);
        GraphPath path = dijkstraShortestPath
                .getPath(1, 20);
        List<Integer> shortestPath = path.getVertexList();
        String vertexes = shortestPath.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println(vertexes);
        System.out.println(path.getWeight());
    }

    private static void initGraphEdges(Graph<Integer, DefaultWeightedEdge> graph, File inputFile) throws FileNotFoundException {
        try (var scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");
                Integer source = Integer.valueOf(line[0].trim());
                Integer target = Integer.valueOf(line[1].trim());
                Integer edgeWeight = Integer.valueOf(line[2].trim());
                DefaultWeightedEdge edge = graph.addEdge(source, target);
                graph.setEdgeWeight(edge, edgeWeight);
            }
        }
    }

    private static Graph<Integer, DefaultWeightedEdge> createGraphOfVertex(File inputFile) throws FileNotFoundException {
        var graph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        try (var scanner = new Scanner(inputFile)) {
            Set<Integer> vertexes = new HashSet<>();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");
                vertexes.add(Integer.valueOf(line[0].trim()));
                vertexes.add(Integer.valueOf(line[1].trim()));
            }
            for (Integer vertex : vertexes) {
                graph.addVertex(vertex);
            }
        }
        return graph;
    }
}
