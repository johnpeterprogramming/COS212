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
        if (args.length == 0) {
            task1();
            task2();
        } else {
            int taskNum = Integer.valueOf(args[0]);
            switch (taskNum) {
                case 1:
                    task1();
                    break;
                case 2:
                    task2();
                    break;
            }
        }
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
        // Test 7
        assertEquals(ll3.toString(), "[0,1] -> [2,0] -> [1,0] -> [0,0]");

        endSuite();
    }

    public static void task2() {
        startSuite("Maze");

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
                    "XXXXXX-X--\n");

        // Test 4
        assertEquals(maze4.toString(), "Empty Map");

        // System.out.println(m.solve(3, 3, 1, 0));
        // System.out.println(m.validStarts(0, 0));

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