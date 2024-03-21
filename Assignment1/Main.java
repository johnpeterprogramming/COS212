public class Main {
    /*
     * 1 Testing
     * 2 Normal
    */
    public static int PRINT_MODE = 1;

    public static int SUITES_RUN = 0;
    public static int SUITES_PASSED = 0;
    public static int TESTS_RUN = 0;
    public static int TESTS_PASSED = 0;
    public static void main(String[] args) {

        task1();
        task2();
        task3();

        endAll();

    }

    public static void task1() {
        startSuite("LinkedList");
        LinkedList ll1 = new LinkedList(0, 0);
        LinkedList ll2 = new LinkedList();

        // Test 1
        assertEquals(ll2.toString(), "Empty List");

        // Test 2
        assertEquals(ll1.toString(), "[0,0]");

        ll1.append(1, 0);
        ll1.append(2, 0);
        ll1.append(0, 1);

        // Test 3
        assertEquals(ll1.toString(), "[0,0] -> [1,0] -> [2,0] -> [0,1]");

        // Test 4
        assertEquals(ll1.contains(0, 0), true);

        // Test 5  
        assertEquals(ll1.contains(2, 2), false);

        // Test 6
        assertEquals(ll1.length(), 4);
        
        // Test 7
        assertEquals(ll2.length(), 0);


        LinkedList ll3 = ll1.reversed();
        // Test 8
        assertEquals(ll3.toString(), "[0,1] -> [2,0] -> [1,0] -> [0,0]");

        ll1.appendList(ll3);
        System.out.println(ll1.toString());

        endSuite();
    }

    public static void task2() {
        startSuite("Maze basic");

        Maze maze1 = new Maze("input.txt");
        Maze maze2 = new Maze(maze1);
        Maze maze3 = new Maze("input2.txt");
        Maze maze4 = new Maze("input3.txt");


        System.out.println(maze4.toString());

        // Test 1
        assertEquals(maze1.toString(), 
                    "-----\n" + 
                    "-----\n" + 
                    "-----\n" + 
                    "-----\n" + 
                    "-----");

        // Test 2
        assertEquals(maze1.toString(), maze2.toString());

        // Test 3
        assertEquals(maze3.toString(), 
                    "-------X--\n" + 
                    "XXXXXX-X--");

        // Test 4
        assertEquals(maze4.toString(), "Empty Map");


        endSuite();
    }

    public static void task3() {
        startSuite("Maze STUFF");

        // Easy Maze
        Maze maze1 = new Maze("input.txt");
        // Hard Maze
        Maze maze2 = new Maze("input2.txt");
        // empty Maze
        Maze maze3 = new Maze("input3.txt");

        LinkedList path1 = new LinkedList();
        path1.append(0, 0);
        path1.append(0, 1);
        path1.append(0, 2);
        path1.append(0, 3);
        path1.append(1, 3);
        path1.append(2, 3);
        path1.append(3, 3);

        LinkedList path2 = new LinkedList();
        path2.append(0, 0);
        path2.append(0, 1); // Crashes into wall here

        LinkedList path3 = new LinkedList();
        path3.append(0, 0);
        path3.append(4, 0); // Cannot move more than one square at a time

        LinkedList path4 = new LinkedList();
        path4.append(0, 0);
        path4.append(-1, 0); 

        // Test 1 - path is valid and no walls
        assertEquals(maze1.validSolution(0, 0, 3, 3, path1), true); 

        // Test 2 - the start coords don't match
        assertEquals(maze1.validSolution(1, 0, 3, 3, path1), false); 

        // Test 3 - end coords don't match 
        assertEquals(maze1.validSolution(0, 0, 2, 3, path1), false); 

        // Test 4 - crashes into wall
        assertEquals(maze2.validSolution(0, 0, 0, 1, path2), false); 

        // Test 5 - more than one square moved
        assertEquals(maze2.validSolution(0, 0, 4, 0, path3), false); 

        // Test 6 - diagonal movement
        assertEquals(maze2.validSolution(0, 0, 1, 1, path1), false); 

        // Test 7 - index out of bounds
        assertEquals(maze1.validSolution(0, 0, -1, 1, path4), false); 

        // Test 13
        assertEquals(maze1.validSolution(0, 0, -1, 1, null), false); 

        // Test 14
        assertEquals(maze3.validSolution(0, 0, -1, 1, null), false); 

        // Test 15
        assertEquals(maze3.validSolution(0, 0, -1, 1, path2), false); 


        // Test 16
        assertEquals(maze3.solve(0, 0, -1, 1), "No valid solution exists"); 

        // Test 16
        assertEquals(maze3.solve(0, 0, 0, 0), "No valid solution exists"); 

        // Test 8
        assertEquals(maze1.solve(0, 0, 1, 4), 
                    "Solution\n" +
                    "S----\n" + 
                    "@----\n" +
                    "@----\n" + 
                    "@----\n" + 
                    "@E---\n" + 
                    "[0,0] -> [0,1] -> [0,2] -> [0,3] -> [0,4] -> [1,4]");

        // Test 9
        assertEquals(maze2.solve(0, 0, 6, 1), 
                    "Solution\n" +
                    "S@@@@@@X--\n" + 
                    "XXXXXXEX--\n" +
                    "[0,0] -> [1,0] -> [2,0] -> [3,0] -> [4,0] -> [5,0] -> [6,0] -> [6,1]");

        // Test 10
        assertEquals(maze2.solve(6, 1, 0, 0), 
                    "Solution\n" +
                    "E@@@@@@X--\n" + 
                    "XXXXXXSX--\n" +
                    "[6,1] -> [6,0] -> [5,0] -> [4,0] -> [3,0] -> [2,0] -> [1,0] -> [0,0]");

        // Test 11
        assertEquals(maze2.validStarts(0, 0).toString(), "[0,0] -> [1,0] -> [2,0] -> [3,0] -> [4,0] -> [5,0] -> [6,0] -> [6,1]");
        // Test 12
        assertEquals(maze1.validStarts(0, 0).toString(), "[0,0] -> [1,0] -> [2,0] -> [3,0] -> [4,0] -> [0,1] -> [1,1] -> [2,1] -> [3,1] -> [4,1] -> [0,2] -> [1,2] -> [2,2] -> [3,2] -> [4,2] -> [0,3] -> [1,3] -> [2,3] -> [3,3] -> [4,3] -> [0,4] -> [1,4] -> [2,4] -> [3,4] -> [4,4]");

        endSuite();
    }
    public static void startSuite(String name) {
        switch (PRINT_MODE) {
            case 1:
                SUITES_RUN++;
                System.out.println("===================\nStarting: " + name + "\n===================");
                break;
        }
    }

    public static void endSuite() {
        switch (PRINT_MODE) {
            case 1:
                if (TESTS_RUN == TESTS_PASSED) {
                    SUITES_PASSED++;
                    System.out.println("All Tests Passed \n===================");
                } else {
                    System.out.println("Tests Failed: " + (TESTS_RUN - TESTS_PASSED)
                            + "\n===================");
                }
                TESTS_RUN = 0;
                TESTS_PASSED = 0;
                break;
        }
    }

    public static <T> void assertEquals(T actual, T expected) {
        switch (PRINT_MODE) {
            case 1:
                TESTS_RUN++;
                if (actual.equals(expected)) {
                    TESTS_PASSED++;
                    System.out.println("Test " + TESTS_RUN + " Passed ");
                } else {
                    System.out.println("Test " + TESTS_RUN + " Failed: Expected " + expected + " but got "
                            + actual);
                }
                break;
            case 2:
                System.out.println(actual);
                break;
        }
    }

    public static void assertEquals(String actual, String expected) {
        switch (PRINT_MODE) {
            case 1:
                TESTS_RUN++;
                if (actual.equals(expected)) {
                    TESTS_PASSED++;
                    System.out.println("Test " + TESTS_RUN + " Passed ");
                } else {
                    System.out.println("Test " + TESTS_RUN + " Failed: Expected ");
                    boolean wrong = false;
                    for (int i = 0; i < expected.length(); i++) {
                        if (i < actual.length() && actual.charAt(i) == expected.charAt(i)) {
                            if (wrong) {
                                wrong = false;
                            }
                        } else if (!wrong) {
                            wrong = true;
                        }
                        System.out.print(expected.charAt(i));
                    }
                    System.out.println(" but got ");
                    wrong = false;
                    for (int i = 0; i < actual.length(); i++) {
                        if (i < expected.length() && actual.charAt(i) == expected.charAt(i)) {
                            if (wrong) {
                                wrong = false;
                            }
                        } else if (!wrong) {
                            wrong = true;
                        }
                        System.out.print(actual.charAt(i));
                    }
                }
                break;
            case 2:
                System.out.println(actual);
                break;
        }
    }

    public static void endAll() {
        switch (PRINT_MODE) {
            case 1:
                if (SUITES_RUN == SUITES_PASSED) {
                    System.out.println(
                            "\n\n===================\n" + "All Suites Passed " + SUITES_PASSED + "/"
                                    + SUITES_RUN + "\n===================");
                } else {
                    System.out.println("===================\n" + "Some Suites Failed: " + SUITES_PASSED + "/"
                            + SUITES_RUN + "\n===================" );
                }
                break;
        }
    }
}