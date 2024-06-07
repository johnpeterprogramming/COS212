public abstract class Graph<T> {
    public GraphImplementation<T> impl;

    Graph(GraphImplementation<T> impl) {
        this.impl = impl;
    }

    public void addNode(T node) {
        impl.addNode(node);
    }

    abstract void addEdge(T from, T to);

    public void removeNode(T node) {
        impl.removeNode(node);
    }

    abstract void removeEdge(T from, T to);

    public void display() {
        System.out.println(impl.display());
    }

    public LinkedList<T> bfs(T startNode) {
        LinkedList<T> visited = new LinkedList<>();
        LinkedList<T> queue = new LinkedList<>();

        visited.add(startNode);
        queue.add(startNode);

        while (queue.size() != 0) {
            T node = queue.get(0);
            queue.removeIndex(0); // Dequeue
            LinkedList<T> neighbors = impl.getNeighbors(node);

            for (int i = 0; i < neighbors.size(); i++) {
                T neighbor = neighbors.get(i);
                if (visited.indexOf(neighbor) == -1) {
                    visited.add(neighbor);
                    queue.add(neighbor); // Enqueue
                }
            }
        }

        return visited;
    }

    public LinkedList<T> dfs(T startNode) {
        LinkedList<T> visited = new LinkedList<>();
        LinkedList<T> stack = new LinkedList<>();

        stack.add(startNode); // Push

        while (stack.size() != 0) {
            T node = stack.get(stack.size() - 1); // Peek
            stack.removeIndex(stack.size() - 1);; // Pop

            if (visited.indexOf(node) == -1) {
                visited.add(node);
                LinkedList<T> neighbours = impl.getNeighbors(node);

                for (int i=0; i<neighbours.size(); i++) {
                    T neighbour = neighbours.get(i);
                    if (visited.indexOf(neighbour) == -1) {
                        stack.add(neighbour); // Push
                    }
                }
            }
        }

        return visited;
    }

    public LinkedList<T> unweightedShortestPath(T startNode, T endNode) {
        return bfs(startNode);

    }
}
