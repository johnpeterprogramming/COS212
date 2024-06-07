
public class AdjacencyMatrix<T> implements GraphImplementation<T> {
    public LinkedList<T> nodes; // Stores the nodes to maintain index mapping
    public boolean[][] matrix; // Adjacency matrix to store edge information

    /*
     * Constructor
     */
    public AdjacencyMatrix() {
        nodes = new LinkedList<>();
        matrix = new boolean[0][0];
    }

    /*
     * Public methods to adhere to the interface
     */
    @Override
    public void addNode(T data) {
        this.nodes.add(data);
        int size = this.nodes.size();

        boolean[][] newMatrix = new boolean[size][size]; 

        for (int i=0; i < size - 1; i++) 
            for (int j=0; j < size - 1; j++) 
                newMatrix[i][j] = this.matrix[i][j];
        
        this.matrix = newMatrix;
    }

    @Override
    public void addEdge(T dataFrom, T dataTo) {
        this.matrix[this.nodes.indexOf(dataFrom)][this.nodes.indexOf(dataTo)] = true;
        // this.matrix[this.nodes.indexOf(dataTo)][this.nodes.indexOf(dataFrom)] = true;
    }

    public void removeNode(T data) {
        int removeIndex = this.nodes.indexOf(data);
        int size = this.matrix.length - 1;

        boolean[][] newMatrix = new boolean[size][size];

        int i = 0;
        int j = 0;

        for (int k=0; k<size + 1;k++) {
            for (int l=0; l<size + 1;l++) {
                if (k != removeIndex && l != removeIndex) {
                    newMatrix[i][j] = this.matrix[k][l];
                    j++;
                    if (j == size) {
                        j = 0;
                        i++;
                    }
                }

            }
        }

        this.matrix = newMatrix;
        this.nodes.remove(data);
    }

    public void removeEdge(T dataFrom, T dataTo) {
        this.matrix[this.nodes.indexOf(dataFrom)][this.nodes.indexOf(dataTo)] = false;
    }

    @Override
    public int numberOfNodes() {
        return this.nodes.size();

    }

    @Override
    public int numberOfEdges() {
        int total = 0;
        for (int i=0; i < this.matrix.length; i++) {
            for (int j=0; j < this.matrix[i].length; j++) {
                if (this.matrix[i][j]) {
                    total++;
                }
            }
        }
        return total;

    }

    @Override
    public boolean containsNode(T data) {
        return (this.nodes.indexOf(data) != -1);

    }

    @Override
    public int indexOfNode(T data) {
        return this.nodes.indexOf(data);
    }

    @Override
    public T nodeAtIndex(int index) {
        try {
            return this.nodes.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public boolean isConnected(T dataFrom, T dataTo) {
        if (this.nodes.indexOf(dataFrom) == -1 || this.nodes.indexOf(dataTo) == -1) {
            return false;
        }
        return this.matrix[this.nodes.indexOf(dataFrom)][this.nodes.indexOf(dataTo)];
    }

    @Override
    public LinkedList<T> getNeighbors(T data) {
        LinkedList<T> neighbors = new LinkedList<>();

        int index = this.nodes.indexOf(data);
        for (int i=0; i < this.matrix[index].length; i++) {
            if (this.matrix[index][i]) {
                neighbors.add(this.nodes.get(i));
            }
        }

        return neighbors;
    }

    @Override
    public String display() {
        String out = ("Adjacency Matrix:\n");
        for (int i = 0; i < matrix.length; i++) {
            out += "\t" + nodes.get(i) + ": ";
            for (int j = 0; j < matrix[i].length; j++) {
                out += (matrix[i][j] ? "1 " : "0 ");
            }
            out += "\n";
        }
        return out;
    }

    /*
     * Private methods used for this implementation
     */

}
