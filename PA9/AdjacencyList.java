class AdjacencyList<T> implements GraphImplementation<T> {

    public LinkedList<Node<T>> nodes;

    /*
     * Constructor
     */
    public AdjacencyList() {
        this.nodes = new LinkedList<>();
    }

    /*
     * Public methods to adhere to the interface
     */
    @Override
    public void addNode(T data) {
        Node<T> newNode = new Node(data);
        this.nodes.add(newNode);
    }

    @Override
    public void addEdge(T dataFrom, T dataTo) {

        Node<T> dataFromNode = null;
        Node<T> dataToNode = null;

        for (int i=0; i < this.nodes.size(); i++) {
            Node<T> curr = this.nodes.get(i);

            if (curr.data.equals(dataFrom))
                dataFromNode = curr;
            if (curr.data.equals(dataTo))
                dataToNode = curr;
        }

        // Nothing happens if BOTH nodes don't exist
        if (dataFromNode != null && dataToNode != null)
            dataFromNode.addEdge(dataToNode);


    }

    public void removeNode(T data) {
        for (int i=0; i < this.nodes.size(); i++) {
            Node<T> curr = this.nodes.get(i);
            
            if (curr.data.equals(data)) {
                this.nodes.remove(curr);
                break;
            }
        }

    }

    public void removeEdge(T dataFrom, T dataTo) {
        for (int i=0; i < this.nodes.size(); i++) {
            Node<T> curFrom1 = this.nodes.get(i);
        
            if (curFrom1.data.equals(dataFrom)) {
                for (int j=0; j < curFrom1.getEdges().size(); j++) {
                    Node<T> curFrom2 = curFrom1.getEdges().get(j);

                    if (curFrom2.data.equals(dataTo)) {
                        curFrom1.removeEdge(curFrom2);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public int numberOfNodes() {
        return this.nodes.size();
    }

    @Override
    public int numberOfEdges() {
        int total = 0;
        for (int i=0; i<this.nodes.size(); i++) {
            total += this.nodes.get(i).getEdges().size();
        }

        return total;
    }

    @Override
    public boolean containsNode(T data) {
        for (int i=0; i<this.nodes.size(); i++) {
            if (this.nodes.get(i).data.equals(data))
                return true;
        }    
        return false;
    }

    @Override
    public int indexOfNode(T data) {
        for (int i=0; i<this.nodes.size(); i++) {
            if (this.nodes.get(i).data.equals(data))
                return i;
        }
        return -1;
    }

    @Override
    public T nodeAtIndex(int index) {
        return this.nodes.get(index).data;
    }

    @Override
    public boolean isConnected(T dataFrom, T dataTo) {
        for (int i=0; i < this.nodes.size(); i++) {
            Node<T> curFrom1 = this.nodes.get(i);
        
            if (curFrom1.data.equals(dataFrom)) {
                for (int j=0; j < curFrom1.getEdges().size(); j++) {
                    Node<T> curFrom2 = curFrom1.getEdges().get(j);

                    if (curFrom2.data.equals(dataTo))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<T> getNeighbors(T data) {
        LinkedList<T> neighbors = new LinkedList<>();
        for (int i=0; i < this.nodes.size(); i++) {
            Node<T> cur1 = this.nodes.get(i);
        
            if (cur1.data.equals(data))  {
                for (int j=0; j < cur1.getEdges().size(); j++) 
                    neighbors.add(cur1.getEdges().get(j).data);
            }
            
        }
        return neighbors;

    }

    @Override
    public String display() {
        String out = ("Graph structure:\n");
        for (int i = 0; i < nodes.size(); i++) {
            Node<T> currentNode = nodes.get(i);

            StringBuilder displayString = new StringBuilder("\t" + currentNode.data.toString() + ": ");
            LinkedList<Node<T>> edges = currentNode.getEdges();

            for (int j = 0; j < edges.size(); j++) {
                displayString.append(edges.get(j).data.toString());
                if (j < edges.size() - 1) {
                    displayString.append(", ");
                }
            }

            out += (displayString + "\n");
        }
        return out;
    }

    /*
     * Private methods used for this implementation
     */

    /*
     * Inner nodes used for this implementation
     */
    private class Node<U> {
        public U data;
        public LinkedList<Node<U>> edges;

        public Node(U data) {
            this.data = data;
            this.edges = new LinkedList<>();
        }

        public LinkedList<Node<U>> getEdges() {
            return this.edges;
        }

        public void addEdge(Node<U> newConnection) {
            this.edges.add(newConnection);
        }

        public void removeEdge(Node<U> oldConnection) {
            this.edges.remove(oldConnection);
        }

    }
}
