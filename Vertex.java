
import java.util.List;
import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
    private final String name;
    private List<Vertex> neighbors;
    private double minDistance;
    
    public Vertex(String name) {
        this.name = name;
        this.neighbors = new ArrayList<Vertex>();
        this.minDistance = Double.POSITIVE_INFINITY;
    }
    
    public String getName() {
        return name;
    }
    
    public void removeNeighbors(Vertex v) {
        neighbors.remove(v);
        v.getNeighbors().remove(this);
    }
    
    public void addNeighbor(Vertex v) {
        neighbors.add(v);
    }
    
    public double getMinDistance() {
        return minDistance;
    }
    
    public void setMinDistance(double d) {
        minDistance = d;
    }
    
    public List<Vertex> getNeighbors() {
        return neighbors;
    }
    
    public boolean hasNeighbor(Vertex v) {
        return this.neighbors.contains(v);
    }
    
    // Checks if this vertex has that vertex as its neighbor.
    // If not, adds that to this' neighbors.
    // Then does the same for that vertex
    public void setNeighbor(Vertex v) {
        if (!hasNeighbor(v))
            this.neighbors.add(v);
        if (!v.hasNeighbor(this))
            v.neighbors.add(this);
    }
    
    public boolean hasEdge(Vertex that) {
        return this.neighbors.contains(that);
    }
    
    public int compareTo(Vertex that) {
        return Double.compare(this.minDistance, that.minDistance);
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (!(that instanceof Vertex)) {
            return false;
        }
        return this.name == ((Vertex)that).name;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
