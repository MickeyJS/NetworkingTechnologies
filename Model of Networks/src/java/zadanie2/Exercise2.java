package zadanie2;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.*;
import java.lang.*;
import java.io.*;


public class Exercise2 {

    private static int quantity;
    private static int G = 0;
    private static int[][] N = new int[10][10];
    private static double m = 0;
    private static double h;
    private static int success = 0;
    private static int failGraphIsNotConnective = 0;
    private static int failGreaterThanTmax = 0;
    private static int failLowBandwidth = 0;
    private static double Tmax;
    private static int vertexNumber = 1;

    private static SimpleGraph<Integer, MyEdge> createGraph(){ // UNUSED METHOD TO CREATE RANDOM GRAPH
        SimpleGraph<Integer, MyEdge> graph = new SimpleGraph<>(MyEdge.class);
        ArrayList<String> graphArray = new ArrayList<>();
        for(int i = 1; i < 10 ; i++)
            graphArray.add(i + " " + (i+1));
        graphArray.add("1 10");
        String[] line;
        int v1,v2;
        Random r = new Random();
        for(String g : graphArray){
            line = g.split(" ");
            v1 = Integer.parseInt(line[0]);
            v2 = Integer.parseInt(line[1]);
            graph.addVertex(v1);
            graph.addVertex(v2);
            MyEdge e = new MyEdge();
            e.setFlow(0);
            e.setCapacity((500+r.nextInt(200)));
            e.setWeight(h);
            graph.addEdge(v1,v2,e);
        }
        addRandomEdge(graph);
        return graph;
    }
    
    
    
    
    private static SimpleGraph<Integer, MyEdge> buildGraphFromFile(String fileName)
            throws FileNotFoundException {
    	   SimpleGraph<Integer, MyEdge> graph = new SimpleGraph<>(MyEdge.class);
           //ArrayList<String> graphArray = new ArrayList<>();
        try {
            File file = new File(fileName);
            InputStreamReader streamReader = new InputStreamReader(
                    new FileInputStream(file));
            BufferedReader br = new BufferedReader(streamReader);
            String line = br.readLine();

            // vertices
            if (line != null) {
                String[] vertexName = line.split(" ");
                int[] vertex = new int[vertexName.length];
                vertexNumber = vertex.length;                //helpful to Dijkstra ALghorithm
                for (int i = 0; i < vertex.length; ++i) {
                    vertex[i] = Integer.parseInt(vertexName[i]); //set vertex

                }

                // edges
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(" ");

                    int vertexA = Integer.parseInt(tokens[0]);
                    int vertexB = Integer.parseInt(tokens[1]);
                    int capacity = Integer.parseInt(tokens[2]);
//                   System.out.println(Integer.parseInt(tokens[2]));
//                    System.out.println(Integer.parseInt(tokens[1]));

                    graph.addVertex(vertexA);
                    graph.addVertex(vertexB);
                    
                    MyEdge e = new MyEdge();		//set Edges
                    e.setFlow(0);
                    e.setCapacity(capacity);
                    e.setWeight(h);
                    graph.addEdge(vertexA,vertexB,e);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return graph;
    }

    private static SimpleGraph<Integer, MyEdge> addRandomEdge(SimpleGraph<Integer, MyEdge> graph){
        int r1, r2;
        Random r = new Random();
        for(int i = 0; i < 7; i++){
            do {
                r1 = r.nextInt(10)+1;
                r2 = r.nextInt(10)+1;
            } while ((r1 == r2) || (graph.containsEdge(r1, r2)) );
            graph.addVertex(r1);
            graph.addVertex(r2);
            MyEdge e = new MyEdge();
            e.setWeight(h);
            e.setFlow(0);
            e.setCapacity((500+r.nextInt(200)));
            graph.addEdge(r1, r2, e);
        }
        return graph;
    }

    private static int[][] createRandomMatrix(){ // UNUSED METHOD TO CREATE RANDOM MATRIX
        Random r = new Random();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(i == j)
                    N[i][j] = 0;
                else {
                    N[i][j] = r.nextInt(10);
                    G += N[i][j];
                }
            }
        }
//        for(int i = 0; i < 10; i++){
//            for(int j = 0; j < 10; j++){
//                System.out.print(N[i][j]+ " ");
//            }
//            System.out.println();
//        }
       m = G/100;
       // System.out.println("G: "+G + "  m: " + m);
        return N;
    }
    
    
    private static int[][] createMatrix(String fileName) throws IOException
    {
    	
    	    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    	    int[][] numArray;
    	    String line = reader.readLine();
    	    if (line == null) {								
    	        throw new IllegalArgumentException("There was no 1st line.");
    	    }
    	    String[] dimensions = line.split("\\s+");
    	    try {														//First line Set dimension of matrix
    	        int rows = Integer.parseInt(dimensions[0]);
    	        int cols = Integer.parseInt(dimensions[1]);
    	        
    	        numArray = new int[rows][cols];
    	    } catch (NumberFormatException e) {
    	        throw new IllegalArgumentException("First line of file has to be 'rows cols'");
    	    }

    	    int row = 0; 

    	    while ((line = reader.readLine()) != null && row < numArray.length) {    //Values of matrix
    	        String[] tokens = line.split("\\s+");
    	        if (tokens.length > numArray[row].length) {
    	            throw new IllegalArgumentException("Too many values provided in matrix row " + row);
    	        }
    	        // to less values will be filled with 0. If you don't want that
    	        // you have to uncomment the next 3 lines.
    	        //if (tokens.length < numArray[row].length) {
    	        //  throw new IllegalArgumentException("Not enough values provided in matrix row " + row);
    	        //}
    	        for(int column = 0; column < tokens.length; column++) {
    	            try {
    	                int value = Integer.parseInt(tokens[column]);
    	                numArray[row][column] = value; 
    	                G += numArray[row][column];
    	            } catch (NumberFormatException e) {
    	                throw new IllegalArgumentException("Non numeric value found in matrix row " + row + ", column " + column);
    	            }
    	        }
    	        row++;
    	    }
    	    if (line != null) {
    	        // there were too many rows provided.
    	        // Superflous ones are ignored.
    	        // You can also throw exception, if you want.
    	    }
    	    if (row < numArray.length) {
    	        // There were too less rows in the file. If that's OK, the
    	        // missing rows will be interpreted as all 0.
    	        // If that's OK with you, you can comment out this whole
    	        // if block
    	        throw new IllegalArgumentException("Expected " + numArray.length + " rows, there only were " + row);
    	    }
    	    try {
    	        reader.close(); 						// never forget to close a stream.
    	    } catch (IOException e) { }
    	    m = G/100;
    	   // System.out.println("G: "+G + "  m: " + m);
    	    return numArray;


    }
    


    private static void searchPath(SimpleGraph<Integer, MyEdge> graph, int[][] N) {
        for (int i = 0; i < vertexNumber; i++){
            for(int j = 0; j < vertexNumber; j++){
                List<MyEdge> path = DijkstraShortestPath.findPathBetween(graph, i+1, j+1);
                for(MyEdge e : path){
                    int flow =  e.getFlow();
                    e.setFlow(flow + N[i][j]);
                }
            }
        }
    }


    private static boolean check(SimpleGraph graph){
        Set<MyEdge> edges = graph.edgeSet();
        for(MyEdge e : edges){
            int x = e.getCapacity() - e.getFlow()* (int)m;
            if(x < 0){
                failLowBandwidth++;
                return true;
            }
        }
        return false;
    }

    private static void avgDelay(SimpleGraph graph, boolean fail){
        if (fail){
        //   System.out.println("Error");
        }
        else{
            double T = 0;
            Set<MyEdge> edges = graph.edgeSet();
            for (MyEdge e: edges) {
               // System.out.println(e + " : Flow -> " + e.getFlow()+ ", Capacity -> "+ e.getCapacity());
                double x = (double) e.getCapacity()/m;
                T +=  ((double)e.getFlow()/( x - (double) e.getFlow()));

            }
            if ((T/G) < Tmax){
                success++;
              //  System.out.println("T: "+ T/G);
            }
            else{
            	//System.out.println("T: "+ T/G);
                failGreaterThanTmax++;
            }
        }
    }

    public static boolean testGraph(SimpleGraph<Integer, MyEdge> graph){
        Random r = new Random();
        double p;
            Set<MyEdge> edges = graph.edgeSet();
            MyEdge[] edgesArray = edges.toArray(new MyEdge[0]);
            for(MyEdge e : edgesArray) {
                p = r.nextDouble();
                double h = e.getWeight();
                if (p > h) {
                    graph.removeEdge(e);
                }
            }
            ConnectivityInspector<Integer, MyEdge> con = new ConnectivityInspector(graph);
            if (!con.isGraphConnected()){
                failGraphIsNotConnective++;
                return false;
            }

        return true;
    }

    public static void makeExercises(String fileMatrix, String fileGraph) throws IOException, FileNotFoundException{
        SimpleGraph<Integer, MyEdge> graph;
        boolean c, isConnective;
        G=0;
        //graph = createGraph();
        graph = buildGraphFromFile(fileGraph);
        N = createMatrix(fileMatrix);
        //N = createRandomMatrix();
        isConnective = testGraph(graph);
        if (isConnective){
        searchPath(graph, N);
        c = check(graph);
        avgDelay(graph, c);
        }
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        //Main do zadania 2
            success = 0;
            failGreaterThanTmax = 0;
            failLowBandwidth = 0;
            failGraphIsNotConnective = 0;
            
            String fileMatrix, fileGraph;
            Scanner odczyt = new Scanner(System.in); //obiekt do odebrania danych od użytkownika
            
            System.out.println("Podaj scieżke pliku, gdzie znajduję się macierz:");
            fileMatrix = odczyt.nextLine();
            
            System.out.println("Podaj scieżke pliku, gdzie znajduję się graf:");
            fileGraph = odczyt.nextLine();
            
            System.out.println("Podaj Tmax:");
            Tmax = Double.parseDouble(odczyt.nextLine());
            
            System.out.println("Podaj liczbę prób:");
            quantity = Integer.parseInt(odczyt.nextLine());
            
            System.out.println("Podaj niezawodność sieci [h]:");
            h = Double.parseDouble(odczyt.nextLine());
            
            odczyt.close();
            
            for (int i = 0; i < quantity; i++)
                makeExercises(fileMatrix, fileGraph);
            System.out.println("Success: " + success);
            
            System.out.println("Fail - low bandwidth: " + failLowBandwidth + " | time is greater than Tmax: " + failGreaterThanTmax +
                    "  | graph isn't connective: " + failGraphIsNotConnective);
            
            System.out.println("Connection reliability: " + ((double) success * 100 / quantity) + "%");
        
    }
}