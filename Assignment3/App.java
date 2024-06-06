import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        // vertexTest();
        simpleMazeTest();
        // studentExample();
    }

    public static void vertexTest() {
        Vertex v1 = new Vertex(1, 1, 'A');
        Vertex v2 = new Vertex(2, 1, 'B');
        Vertex v3 = new Vertex(1, 2, 'C');
        System.out.println(v1.equals(v2));
        System.out.println(v1.equals(v3));
        Edge e1 = new Edge(v1, v2, 1);
        Edge e2 = new Edge(v2, v3, 1);

        v1.edges.insert(e1);
        v2.edges.insert(e2);

        v1.edges.printList();
        v2.edges.printList();
    }

    public static void simpleMazeTest() {
        Maze maze1 = new Maze("studentMaze.txt");
        // System.out.println(maze1.latexCode());

        // Maze maze2 = Maze.createMaze(
        //     "##########\n" +
        //     "####T    #\n" +
        //     "#    # 0 #\n" +
        //     "######   #\n" +
        //     "######D# #\n" +
        //     "# T #  #D#\n" +
        //     "##1  K   #\n" +
        //     "###### # #\n" +
        //     "###### # #\n" + 
        //     "########S#\n"
        // );
        // System.out.println(maze2.latexCode());

        maze1.stage1Reducing();
        // maze1.stage2Reducing();
        System.out.println(maze1.latexCode());

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
        Maze m = new Maze("studentMaze.txt.txt");
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
        System.out.println(m.shortestPathDistanceNoDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.shortestPathDistanceNoDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        System.out.println(m.shortestPathThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.shortestPathThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        System.out.println(m.getRatio(m.getVertex(new Vertex(2,6, '1'))));
    }
}
