package zadanie2;

import org.jgrapht.graph.DefaultEdge;


public class MyEdge extends DefaultEdge {
    private int capacity;
    private int flow;
    private double weight;
    int vertex;
    
    public void addNumberVertex()
    {
    	vertex++;
    }

    public int getVertex(){
    	return vertex;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
    
    public void setCapacity(int x) {
        this.capacity = x;
    }
    
    public void setFlow(int y){
        this.flow = y;
    }

    
    public double getWeight(){
        return weight;
    }

    public int getCapacity() {
        return this.capacity;
    }


    public int getFlow() {
        return this.flow;
    }
}
