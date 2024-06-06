public class Vertex {
    LinkedList<Edge> edges;
    int xPos;
    int yPos;
    char symbol;
    static int globalCounter = 0;
    int counter = globalCounter++;

    Vertex(int x, int y, char sym) {
        this.xPos = x;
        this.yPos = y;
        this.symbol = sym;
        edges = new LinkedList<Edge>();
    }

    @Override
    public String toString() {
        return "(" + xPos + ":" + yPos + ")[" + symbol + "]";
    }

    String latexCode(){
        return "\\node[node] (" +counter + ") at (" + xPos + "," + (yPos) + ") {" + symbol + "};";
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        try {
            Vertex vertex = (Vertex) obj;
            if (vertex.xPos == xPos && vertex.yPos == yPos) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    Edge[] getEdges() {
        return this.edges.toEdgeArray();
    }

    // Uses selection sort
    public Edge[] getEdgesSorted() {
        Edge[] unsorted = this.edges.toEdgeArray();
        Edge[] sorted = new Edge[unsorted.length];

        for (int i = 0; i < unsorted.length; i++) {
            Edge smallestEdge = unsorted[i];
            for (int j=i + 1; j< unsorted.length; j++) {
                if (smallestEdge.toString().compareTo(unsorted[j].toString()) > 0) {
                    smallestEdge = unsorted[j];
                }
            }
            sorted[i] = smallestEdge;
        }

        return sorted;
    }
}
