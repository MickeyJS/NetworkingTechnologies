package sieci.zadanie1;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public interface GraphFactory {
    SimpleWeightedGraph<Integer, DefaultWeightedEdge> createGraph();
}
