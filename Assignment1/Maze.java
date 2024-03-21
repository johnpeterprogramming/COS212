import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    private String[] map;

    public Maze(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            // First line is always int indicating row count
            int rows = scanner.nextInt();
            scanner.nextLine();

            map = new String[rows];

            MazeContructorHelper(scanner, 0);

        } catch (FileNotFoundException e) {
            map = new String[0];
            // System.out.println(e.getStackTrace());
        }
    }
    private void MazeContructorHelper(Scanner scanner, int index) {
        if (index < map.length) {
            map[index] = scanner.nextLine();
            MazeContructorHelper(scanner, index + 1);
        }
    }

    public Maze(Maze other) {
        map = other.map.clone();
    }

    @Override
    public String toString() {
        if (map.length == 0)
            return "Empty Map";
        return toStringHelper(0);
    }
    private String toStringHelper(int index) {
        if (index < map.length - 1)
            return map[index] + "\n" + toStringHelper(index + 1);
        else 
            return map[index];
    }

    public boolean validSolution(int startX, int startY, int goalX, int goalY, LinkedList path) {
        if (path == null) return false;
        if (path.head == null) return false;
        // Start and End coordinates are valid after these checks
        if (path.head.x != startX || path.head.y != startY)
            return false;
        LinkedList reversedLinkedList = path.reversed();
        if (reversedLinkedList.head.x != goalX || reversedLinkedList.head.y != goalY)
            return false;

        // Now must just checks walls and that there isn't duplicate coordinates
        return validSolutionHelper(path.head, new LinkedList());
    }
    private boolean validSolutionHelper(CoordinateNode node, LinkedList duplicateChecker) {
        // This means that it has reached the end without any issues
        if (node == null) return true;
        char value;
        try {
            value = map[node.y].charAt(node.x);
        } catch (StringIndexOutOfBoundsException e) {
            // The path has gone out of bounds
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            // The path has gone out of bounds
            return false;
        }
        if (value == 'X') {
            // Can't have a wall in a path
            return false;
        } else if (duplicateChecker.contains(node.x, node.y)) {
            // The path has a duplicate coordinate
            return false;
        } else if (node.next != null) {
            if (Math.abs(node.x - node.next.x) > 0 && Math.abs(node.y - node.next.y) > 0) {
                // No diagonals allowed
                return false;
            } else if (Math.abs(node.x - node.next.x) > 1 || Math.abs(node.y - node.next.y) > 1) {
                // Cannot move more than 1 square
                return false;
            }
        } 
        duplicateChecker.append(node.x, node.y);

        return validSolutionHelper(node.next, duplicateChecker);
    }

        public String solve(int x, int y, int goalX, int goalY) {
            LinkedList path = new LinkedList(x, y);
            if (solveHelper(x, y, goalX, goalY, path)) {
                
                String[] solutionMap = new String[map.length];
                initArrayFromMap(solutionMap, 0);
                // System.out.println("TEST map to string:\n" + arrayToString(solutionMap, 0));
                linkedListToMazeString(solutionMap, path.head);

                solutionMap[goalY] = solutionMap[goalY].substring(0, goalX) + "E" + solutionMap[goalY].substring(goalX + 1);
                solutionMap[y] = solutionMap[y].substring(0, x) + "S" + solutionMap[y].substring(x + 1);

                return "Solution\n" +
                // Make maze string from linked list
                    arrayToString(solutionMap, 0) + "\n" +
                    path.toString();  
            }
            return "No valid solution exists";
        }
        private boolean solveHelper(int x, int y, int goalX, int goalY, LinkedList path) {
            if (validSolution(path.head.x, path.head.y, goalX, goalY, path)) {
                return true;
            }

            if (validSolutionHelper(new CoordinateNode(x - 1, y), new LinkedList()) && !path.contains(x-1, y)) {
                // System.out.println("Trying LEFT");
                path.append(x - 1, y);
                if (solveHelper(x - 1, y, goalX, goalY, path)) return true;
                path.pop_back();
                // System.out.println("Tried and failed LEFT");
            } 
            if (validSolutionHelper(new CoordinateNode(x, y + 1), new LinkedList()) && !path.contains(x, y+1)) {
                // System.out.println("Trying DOWN");
                path.append(x, y + 1);
                if (solveHelper(x, y + 1, goalX, goalY, path)) return true;
                path.pop_back();
                // System.out.println("Tried and failed DOWN");
            } 
            if (validSolutionHelper(new CoordinateNode(x, y - 1), new LinkedList()) && !path.contains(x, y-1)) {
                // System.out.println("Trying UP");
                path.append(x, y - 1);
                if (solveHelper(x, y - 1, goalX, goalY, path)) return true;
                path.pop_back();
                // System.out.println("Tried and failed UP");
            } 
            if (validSolutionHelper(new CoordinateNode(x + 1, y), new LinkedList()) && !path.contains(x+1, y)) {
                // System.out.println("Trying RIGHT");
                path.append(x + 1, y);
                if (solveHelper(x+1, y, goalX, goalY, path)) return true;
                path.pop_back();
                // System.out.println("Tried and failed RIGHT");
            } 

            return false;

        }
    private String linkedListToMazeString(String[] m, CoordinateNode node) {
        if (node == null) return arrayToString(m, 0);
        else {
            // System.out.println("Node: " + node.x + " " + node.y + " -- " + m[node.y]);
            m[node.y] = m[node.y].substring(0, node.x) + "@" + m[node.y].substring(node.x + 1);
            return linkedListToMazeString(m, node.next);
        }
    }
    private String arrayToString(String[] m, int index) {
        if (index < m.length - 1) {
            return m[index] + "\n" + arrayToString(m, index + 1);
        } else return m[index];
    }
    private void initArrayFromMap(String[] m, int index) {
        if (index < map.length) {
            m[index] = new String(map[index]);
            initArrayFromMap(m, index + 1);
        } 
    }


    public LinkedList validStarts(int x, int y) {
        LinkedList validPaths = new LinkedList();

        validStartsHelper(x, y, 0, 0, validPaths);

        return validPaths;
    }
    private void validStartsHelper(int goalX, int goalY, int i, int j, LinkedList path) {
        if (i < map.length && j < map[i].length()) {
            if (solveHelper(j, i, goalX, goalY, new LinkedList(j, i))) {
                path.append(j, i);
            }
            if (j < map[i].length() - 1) {
                validStartsHelper(goalX, goalY, i, j + 1, path);
            } else if (i < map.length -1) {
                // System.out.println("Y axis is going DOWN");
                validStartsHelper(goalX, goalY, i + 1, 0, path);
            } 
        }
    }
}