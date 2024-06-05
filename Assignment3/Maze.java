import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    LinkedList<Vertex> vertexes;
    LinkedList<Edge> edges;
    Vertex start;

    Maze() {
        this.start = null;
        this.vertexes = new LinkedList<Vertex>();
        this.edges = new LinkedList<Edge>();
    }

    Maze(String fileName) {
        this.start = null;
        this.vertexes = new LinkedList<Vertex>();
        this.edges = new LinkedList<Edge>();

        try {

            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            int size = 0;

            while (myReader.hasNextLine()) {
                size++;
                myReader.nextLine();
            }
            myReader.close();

            // Assumes that the maze is always square
            // System.out.println("Size of maze is: " + size);
            char[][] maze = new char[size][size];

            // Reopen file to read into array
            myReader = new Scanner(myObj);

            // Row num
            int i = 0;
            while (myReader.hasNextLine()) {
                char[] charLineArray = myReader.nextLine().toCharArray();
                maze[i] = charLineArray;
                i++;
            }
            myReader.close();

            // Now data from text file is loaded into 2D array of chars

            // ----- FOR PRINTING OUT CHAR ARRAY OF MAZE -----
            // for (i=0; i < size; i++) {
            //     for (int j=0; j< size ; j++) {
            //         System.out.print(maze[i][j]);
            //     }
            //     System.out.println();
            // }

            // ----- FOR CREATING VERTICES -----
            for (i = 0; i< size; i++) {
                for (int j = 0; j < size; j++) {
                    if (maze[i][j] != '#') {
                        Vertex newVertex = new Vertex(j, i, maze[i][j]);
                        // Sets the start vertex if it's found
                        if (maze[i][j] == 'S') {
                            this.start = newVertex;
                        }

                        this.vertexes.insert(newVertex);
                    }
                }
            }

            // ----- FOR CREATING EDGES -----
            for (i = 0; i< size; i++) {
                for (int j = 0; j < size; j++) {
                    if (maze[i][j] != '#') {

                        // Current Vertex 
                        Vertex v1 = getVertexOfIndex(j, i); 
                        // Vertex to the right
                        Vertex v2 = getVertexOfIndex(j + 1, i); 
                        // Vertex below
                        Vertex v3 = getVertexOfIndex(j, i + 1); 

                        if (v1 != null) {
                            if (v2 != null) {
                                // Insert edge between v1 and v2
                                Edge newEdge = new Edge(v1, v2, 1);  // Weight is 1 by default
                                this.edges.insert(newEdge);
                                v1.edges.insert(newEdge);
                                v2.edges.insert(newEdge);
                            }
                            if (v3 != null) {
                                // Insert edge between v1 and v2
                                Edge newEdge = new Edge(v1, v3, 1);  // Weight is 1 by default
                                this.edges.insert(newEdge);
                                v1.edges.insert(newEdge);
                                v3.edges.insert(newEdge);
                            }

                        }

                    }
                }
            }



        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private Vertex getVertexOfIndex(int x, int y) {
        for (Vertex v: this.vertexes) {
            if (v.xPos == x && v.yPos == y) {
                return v;
            }
        }
        return null;
    }

    static Maze createMaze(String mazeString) {
       return null; 
    }

    String latexCode(){
        String result = "\\documentclass[hidelinks, 12pt, a4paper]{article}\r\n" + //
        "\\usepackage{tikz}\n" + //
        "\n" + //
        "\\begin{document}\n" + //
        "\n" + //
        "\\begin{tikzpicture}[node/.style={circle, draw, minimum size=1.2em},yscale=-1]\n";
        for(Vertex v: getVertices()){
            result += v.latexCode() + "\n";
        }
        result += "\n";
        for(Edge e: getEdges()){
            result += e.latexCode() + "\n";
        }
        result += "\\end{tikzpicture}\r\n" + //
                        "\r\n" + //
                        "\\end{document}";
        return result;
    }

    Vertex[] getVertices() {
        Vertex[] vertexArray = new Vertex[vertexes.size];
        for (Vertex v : this.vertexes) {
            vertexArray[v.counter] = v;
        }
        return vertexArray;
    }

    Edge[] getEdges() {
       Edge[] edgeArray = new Edge[edges.size];
        for (Edge e : this.edges) {
            edgeArray[e.counter] = e;
        }
        return edgeArray;
    }

    void stage1Reducing() {// Removing of redundant steps
        
    }

    void stage2Reducing() {// Removing dead ends
        
    }

    void stage3Reducing() {// Doubling the weight to traps
        
    }

    public Vertex[] getAllDoors() {
       return null; 
    }

    public Vertex[] getAllGoals() {
       return null; 
    }

    public Vertex[] getAllKeys() {
       return null; 
    }

    boolean isReachAble(Vertex start, Vertex goal) {
       return false; 
    }

    Vertex[] isReachAblePath(Vertex start, Vertex goal) {
        return null;
    }

    double shortestPathDistanceNoDoor(Vertex start, Vertex goal) {
        return 0; 
    }

    Vertex[] shortestPathPathNoDoor(Vertex start, Vertex goal) {
       return null; 
    }

    double shortestPathDistanceDoor(Vertex start, Vertex goal, boolean goUp) {
       return 0; 
    }

    Vertex[] shortestPathPathDoor(Vertex start, Vertex goal, boolean goUp) {
      return null; 
    }

    Vertex getVertex(Vertex v) {
       return null; 
    }

    boolean isReachAbleThroughDoor(Vertex start, Vertex goal) {
       return false; 
    }

    Vertex[] isReachAbleThroughDoorPath(Vertex start, Vertex goal) {
       return null; 
    }

    double shortestPathThroughDoor(Vertex start, Vertex goal) {
       return 0; 
    }

    Vertex[] shortestPathThroughDoorPath(Vertex start, Vertex goal) {
       return null; 
    }

    boolean canReachGoal(char targetGoal){
       return false; 
    }

    Vertex[] canReachGoalPath(char targetGoal){
       return null; 
    }

    double getRatio(Vertex goal){
       return 0; 
    }

    Vertex getRecommendedGoal(){
       return null; 
    }

    double getRecommendedRatio(){
       return 0; 
    }

    Vertex[] getRecommendedPath(){
        return null;
    }


}
