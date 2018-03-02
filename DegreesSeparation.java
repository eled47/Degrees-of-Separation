import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Float.POSITIVE_INFINITY;

/**
 * Uses a graph to calculate degrees of separation between a given set
 * of musicians
 */
public class DegreesSeparation {

    private Map<String,Vertex> musicians;

    public DegreesSeparation(){
        musicians = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        if (musicians.containsKey(v.name))
            throw new IllegalArgumentException("Cannot create new vertex with existing name.");
        musicians.put(v.name, v);
    }

    public void addEdge(String a, String b) {
        if (!musicians.containsKey(a))
            throw new IllegalArgumentException(a + " does not exist. Cannot create edge.");
        if (!musicians.containsKey(b))
            throw new IllegalArgumentException(b + " does not exist. Cannot create edge.");
        Vertex sourceVertex = musicians.get(a);
        Vertex targetVertex = musicians.get(b);
        Edge newEdge = new Edge(sourceVertex, targetVertex);
        sourceVertex.addConnect(newEdge);
    }

    public void addUndirectedEdge(String a, String b) {
        addEdge(a, b);
        addEdge(b, a);
    }

    //do unweighted shortest path algorithm, which is basically Dijkstra
    public void dijkstraUnw(String s){
        List<Vertex> queue = new LinkedList<>();

        Set<String> vertices = musicians.keySet();

        //initialize all distances to infinity
        for(String a : vertices){
            Vertex vtx = musicians.get(a);
            vtx.distance = POSITIVE_INFINITY;
        }
        Vertex v = musicians.get(s);
        v.distance = 0;
        queue.add(v);

        while(!queue.isEmpty()){
            Vertex min = queue.remove(0);

            List<Edge> musicConnects = min.connects;
            for(Edge e : musicConnects){
                Vertex vt = e.target;
                if(vt.distance == POSITIVE_INFINITY) {
                    vt.distance = min.distance + 1;
                    vt.prev = min;
                    queue.add(vt);
                }
            }
        }
    }

    public int getDistance(String s, String t){
        dijkstraUnw(s);
        Vertex a = musicians.get(t);
        return (int) a.distance;
    }

    public static void main(String[] args) throws IOException{
        String musFile = "musicians.txt";
        String pairFile = "musicianPairs.txt";

        DegreesSeparation dees = new DegreesSeparation();
        String line;

        //Read in the musicians file
        BufferedReader vtxFileBr = new BufferedReader(new FileReader(musFile));
        while((line = vtxFileBr.readLine()) != null){
            Vertex vtx = new Vertex(line);
            dees.addVertex(vtx);
        }
        vtxFileBr.close();

        //Read in the musician pairs file
        BufferedReader edgeBr = new BufferedReader(new FileReader(pairFile));
        while((line = edgeBr.readLine()) != null){
            String[] pair = line.split(",");
            if (pair.length != 2) {
                edgeBr.close();
                throw new IOException("Invalid line in edge file " + line);
            }
            dees.addUndirectedEdge(pair[0],pair[1]);
        }
        edgeBr.close();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter two artists");
        String mus1 = in.nextLine();
        String mus2 = in.nextLine();
        int d = dees.getDistance(mus1,mus2);
        //Have to fix bug it's saying Infinity degrees of separation
        //Have to make a method that retrieves the distance b/c it's not
        //actually affecting the vertex object in the hashmap only the
        //vertex objects in the adjacency lists, so vertex Rihanna in the
        //adjacency list is different than vertex Rihanna in the Hashmap
        //Still gotta fix bugs
        System.out.println("There are "+d+" degrees of separation between these" +
                " artists");
    }
}

