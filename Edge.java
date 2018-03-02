/**
 * Edge class that models edge in a graph
 * Edges for this graph are unweighted
 * All edges are also undirected
 */
public class Edge {
    public Vertex source;
    public Vertex target;
    private int weight = 1;

    public Edge(Vertex src, Vertex trgt){
        source = src;
        target = trgt;
    }

    public String toString(){
        return source + " - " + target;
    }
}
