package sieci.zadanie1;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Exercise1 {


    private final Random generator = new Random();

    public void start() {

        List<GraphFactory> graphs = new ArrayList<>();

        graphs.add(new A());
        graphs.add(new B());
        graphs.add(new C());
        graphs.add(new D());

        for (GraphFactory graphFactory : graphs) {

            System.out.println();

            int all = 0;
            int passed = 0;

            System.out.print("SPRAWDZAMY GRAF: " + graphFactory.toString());

            while (all!=1000) {

                @SuppressWarnings("unchecked")
                SimpleWeightedGraph<Integer, DefaultWeightedEdge> graf = graphFactory.createGraph();

                ArrayList<DefaultWeightedEdge> krawedzie = new ArrayList<>();
                krawedzie.addAll(graf.edgeSet());

                for (DefaultWeightedEdge krawedz : krawedzie) {
                    double weight = graf.getEdgeWeight(krawedz);

                    double number = generator.nextDouble();
                    System.out.println(number);
                    if (number > weight) {
                        //Usuwamy krawędź
                        graf.removeEdge(krawedz);
                    }

                }

                if (isConnectivity(graf)) {
                    passed++;
                }


                all++;

             //   System.out.print("\r");
              //  System.out.print(graphFactory.toString() + ": " + passed * 100 / all + "%  " + passed+"/" + all);


                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            System.out.print("\r");
            System.out.print(graphFactory.toString() + ": " + passed * 100 / all + "%  " + passed+"/" + all);

        }
    }

    @SuppressWarnings("unchecked")
    private boolean isConnectivity(UndirectedGraph graph) {
        return new ConnectivityInspector<>(graph).isGraphConnected();
    }


}
