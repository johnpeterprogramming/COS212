import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        studentExample();
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
