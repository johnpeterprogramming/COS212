public class Main {
    public static void main(String[] args) throws Exception {
        // task1();
        // task2();
        task3();

    }

    public static void task1() {
        // Adjacency List TESTS
        AdjacencyList<Integer> al = new AdjacencyList<>();
        al.addNode(1);
        al.addNode(2);
        al.addNode(3);

        al.addEdge(1, 2);
        al.addEdge(1, 3);
        al.addEdge(2, 3);
        al.addEdge(3, 1);

        System.out.println(al.display());
        System.out.println("Num Edges: " + al.numberOfEdges());
        System.out.println("Num Nodes: " + al.numberOfNodes());

        System.out.println("Contains 2: " + al.containsNode(2));
        System.out.println("Contains 4: " + al.containsNode(4));
        
        System.out.println("Index of 2: " + al.indexOfNode(2));
        System.out.println("Index of 4: " + al.indexOfNode(4));
        
        System.out.println("1 connected to 2: " + al.isConnected(1, 2));
        System.out.println("1 connected to 4: " + al.isConnected(1, 4));

        LinkedList<Integer> neighbours = al.getNeighbors(1);
        System.out.println("Neighbours size: " + neighbours.size());


        al.removeNode(1);

        System.out.println(al.display());

        al.removeEdge(2, 3);

        System.out.println(al.display());
    }

    public static void task2() {
        // Adjacency Matrix TESTS
        AdjacencyMatrix<Integer> am = new AdjacencyMatrix<>();
        am.addNode(1);
        am.addNode(2);
        am.addNode(3);
        am.addNode(4);

        System.out.println(am.display());

        am.addEdge(1, 3);
        am.addEdge(2, 4);

        System.out.println(am.display());

        am.removeNode(2);

        System.out.println(am.display());

        System.out.println(am.isConnected(1, 3));
        System.out.println(am.isConnected(2, 4));

        am.nodeAtIndex(0);
        am.indexOfNode(1);

        System.out.println(am.containsNode(2));
        System.out.println(am.containsNode(4));
        System.out.println(am.numberOfNodes());
    }

    public static void task3() {
        // Graph TESTS
        UndirectedGraph<Integer> ugraph1 = new UndirectedGraph<>(new AdjacencyList<>());
        UndirectedGraph<Integer> ugraph2 = new UndirectedGraph<>(new AdjacencyMatrix<>());

        ugraph1.addNode(1);
        ugraph1.addNode(2);
        ugraph1.addNode(3);
        ugraph2.addNode(1);
        ugraph2.addNode(2);
        ugraph2.addNode(3);

        ugraph1.addEdge(1, 2);
        ugraph1.addEdge(2, 3);
        ugraph1.addEdge(1, 3);
        ugraph2.addEdge(1, 2);
        ugraph2.addEdge(2, 3);
        ugraph2.addEdge(1, 3);

        System.out.println("Adjacency List Undirected:");
        ugraph1.display();
        System.out.println("Adjacency Matrix Undirected:");
        ugraph2.display();

        DirectedGraph<Integer> dgraph1 = new DirectedGraph<>(new AdjacencyList<>());
        DirectedGraph<Integer> dgraph2 = new DirectedGraph<>(new AdjacencyMatrix<>());

        dgraph1.addNode(1);
        dgraph1.addNode(2);
        dgraph1.addNode(3);
        dgraph2.addNode(1);
        dgraph2.addNode(2);
        dgraph2.addNode(3);

        dgraph1.addEdge(1, 2);
        dgraph1.addEdge(2, 3);
        dgraph1.addEdge(1, 3);
        dgraph2.addEdge(1, 2);
        dgraph2.addEdge(2, 3);
        dgraph2.addEdge(1, 3);

        System.out.println("Adjacency List Directed:");
        dgraph1.display();
        System.out.println("Adjacency Matrix Directed:");
        dgraph2.display();


        dgraph1.bfs(1);
        dgraph2.bfs(1);
        dgraph1.dfs(1);
        dgraph2.dfs(1);
    }
}
