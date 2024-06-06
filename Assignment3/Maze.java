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

                    this.vertexes.enqueue(newVertex);
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
                            // enqueue edge between v1 and v2
                            Edge newEdge = new Edge(v1, v2, 1);  // Weight is 1 by default
                            this.edges.enqueue(newEdge);
                            v1.edges.enqueue(newEdge);
                            v2.edges.enqueue(newEdge);
                        }
                        if (v3 != null) {
                            // enqueue edge between v1 and v2
                            Edge newEdge = new Edge(v1, v3, 1);  // Weight is 1 by default
                            this.edges.enqueue(newEdge);
                            v1.edges.enqueue(newEdge);
                            v3.edges.enqueue(newEdge);
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
        return this.vertexes.toVertexArray();
    }

    Edge[] getEdges() {
        return this.edges.toEdgeArray();
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
                v1.edges.enqueue(newEdge);
                v2.edges.enqueue(newEdge);
                
                this.edges.enqueue(newEdge);

                // Remove the vertex
                vertexesToRemove.enqueue(v);

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

    void stage2Reducing() {
        LinkedList<Vertex> vertexesToRemove = new LinkedList<>();
        for (Vertex v: this.vertexes) {
            if (v.edges.size == 1 && v.symbol == ' ') {
                Edge edge = v.edges.head.data;
                Vertex v1 = edge.getOtherVertex(v);

                v1.edges.remove(edge);
                this.edges.remove(edge);

                vertexesToRemove.enqueue(v);
            }
        }
        for (Vertex vertex: vertexesToRemove) {
            this.vertexes.remove(vertex);
        }
    }

    // MAKE SURE ALL ADJANCENT VERTICES TO A DOOR HAVE THEIR WEIGHT DOUBLED ASWELL?
    void stage3Reducing() {
        for (Edge e: this.edges) {
            if (e.v1.symbol == 'T' || e.v2.symbol == 'T') {
                e.weight *= 2;
            }
        }
        
    }

    public Vertex[] getAllDoors() {
        LinkedList<Vertex> doors = new LinkedList<>();
        for (Vertex v: this.vertexes) {
            if (v.symbol == 'D') {
                doors.enqueue(v);
            }
        }
        return doors.getVertexesSorted();
    }

    public Vertex[] getAllGoals() {
       return null; 
    }

    public Vertex[] getAllKeys() {
        LinkedList<Vertex> keys = new LinkedList<>();
        for (Vertex v: this.vertexes) {
            if (v.symbol == 'K') {
                keys.enqueue(v);
            }
        }
        return keys.getVertexesSorted();
    }

    boolean isReachAble(Vertex start, Vertex goal) {
        LinkedList<Vertex> visited = new LinkedList<>();
        LinkedList<Vertex> toVisit = new LinkedList<>();
        toVisit.enqueue(start);

        do {
            Vertex current = toVisit.pop();
            // System.out.println("Visiting: " + current.toString());
            visited.enqueue(current);
            if (current.equals(goal))
                return true;
            
            if (current.symbol != 'D' || (current.symbol == 'D' && start.symbol =='D')) {
                Edge[] sortedEdges = current.getEdgesSorted();
                for (int i=0; i<sortedEdges.length; i++) {
                    Edge e = sortedEdges[i];

                    Vertex neighbourVertex = e.getOtherVertex(current);
                    // Can't traverse through a door or previously visited vertexes
                    if (visited.indexOf(neighbourVertex) == -1 && toVisit.indexOf(neighbourVertex) == -1) {
                        toVisit.enqueue(neighbourVertex);
                    }
                }
            }
        } while (visited.size < this.vertexes.size && toVisit.size > 0);
        
        return false;
    }

    Vertex[] isReachAblePath(Vertex start, Vertex goal) {
        LinkedList<Vertex> visited = new LinkedList<>();
        LinkedList<Vertex> toVisit = new LinkedList<>();
        toVisit.enqueue(start);

        while (visited.size < this.vertexes.size && toVisit.size > 0) {
            Vertex current = toVisit.pop();
            // System.out.println("Visiting: " + current.toString());
            visited.enqueue(current);
            if (current.equals(goal))
                return visited.toVertexArray();
            
            // Only add other nodes if it's not a door
            if (current.symbol != 'D' || (current.symbol == 'D' && start.symbol =='D')) {
                Edge[] sortedEdges = current.getEdgesSorted();
                // System.out.println("ADDING EDGES FROM " + current.toString());
                // Loop through reversed, because of how stack pops, I still want edges to be popped in ascending order
                for (int i=sortedEdges.length-1; i>=0; i--) {
                    Edge e = sortedEdges[i];
                    
                    Vertex neighbourVertex = e.getOtherVertex(current);
                    // Can't traverse through a door or previously visited vertexes
                    if (visited.indexOf(neighbourVertex) == -1 && toVisit.indexOf(neighbourVertex) == -1) {
                        // System.out.print(neighbourVertex.toString() + " ");
                        toVisit.enqueue(neighbourVertex);
                    }
                }
                // System.out.println();
                // System.out.println();
            }

        } 
        
        return new Vertex[0];
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
        for (Vertex curVertex: this.vertexes) {
            if (curVertex.equals(v))
                return curVertex;
        }
        return null;
    }

    boolean isReachAbleThroughDoor(Vertex start, Vertex goal) {
        Vertex[] keys = this.getAllKeys();
        Vertex[] doors = this.getAllDoors();

        for (int i=0; i<keys.length; i++) {
            Vertex key = keys[i];
            for (int j=0; j<doors.length; j++) {
                Vertex door = doors[j];
                // System.out.println("Comparing key: " + key + " and door: " + door);
    
                Vertex[] pathToKey = isReachAblePath(start, key);
                Vertex[] pathToDoor = isReachAblePath(key, door);
                Vertex[] pathToGoal = isReachAblePath(door, goal);

                // System.out.println("Path to key exists: " + (pathToKey != null));
                // System.out.println("Path to door exists: " + (pathToDoor != null));
                // System.out.println("Path to goal exists: " + (pathToGoal != null));
    
                if (pathToKey != null && pathToDoor != null && pathToGoal != null) {
                    return true;
                }

            }
        }

        return false;
    }

    Vertex[] isReachAbleThroughDoorPath(Vertex start, Vertex goal) {
        Vertex[] keys = this.getAllKeys();
        Vertex[] doors = this.getAllDoors();
        // System.out.println("DOORS:");
        // System.out.println(doors[0].toString());
        // System.out.println(doors[1].toString());
        // System.out.println(doors[0].toString().compareTo(doors[1].toString()) < 0);

        for (int i=0; i<keys.length; i++) {
            Vertex key = keys[i];
            for (int j=0; j<doors.length; j++) {
                Vertex door = doors[i];
                // System.out.println("Comparing key: " + key + " and door: " + door);
    
                Vertex[] pathToKey = this.isReachAblePath(start, key);
                Vertex[] pathToDoor = this.isReachAblePath(key, door);
                Vertex[] pathToGoal = this.isReachAblePath(door, goal);
    
                if (pathToKey != null && pathToDoor != null && pathToGoal != null) {
                    // System.out.print("Path to key:");
                    // for (Vertex v: pathToKey) {
                    //     System.out.print(v + " ");
                    // }
                    // System.out.println();
                    // System.out.print("Path to door:");
                    // for (Vertex v: pathToDoor) {
                    //     System.out.print(v + " ");
                    // }
                    // System.out.println();
                    // System.out.print("Path to Goal:");
                    // for (Vertex v: pathToGoal) {
                    //     System.out.print(v + " ");
                    // }
                    // System.out.println();

                    Vertex[] path = new Vertex[pathToKey.length + pathToDoor.length + pathToGoal.length - 2];

                    int count = 0;
                    for (int k=0; k<pathToKey.length - 1; k++) {
                        path[count++] = pathToKey[k];
                    }
                    for (int k=0; k<pathToDoor.length - 1; k++) {
                        path[count++] = pathToDoor[k];
                    }
                    for (int k=0; k<pathToGoal.length; k++) {
                        path[count++] = pathToGoal[k];
                    }

                    return path;
                }

            }
        }

        return new Vertex[0];
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
