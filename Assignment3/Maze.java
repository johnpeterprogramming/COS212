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

        File myObj = new File(fileName);

        char[][] maze = getMazeArray(myObj);

        CreateVertexesAndConnectEdges(maze);
            
    }

    private void CreateVertexesAndConnectEdges(char[][] maze) {
        // ----- FOR CREATING VERTICES -----
        for (int i = 0; i< maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
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
        for (int i = 0; i< maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
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

    private Vertex getVertexOfIndex(int x, int y) {
        for (Vertex v: this.vertexes) {
            if (v.xPos == x && v.yPos == y) {
                return v;
            }
        }
        return null;
    }

    static Scanner getScanner(Object input) {
        Scanner scanner = null;
        if (input instanceof File) {
            try {
                scanner = new Scanner((File) input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (input instanceof String) {
            scanner = new Scanner((String) input);
        }
        return scanner;
    }

    static char[][] getMazeArray(Object input) {
        Scanner scanner = getScanner(input);

        int size = 0;

        while (scanner.hasNextLine()) {
            size++;
            scanner.nextLine();
        }
        scanner.close();

        // Assumes that the maze is always square
        // System.out.println("Size of maze is: " + size);
        char[][] maze = new char[size][size];

        // Reopen file to read into array
        scanner = getScanner(input);

        // Row num
        int i = 0;
        while (scanner.hasNextLine()) {
            char[] charLineArray = scanner.nextLine().toCharArray();
            maze[i] = charLineArray;
            i++;
        }
        scanner.close();

        return maze;
    }

    static Maze createMaze(String mazeString) {
        Maze mazeObject = new Maze();

        char[][] maze = Maze.getMazeArray(mazeString);

        mazeObject.CreateVertexesAndConnectEdges(maze);

        return mazeObject;

    }

    String latexCode(){
        String result = "\\documentclass[hidelinks, 12pt, a4paper]{article}\r\n" + //
        "\\usepackage{tikz}\n" + //
        "\n" + //
        "\\begin{document}\n" + //
        "\n" + //
        "\\begin{tikzpicture}[node/.style={circle, draw, minimum size=1.2em},yscale=-2,xscale=2]\n";
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
        int count = 0;
        for (Vertex v : this.vertexes) {
            vertexArray[count++] = v;
        }
        return vertexArray;
    }

    Edge[] getEdges() {
        Edge[] edgeArray = new Edge[edges.size];
        int count = 0;
        for (Edge e : this.edges) {
            edgeArray[count++] = e;
        }
        return edgeArray;
    }

    void stage1Reducing() {
        // Remove the vertex and the original edges and create a new edge linking the two vertices with a combined weight
        LinkedList<Vertex> vertexesToRemove = new LinkedList<Vertex>();
        // Loop through vertexes and add to-remove list
        for (Vertex v: this.vertexes) {
            if (v.edges.size == 2 && v.symbol == ' ') {
                Edge edge1 = v.edges.head.data;
                Edge edge2 = v.edges.head.next.data;
                double weight = edge1.weight + edge2.weight;

                Vertex v1 = edge1.getOtherVertex(v);
                Vertex v2 = edge2.getOtherVertex(v);

                // Add new edge
                Edge newEdge = new Edge(v1, v2, weight);
                v1.edges.remove(edge1);
                v2.edges.remove(edge2);
                v1.edges.insert(newEdge);
                v2.edges.insert(newEdge);
                
                this.edges.insert(newEdge);

                // Remove the vertex
                vertexesToRemove.insert(v);

                // Remove the edges
                this.edges.remove(edge1);
                this.edges.remove(edge2);
            }
        }
        // Remove the vertexes
        for (Vertex v: vertexesToRemove) {
            this.vertexes.remove(v);
        }
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
