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
        return validSolutionHelper(startX, startY, goalX, goalY, path);
    }
    private boolean validSolutionHelper(int startX, int startY, int goalX, int goalY, LinkedList path) {
        if (startX == goalX && startY == goalY) {
            return true;
        } else if (map[startY].charAt(startX) == 'X') {
            return false;
        } else if (path.contains(startX, startY)) {
            return false;
        } else {
            LinkedList newPath = new LinkedList(path);
            newPath.append(startX, startY);
            return validSolutionHelper(startX + 1, startY, goalX, goalY, newPath) ||
                   validSolutionHelper(startX - 1, startY, goalX, goalY, newPath) ||
                   validSolutionHelper(startX, startY + 1, goalX, goalY, newPath) ||
                   validSolutionHelper(startX, startY - 1, goalX, goalY, newPath);
        }
    }

    public String solve(int x, int y, int goalX, int goalY) {
        return "";
    }

    public LinkedList validStarts(int x, int y) {
        return null;
    }

}