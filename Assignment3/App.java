import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        // vertexTest();
        // mazeTest();
        // studentExample();
        // reachableTest();
        shortestPathTest();
    }

    public static void shortestPathTest() {
        Maze maze1 = new Maze("studentMaze.txt");

        maze1.stage1Reducing();
        maze1.stage2Reducing();
        maze1.stage3Reducing();

        Vertex[] path = maze1.shortestPathPathNoDoor(maze1.start, new Vertex(2, 5, 'T'));
        for (Vertex v: path) {
            System.out.print(v + ":" + v.dist + " ");
        }
        System.out.println();

        Vertex[] path2 = maze1.shortestPathPathNoDoor(maze1.start, new Vertex(4, 1, 'T'));
        for (Vertex v: path2) {
            System.out.print(v + " ");
        }
        System.out.println();

        System.out.println(maze1.shortestPathDistanceNoDoor(maze1.start, new Vertex(2, 5, 'T')));

        System.out.println(maze1.shortestPathDistanceDoor(maze1.getVertex(new Vertex(8, 5, 'D')), maze1.getVertex(new Vertex(2, 5, 'T')), false));

        Vertex[] path3 = maze1.shortestPathThroughDoorPath(maze1.getVertex(new Vertex(8, 9, 'S')), maze1.getVertex(new Vertex(4, 1, 'T')));
        for (Vertex v: path3) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println(maze1.shortestPathThroughDoor(maze1.getVertex(new Vertex(8, 9, 'S')), maze1.getVertex(new Vertex(4, 1, 'T'))));
    }

    public static void vertexTest() {
        Vertex v1 = new Vertex(1, 1, 'A');
        Vertex v2 = new Vertex(2, 1, 'B');
        Vertex v3 = new Vertex(1, 2, 'C');
        System.out.println(v1.equals(v2));
        System.out.println(v1.equals(v3));
        Edge e1 = new Edge(v1, v2, 1);
        Edge e2 = new Edge(v2, v3, 1);

        v1.edges.enqueue(e1);
        v2.edges.enqueue(e2);

        v1.edges.printList();
        v2.edges.printList();
    }

    public static void reachableTest() {
        Maze maze1 = new Maze("studentMaze.txt");

        maze1.stage1Reducing();
        maze1.stage2Reducing();
        maze1.stage3Reducing();

        // test if path from doors to T exists
        // System.out.println("Is T reachable from D (8, 5): " + maze1.isReachAble(maze1.getVertex(new Vertex(8,5,'D')), maze1.getVertex(new Vertex(4, 1, 'T'))));
        // System.out.println("Is T reachable from D (6, 4): " + maze1.isReachAble(maze1.getVertex(new Vertex(6,4,'D')), maze1.getVertex(new Vertex(4, 1, 'T'))));
        // end test

        Vertex[] path = maze1.isReachAblePath(maze1.getVertex(new Vertex(8,5,'D')), maze1.getVertex(new Vertex(4, 1, 'T')));
        for (Vertex v: path) {
            System.out.println(v);
        }

    }

    public static void mazeTest() {
        Maze maze1 = new Maze("studentMaze.txt");
        System.out.println(maze1.latexCode());

        Maze maze2 = Maze.createMaze(
            "##########\n" +
            "####T    #\n" +
            "#    # 0 #\n" +
            "######   #\n" +
            "######D# #\n" +
            "# T #  #D#\n" +
            "##1  K   #\n" +
            "###### # #\n" +
            "###### # #\n" + 
            "########S#\n"
        );
        System.out.println(maze2.latexCode());

        maze1.stage1Reducing();
        maze1.stage2Reducing();
        maze1.stage3Reducing();
        System.out.println(maze1.latexCode());

        Edge[] edges = maze1.getEdges();
        for (int i=0; i<edges.length; i++) {
            System.out.println(edges[i]);
        }
        Vertex[] vertexes = maze1.getVertices();
        for (int i=0; i<vertexes.length; i++) {
            System.out.println(vertexes[i]);
        }

        System.out.println("Is T reachable from S: " + maze1.isReachAble(maze1.start, maze1.getVertex(new Vertex(4, 1, 'T'))));
        System.out.println("Is 1 reachable from S: " + maze1.isReachAble(maze1.start, maze1.getVertex(new Vertex(2, 6, '1'))));

        Vertex[] path = maze1.isReachAblePath(maze1.start, new Vertex(2, 6, '1'));
        System.out.print("Is 1 reachable from S path: ");
        for (int i=0; i<path.length; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.println();

        System.out.println("Is T reachable from S through a door: " + maze1.isReachAbleThroughDoor(maze1.start, new Vertex(4, 1, 'T')));

        Vertex[] path2 = maze1.isReachAbleThroughDoorPath(maze1.start, new Vertex(4, 1, 'T'));
        System.out.print("Is T reachable from S through a door: ");
        for (int i=0; i<path2.length; i++) {
            System.out.print(path2[i] + " ");
        }
        System.out.println();
    }

    public static void toFile(MazeGenerator mg, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName+".txt");
            myWriter.write(mg.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(fileName+".md");
            myWriter.write(mg.toMarkDown());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void studentExample() {
        Maze m = new Maze("studentMaze.txt");
        m.stage1Reducing();
        m.stage2Reducing();
        m.stage3Reducing();

        System.out.println(m.isReachAble(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.isReachAble(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        Vertex[] path = m.isReachAblePath(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1')));
        for(Vertex v: path){
            System.out.println(v);
        }
        System.out.println(m.isReachAbleThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        path = m.isReachAbleThroughDoorPath(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T')));
        for(Vertex v: path){
            System.out.println(v);
        }
        // System.out.println(m.shortestPathDistanceNoDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        // System.out.println(m.shortestPathDistanceNoDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        // System.out.println(m.shortestPathThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        // System.out.println(m.shortestPathThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        // System.out.println(m.getRatio(m.getVertex(new Vertex(2,6, '1'))));
    }
}
