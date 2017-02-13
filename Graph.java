
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Graph {
    
    private Map<String, Integer> edgeWeights;
    private Map<String, List<Vertex>> adjList;
    private Map<String, Vertex> vertices;
    
    public Graph() {
        this.edgeWeights = new HashMap<String, Integer>();
        this.adjList = new HashMap<String, List<Vertex>>();
        this.vertices = new HashMap<String, Vertex>();
    }
    
    public Map<String, Integer> getEdgeWeights() {
        return this.edgeWeights;
    }
    
    public Map<String, List<Vertex>> getAdjList() {
        return this.adjList;
    }
    
    public Map<String, Vertex> getVertices() {
        return vertices;
    }
    
    public void setEdge(Edge e) {
        Vertex v1 = e.getVertex1();
        Vertex v2 = e.getVertex2();
        if (!edgeWeights.containsKey(e.toString()))
            this.edgeWeights.put(e.toString(), e.getWeight());
        addVertex(v1);
        addVertex(v2);
        v1.setNeighbor(v2);
        addToAdjList(v1, v2);
    }
    
    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.toString()))
            vertices.put(v.toString(), v);
    }
    
    public void addToAdjList(Vertex v1, Vertex v2) {
        if (!adjList.containsKey(v1.toString())) {
            this.adjList.put(v1.toString(), v1.getNeighbors());   
        }
        else {
            List<Vertex> value = this.adjList.get(v1.toString());
            value.add(v2);
        }
        
        if (!adjList.containsKey(v2.toString())) {
            this.adjList.put(v2.toString(), v2.getNeighbors());   
        }
        else {
            List<Vertex> value = this.adjList.get(v2.toString());
            value.add(v1);
        }
    }
    
    public void removeFromAdjList(Vertex v1, Vertex v2) {
        if (adjList.containsKey(v1.toString())) {
            List<Vertex> neighborList = this.adjList.get(v1.toString());
            for (int i = 0; i < neighborList.size(); i++) {
                if (neighborList.get(i).getName().equals(v2.toString())) {
                    neighborList.remove(i);
                    break;
                }
            }
        }

        if (adjList.containsKey(v2.toString())) {
            List<Vertex> neighborList = this.adjList.get(v2.toString());
            for (int i = 0; i < neighborList.size(); i++) {
                if (neighborList.get(i).getName().equals(v1.toString())) {
                    neighborList.remove(i);
                    break;
                }
            }
        }
    }
    
    @Override
    public String toString() {
        String s = "";
        for (Map.Entry<String, List<Vertex>> entry : adjList.entrySet()) {
            String key = entry.getKey();
            List<Vertex> value = entry.getValue();
            s += key + ": ";
            for (Vertex v : value) {
                s += v.toString() + " ";
            }
            s += "\n";
        }
        return s;
    }
}
