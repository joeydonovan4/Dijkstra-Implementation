
public class Edge implements Comparable<Edge> {

    private Vertex v1;
    private Vertex v2;
    private final int weight;

    public Edge(Vertex v1, Vertex v2, int weight) {
        order(v1, v2);
        this.weight = weight;
    }
    
    private void order(Vertex v1, Vertex v2) {
        if (v1.getName().compareTo(v2.getName()) < 0) {
            this.v1 = v1;
            this.v2 = v2;
        }
        else {
            this.v1 = v2;
            this.v2 = v1;
        }
    }

    public Vertex getVertex1() {
        return v1;
    }

    public Vertex getVertex2() {
        return v2;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (!(that instanceof Edge)) {
            return false;
        }
        return this.v1.equals(((Edge)that).v2) &&
                this.v2.equals(((Edge)that).v1)
                ? this.getWeight() == ((Edge)that).getWeight() : false;
    }

    public int compareTo(Edge that) {
        if (this.getWeight() < that.getWeight()) {
            return -1;
        }
        else if (this.getWeight() == that.getWeight()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.v1.getName() + "," + this.v2.getName();
    }

    @Override
    public int hashCode() {
        return v1.hashCode() ^ v2.hashCode();
    }
}
