import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dijkstra {

    private Graph graph;
    private Heap priorityQueue;
    private Map<String, Double> done;
    private Map<String, Double> mins;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        this.priorityQueue = new Heap();
        this.done = new HashMap<String, Double>();
        this.mins = new HashMap<String, Double>();
    }

    public void computePaths(Vertex source) {
        source.setMinDistance(0.0);
        priorityQueue.insert(source);

        while (!priorityQueue.isEmpty()) {

            Vertex u = priorityQueue.poll();
            done.put(u.toString(), u.getMinDistance());

            List<Vertex> neighbors;
            neighbors = graph.getAdjList().get(u.toString());

            for (Vertex v : neighbors) {
                if (!done.containsKey(v.toString())) {
                    String s = u.toString() + "," + v.toString();
                    Map<String, Integer> weights = graph.getEdgeWeights();

                    if (weights.get(s) == null)
                        s = v.toString() + "," + u.toString();

                    double weight = weights.get(s);
                    double distanceThroughU = done.get(u.toString()) + weight;

                    if (mins.containsKey(v.toString()))
                        v.setMinDistance(mins.get(v.toString()));
                    else
                        v.setMinDistance(Double.POSITIVE_INFINITY);
                    if (distanceThroughU < v.getMinDistance()) {
                        priorityQueue.remove(v);
                        v.setMinDistance(distanceThroughU);
                        mins.put(v.toString(), v.getMinDistance());
                        priorityQueue.insert(v);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return lexicographicOrder();
    }
    
    private String lexicographicOrder() {
        String[] sort = new String[graph.getVertices().size()];
        int i = 0;
        for (Map.Entry<String, Vertex> entry : graph.getVertices().entrySet()) {
            String key = entry.getKey();
            sort[i] = key + "=" + done.get(key) + ",";
            i++;
        }
        Arrays.sort(sort);
        StringBuilder builder = new StringBuilder();
        for (String string : sort) {
            builder.append(string);
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));
        String currentLine = null;
        Graph g = new Graph();
        Dijkstra d = new Dijkstra(g);

        while ((currentLine = br.readLine()) != null) {
            String[] split = currentLine.split("=");
            String[] verts = split[0].split(",");

            Vertex v1 = new Vertex(verts[0]);
            Vertex v2 = new Vertex(verts[1]);
            Edge e = new Edge(v1, v2, Integer.parseInt(split[1]));
            g.setEdge(e);
        }
        long startTime = System.nanoTime();
        br.close();
        
        Vertex start = g.getVertices().get("V0");
        d.computePaths(start);
        System.out.println(d.toString());

        BufferedReader br2 = new BufferedReader(new FileReader(new File(args[1])));
        while ((currentLine = br2.readLine()) != null) {
            String[] split = currentLine.split("=");
            String[] verts = split[0].split(",");

            Vertex v1 = new Vertex(verts[0]);
            Vertex v2 = new Vertex(verts[1]);

            if (split[1].equals("D")) {
                v1.removeNeighbors(v2);
                g.removeFromAdjList(v1, v2);
            }
            else if (split[1].equals("U")){
                v1.addNeighbor(v2);
                g.addToAdjList(v1, v2);
            }
            else {
                throw new IllegalArgumentException("Input can only be \"D\" or \"U\"");
            }

            Dijkstra d2 = new Dijkstra(g);
            d2.computePaths(start);
            System.out.println(d2.toString());
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.err.println("Elapsed time is: " + elapsedTime + " nanoseconds.");
        br2.close();
    }
}
