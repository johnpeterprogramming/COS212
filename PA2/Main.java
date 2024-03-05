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
            task3();
        } else {
            int taskNum = Integer.valueOf(args[0]);
            switch (taskNum) {
                case 1:
                    task1();
                    break;
                case 2:
                    task2();
                    break;
                case 3:
                    task3();
                    break;

            }
        }
        endAll();
    }

    public static void task1() {
        startSuite("Insert, Delete, Contains Testing");
        BST<Integer> bst1 = new BST<Integer>();
        bst1.insert(5);
        bst1.insert(1);
        bst1.insert(10);
        bst1.insert(7);
        bst1.insert(7);

        // Test 1 - insert
        assertEquals(bst1.toString(),
                     "│   ┌── 10\n" +
                     "│   │   └── 7\n" +
                     "└── 5\n" + 
                     "    └── 1\n");

        BST<Integer> bst2= new BST<Integer>();
        bst2.insert(1);
        
        // Test 2 - single node
        assertEquals(bst2.toString(), "└── 1\n");

        BST<Integer> bst3 = new BST<Integer>();
        // Test 3 - empty tree
        assertEquals(bst3.toString(),"Empty tree");

        // Test 4 - delete
        bst1.delete(7);
        bst1.delete(5);
        assertEquals(bst1.toString(),
                    "└── 10\n" + 
                    "    └── 1\n");

        // Test 5 - delete empty
        bst2.delete(1);
        assertEquals(bst2.toString(),"Empty tree");

        // Test 6 - contains
        bst2.insert(1);
        bst2.insert(2);
        bst2.insert(3);
        assertEquals(bst2.contains(2), true);

        // Test 7 - contains
        assertEquals(bst2.contains(4), false);

        BST<String> bst4 = new BST<String>();
        bst4.insert("1");
        bst4.insert("12");
        bst4.insert("3");
        bst4.insert("10");
        bst4.insert("-1");

        assertEquals(bst4.toString(),
                     "│   ┌── 12\n" +
                     "│   │   │   ┌── 10\n" +
                     "│   │   └── 3\n" + 
                     "└── 1\n" + 
                     "    └── -1\n");

        endSuite();
    }

    public static void task2() {
        startSuite("Searchpath, Find Max/Min, Getters");
        BST<Integer> bst1 = new BST<Integer>();
        bst1.insert(0);
        bst1.insert(22);
        bst1.insert(80);
        bst1.insert(7);
        bst1.insert(25);
        // Test 1 - search path
        assertEquals(bst1.printSearchPath(7), "0 -> 22 -> 7");

        // Test 2 - search path
        assertEquals(bst1.printSearchPath(25), "0 -> 22 -> 80 -> 25");

        // Test 3 - not found
        assertEquals(bst1.printSearchPath(200), "0 -> 22 -> 80 -> Null");

        // Test 4 - find Max
        assertEquals(bst1.findMax().data, 80);

        bst1.insert(100);
        // Test 5 - find Max
        assertEquals(bst1.findMax().data, 100);

        // Test 6 - find Min
        assertEquals(bst1.findMin().data, 0);

        // Test 7 - getNode
        assertEquals(bst1.getNode(80).data, 80);

        // Test 8 - getNode - none
        BinaryNode<Integer> nullNode = bst1.getNode(110);
        assertEquals(nullNode == null, true);

        // Test 9 - getNumLeaves
        assertEquals(bst1.getNumLeaves(), 3);

        // Test 10 - getHeight
        // System.out.println(bst1.toString());
        assertEquals(bst1.getHeight(), 4);
        endSuite();
    }

    public static void task3() {
        startSuite("isSuperficiallyBalanced, extractBiggestSuperficiallyBalancedSubtree");
        BST<Integer> bst1 = new BST<Integer>();
        bst1.insert(0);
        bst1.insert(22);
        bst1.insert(80);
        bst1.insert(7);
        

        assertEquals(bst1.isSuperficiallyBalanced(), true);

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