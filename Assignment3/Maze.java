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
                        toVisit.enqueue(neighbourVertex);
                    }
                }
            }

        } 
        
        return new Vertex[0];
    }

    double shortestPathDistanceNoDoor(Vertex start, Vertex goal) {
        Vertex[] path = this.shortestPathPathNoDoor(start, goal);

        if (path.length <= 0)
            return Double.POSITIVE_INFINITY;
        // System.out.println("path is not empty");
        return path[path.length - 1].dist;
    }

    Vertex[] shortestPathPathNoDoor(Vertex start, Vertex goal) {
        if (start == null || goal == null || start.symbol == 'D')
            return new Vertex[0];
        // Implementing Dijkstra's algorithm
        // Reconstruct path
        // No doors!
        LinkedList<Vertex> unvisited = new LinkedList<>();
        LinkedList<Vertex> toVisit = new LinkedList<>();
        
        for (Vertex v: this.vertexes) {
            getVertex(v).dist = Double.POSITIVE_INFINITY;
            getVertex(v).prevVisited = null;
            unvisited.enqueue(v);
        }
        
        getVertex(start).dist = 0;
        toVisit.enqueue(start);
        while (unvisited.size > 0 && toVisit.size > 0) {
            Vertex current = toVisit.dequeue();
            // System.out.println("Visiting: " + current.toString());
            unvisited.remove(current);

            Edge[] sortedEdges = current.getEdgesSortedByWeight();

            for (Edge e: sortedEdges) {

                Vertex neighbour = e.getOtherVertex(current);

                double newDist = current.dist + e.weight;

                if (newDist < neighbour.dist) {
                    // System.out.println("Set vertex: " + neighbour.toString() + " to " + newDist);
                    neighbour.dist = newDist;
                    neighbour.prevVisited = current;

                    if (toVisit.indexOf(neighbour) == -1 && unvisited.indexOf(neighbour) != -1 && neighbour.symbol != 'D')
                        toVisit.enqueue(neighbour);
                }

            }
        }

        if (unvisited.indexOf(goal) != -1) {
            // If goal hasn't been visited, the shortest path to goal wasn't found(because door or vertex doesn't exist)
            return new Vertex[0]; 
        } else {
            // System.out.println("Found goal: " + goal.toString());
            LinkedList<Vertex> shortestPathReversed = new LinkedList<>();
            LinkedList<Vertex> shortestPath = new LinkedList<>();
            Vertex current = getVertex(goal);
            while (current != start) {
                // System.out.println("Adding: " + current.toString());
                shortestPathReversed.enqueue(current);
                current = current.prevVisited;
            }
            shortestPathReversed.enqueue(start);
            while (shortestPathReversed.size > 0) {
                Vertex v = shortestPathReversed.pop();
                // System.out.print(v + " ");
                shortestPath.enqueue(v);
            }
            // System.out.println();

            return shortestPath.toVertexArray(); 
        }

        
    }

    double shortestPathDistanceDoor(Vertex start, Vertex goal, boolean goUp) {
        Vertex[] path = this.shortestPathPathDoor(start, goal, goUp);

        if (path.length <= 0)
            return Double.POSITIVE_INFINITY;
        // System.out.println("path is not empty");
        return path[path.length - 1].dist;
    }

    Vertex[] shortestPathPathDoor(Vertex start, Vertex goal, boolean goUp) {
        if (start == null || goal == null)
            return new Vertex[0];
        // Implementing Dijkstra's algorithm
        // Reconstruct path
        // No doors!
        LinkedList<Vertex> unvisited = new LinkedList<>();
        LinkedList<Vertex> toVisit = new LinkedList<>();
        
        for (Vertex v: this.vertexes) {
            getVertex(v).dist = Double.POSITIVE_INFINITY;
            getVertex(v).prevVisited = null;
            unvisited.enqueue(v);
        }
        
        getVertex(start).dist = 0;
        toVisit.enqueue(start);
        while (unvisited.size > 0 && toVisit.size > 0) {
            Vertex current = toVisit.dequeue();
            // System.out.println("Visiting: " + current.toString());
            unvisited.remove(current);

            Edge[] sortedEdges = current.getEdgesSortedByWeight();

            for (Edge e: sortedEdges) {

                Vertex neighbour = e.getOtherVertex(current);

                double newDist = current.dist + e.weight;

                if (newDist < neighbour.dist) {
                    // System.out.println("Set vertex: " + neighbour.toString() + " to " + newDist);
                    neighbour.dist = newDist;
                    neighbour.prevVisited = current;

                    if (toVisit.indexOf(neighbour) == -1 && unvisited.indexOf(neighbour) != -1)
                        toVisit.enqueue(neighbour);
                }

            }
        }

        if (unvisited.indexOf(goal) != -1) {
            // If goal hasn't been visited, the shortest path to goal wasn't found(because door or vertex doesn't exist)
            return new Vertex[0]; 
        } else {
            // System.out.println("Found goal: " + goal.toString());
            LinkedList<Vertex> shortestPathReversed = new LinkedList<>();
            LinkedList<Vertex> shortestPath = new LinkedList<>();
            Vertex current = getVertex(goal);
            while (current != start) {
                // System.out.println("Adding: " + current.toString());
                shortestPathReversed.enqueue(current);
                current = current.prevVisited;
            }
            shortestPathReversed.enqueue(start);
            while (shortestPathReversed.size > 0) {
                Vertex v = shortestPathReversed.pop();
                // System.out.print(v + " ");
                shortestPath.enqueue(v);
            }
            // System.out.println();

            return shortestPath.toVertexArray(); 
        }
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
    
                Vertex[] pathToKey = isReachAblePath(start, key);
                Vertex[] pathToDoor = isReachAblePath(key, door);
                Vertex[] pathToGoal = isReachAblePath(door, goal);
    
                if (pathToKey != null && pathToDoor != null && pathToGoal != null) {
                    return true;
                }

            }
        }

        return false;
    }

    Vertex[] isReachAbleThroughDoorPath(Vertex start, Vertex goal) {
        try {
            Vertex[] keys = this.getAllKeys();
            Vertex[] doors = this.getAllDoors();
    
            for (int i=0; i<keys.length; i++) {
                Vertex key = keys[i];
                for (int j=0; j<doors.length; j++) {
                    Vertex door = doors[i];
        
                    Vertex[] pathToKey = this.isReachAblePath(start, key);
                    Vertex[] pathToDoor = this.isReachAblePath(key, door);
                    Vertex[] pathToGoal = this.isReachAblePath(door, goal);
        
                    if (pathToKey != null && pathToDoor != null && pathToGoal != null) {
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

        } catch (Exception e) {
            return new Vertex[0];
        } 

        return new Vertex[0];
    }

    double shortestPathThroughDoor(Vertex start, Vertex goal) {
        Vertex[] path = this.shortestPathThroughDoorPath(start, goal);

        if (path.length <= 0)
            return Double.POSITIVE_INFINITY;
        // System.out.println("path is not empty");
        return path[path.length - 1].dist; 
    }

    Vertex[] shortestPathThroughDoorPath(Vertex start, Vertex goal) {
       Vertex[] keys = this.getAllKeys();
        Vertex[] doors = this.getAllDoors();

        for (int i=0; i<keys.length; i++) {
            Vertex key = keys[i];
            for (int j=0; j<doors.length; j++) {
                Vertex door = doors[i];
    
                Vertex[] pathToKey = this.shortestPathPathNoDoor(start, key);
                if (pathToKey.length <= 0) {
                    // System.out.println("path to key is empty");
                    continue;
                }
                double pathToKeyDist = pathToKey[pathToKey.length - 1].dist;

                Vertex[] pathToDoor = this.shortestPathPathDoor(key, door, true);
                if (pathToDoor.length <= 0) {
                    
                    // System.out.println("path to door is empty");
                    continue;
                }

                double pathToDoorDist = pathToDoor[pathToDoor.length - 1].dist;

                Vertex[] pathToGoal = this.shortestPathPathDoor(door, goal, true);
                if (pathToGoal.length <= 0) {

                    // System.out.println("path to goal is empty");
                    continue;
                }
                double pathToGoalDist = pathToGoal[pathToGoal.length - 1].dist;
                
                
                if (pathToKey != null && pathToDoor != null && pathToGoal != null) {
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

                    getVertex(goal).dist = pathToKeyDist + pathToDoorDist + pathToGoalDist;

                    return path;
                }

            }
        }

        return new Vertex[0];
    }

    boolean canReachGoal(char targetGoal){
        for (Vertex v: this.vertexes) {
            if (v.symbol == targetGoal) {
                return isReachAble(this.start, v);
            }
        }
        return false;
    }

    Vertex[] canReachGoalPath(char targetGoal){
        for (Vertex v: this.vertexes) {
            if (v.symbol == targetGoal) {
                return isReachAblePath(this.start, v);
            }
        }
        return new Vertex[0];
    }

    double getRatio(Vertex goal){
        if (isReachAble(this.start, goal))
            return ((int) (goal.symbol - '0') * 100)  / shortestPathDistanceNoDoor(this.start, goal);
        return ((int) (goal.symbol - '0') * 100) / shortestPathDistanceDoor(goal, goal, true);
    }

    Vertex getRecommendedGoal(){
       return new Vertex(0, 0, '1');
    }

    double getRecommendedRatio(){
       return 0; 
    }

    Vertex[] getRecommendedPath(){
        return new Vertex[0];
    }


}
