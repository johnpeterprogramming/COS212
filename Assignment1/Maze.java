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
            solveHelper(x, y, goalX, goalY, path);
            if (path == null)
                return "No Solution Found";
            return path.toString();
        }
        private void solveHelper(int x, int y, int goalX, int goalY, LinkedList path) {
            if (validSolution(x, y, goalX, goalY, path)) {
                System.out.println("FOUND SOLUTION:" + path.toString());
                return;
            }

            if (validSolutionHelper(new CoordinateNode(x, y), new LinkedList())) {
                System.out.println("Trying LEFT");
                path.append(x - 1, y);
                solveHelper(x - 1, y, goalX, goalY, path);
                path.pop_back();
                System.out.println("Tried and failed LEFT");
            } 
            if (validSolution(x, y + 1, goalX, goalY, path)) {
                System.out.println("Trying UP");
                path.append(x, y + 1);
                solveHelper(x, y + 1, goalX, goalY, path);
                path.pop_back();
                System.out.println("Tried and failed UP");
            } 
            if (validSolution(x, y - 1, goalX, goalY, path)) {
                System.out.println("Trying Down");
                path.append(x, y - 1);
                solveHelper(x, y - 1, goalX, goalY, path);
                path.pop_back();
                System.out.println("Tried and failed DOWN");
            } 
            if (validSolution(x + 1, y, goalX, goalY, path)) {
                System.out.println("Trying RIGHT");
                path.append(x + 1, y);
                solveHelper(x+1, y, goalX, goalY, path);
                path.pop_back();
                System.out.println("Tried and failed RIGHT");
            } 

        }
        private boolean inBounds(int x, int y) {
            return (x >= 0 && x < map[y].length() && y >= 0 && y < map.length);
        }

    public LinkedList validStarts(int x, int y) {
        return null;
    }

}