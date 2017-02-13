import java.util.Map;

public class Heap {

    private final int MAX_SIZE = 100;
    private int size;
    private Vertex[] pq;

    public Heap() {
        this.size = 0;
        this.pq = new Vertex[MAX_SIZE];
    }

    public Heap(Graph g) {
        this.size = 0;
        this.pq = new Vertex[MAX_SIZE];
        for (Map.Entry<String, Vertex> entry : g.getVertices().entrySet()) {
            insert(entry.getValue());
            System.err.println(this.toString() + "\n");
        }
    }

    public boolean hasVertex(Vertex v) {
        boolean contains = false;
        for (int i = 0; i < this.size; i++) {
            if (pq[i].getName().equals(v.getName())) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public int parentIndex(int p) {
        return p/2;
    }

    public boolean hasParent(int p) {
        return p > 1;
    }

    public boolean hasLeftChild(int p) {
        return pq[leftChildIndex(p) - 1] != null;
    }

    public int leftChildIndex(int p) {
        return (2 * p);
    }

    public boolean hasRightChild(int p) {
        return pq[rightChildIndex(p) - 1] != null;
    }

    public int rightChildIndex(int p) {
        return (2 * p) + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Vertex e) {

        if (this.size >= pq.length) {
            pq = new Vertex[size];
        }
        size++;
        pq[size-1] = e;
        heapifyUp();
    }

    public void heapifyUp() {
        int p = size;
        while (hasParent(p) && pq[parentIndex(p)-1].compareTo(pq[size-1]) > 0) {
            swap(p, parentIndex(p));
            p = parentIndex(p);
        }
    }

    public void heapifyDown(int p) {
        while (hasLeftChild(p)) {
            int smaller;
            if (!hasRightChild(p)) {
                smaller = leftChildIndex(p);
            }
            else if (pq[leftChildIndex(p)-1].compareTo(pq[rightChildIndex(p)-1]) > 0) {
                smaller = rightChildIndex(p);
            }
            else {
                smaller = leftChildIndex(p);
            }

            if (pq[p-1].compareTo(pq[smaller-1]) > 0) {
                swap(p, smaller);
            }
            else
                break;
            p = smaller;
        }
    }

    public Vertex poll() {
        if (this.isEmpty())
            return null;

        Vertex smallest = pq[0];
        swap(1, size);
        pq[size-1] = null;
        size--;
        heapifyDown(1);
        return smallest;
    }

    public void remove(Vertex v) {
        int remIndex = 0;
        boolean contains = false;
        for (int i = 0; i < this.size; i++) {
            if (pq[i].getName().equals(v.getName())) {
                remIndex = i;
                contains = true;
                break;
            }
        }
        if (contains) {
            for (int i = remIndex; i < size; i++)
                pq[i] = pq[i+1];
            heapifyDown(remIndex+1);
            size--;
        }
    }

    public void swap(int p1, int p2) {
        Vertex temp;
        temp = pq[p1-1];
        pq[p1-1] = pq[p2-1];
        pq[p2-1] = temp;
    }

    @Override
    public String toString() {
        String retval = "";
        for (Vertex each : pq) {
            if (each != null) retval += each.toString() + "\n";
        }
        return retval;
    }

}
