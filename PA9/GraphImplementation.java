interface GraphImplementation<T> {
    void addNode(T data);

    void addEdge(T dataFrom, T dataTo);

    void removeNode(T data);

    void removeEdge(T dataFrom, T dataTo);

    int numberOfNodes();

    int numberOfEdges();

    boolean containsNode(T data);

    int indexOfNode(T data);

    T nodeAtIndex(int index);

    boolean isConnected(T dataFrom, T dataTo);

    LinkedList<T> getNeighbors(T data);

    String display();
}