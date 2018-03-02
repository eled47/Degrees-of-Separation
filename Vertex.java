import java.util.LinkedList;
import java.util.List;

/**
 * Class that models a vertex in a graph
 */
public class Vertex {

    public String name;
    public boolean known;
    public Vertex prev;
    public double distance;
    public List<Edge> connects;

    public Vertex(String name){
        this.name = name;
        known = false;
        connects = new LinkedList<>();
        prev = null;
    }

    public void addConnect(Edge e){
        connects.add(e);
    }

    public String toString(){
        return name;
    }
}
