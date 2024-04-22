public class Main {
    public static int PRINT_MODE = 1;

    public static int SUITES_RUN = 0;
    public static int SUITES_PASSED = 0;
    public static int TESTS_RUN = 0;
    public static int TESTS_PASSED = 0;

    public static void main(String[] args) {
        try {
            task1();
        } catch (Error e) {}
        try {
            task2();
        } catch (Error e) {}
    }

    public static void task1() {
        startSuite("Constructor, getRoot and isValid");
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        RedBlackNode<Integer> node1 = new RedBlackNode<>(2);
        RedBlackNode<Integer> node2 = new RedBlackNode<>(3);
        RedBlackNode<Integer> node3 = new RedBlackNode<>(1);
        RedBlackNode<Integer> node4 = new RedBlackNode<>(4);


        // Test 1: Null node root
        assertEquals(rbt.getRoot(), rbt.NULL_NODE);

        node1.left = rbt.NULL_NODE;
        node1.right = rbt.NULL_NODE;
        node1.colour = 3; // This should be invalid because colour can only be red or black

        rbt.SENTINEL.right = node1;
        node1.parent = rbt.SENTINEL;

        // Test 2: colour must be red or black
        assertEquals(rbt.isValidRedBlackTree(), false);

        // Test 3: Root must be Black
        node1.colour = rbt.RED;
        assertEquals(rbt.isValidRedBlackTree(), false);
        node1.colour = rbt.BLACK;

        node4.left = rbt.NULL_NODE;
        node4.right = rbt.NULL_NODE;
        node4.colour = rbt.RED;

        node2.left = rbt.NULL_NODE;
        node2.right = node4;

        
        node3.left = rbt.NULL_NODE;
        node3.right = rbt.NULL_NODE;
        
        node1.right = node2;
        node2.parent = node1;
        
        node1.left = node3;
        node3.parent = node1;

        // Test 4: Red node cannot have red children/same amount of black nodes for every path
        node2.colour = rbt.RED;
        System.out.println(rbt.toString());
        assertEquals(rbt.isValidRedBlackTree(), false);
        node2.colour = rbt.BLACK;

        
        // Test 4: getRoot
        assertEquals(rbt.getRoot().data, 2);
        
        System.out.println(rbt.toString());
        // Test 5: is Valid
        assertEquals(rbt.isValidRedBlackTree(), true);



        endSuite();
    }

    public static void task2() {
        startSuite("Buttom up Insert and DELETE");

        RedBlackTree<Integer> bst = new RedBlackTree<>();
        bst.bottomUpInsert(-2);
        
        bst.bottomUpInsert(-1);
        bst.bottomUpInsert(0);
        bst.bottomUpInsert(1);
        bst.bottomUpInsert(2);
        
        bst.bottomUpInsert(3);
        bst.bottomUpInsert(4);
        bst.bottomUpInsert(5);
        bst.bottomUpInsert(6);
        bst.bottomUpInsert(7);
        bst.bottomUpInsert(8);
        bst.bottomUpInsert(9);
        // Duplicate data test
        bst.bottomUpInsert(9);

        bst.bottomUpInsert(-3);
        bst.bottomUpInsert(-4);
        bst.bottomUpInsert(-5);
        bst.bottomUpInsert(-6);
        bst.bottomUpInsert(-7);

        System.out.println(bst.toString());

        assertEquals(bst.isValidRedBlackTree(), true);

        try {
            bst.topDownDelete(-7);
            System.out.println(bst.toString());
            assertEquals(bst.isValidRedBlackTree(), true);
        } catch (Error e) {}

        try {
            bst.topDownDelete(-3);
            System.out.println(bst.toString());
            assertEquals(bst.isValidRedBlackTree(), true);
        } catch (Error e) {}

        try {
            bst.topDownDelete(1);
            System.out.println(bst.toString());
            assertEquals(bst.isValidRedBlackTree(), true);
        } catch (Error e) {}


        try {
            bst.topDownDelete(7);
            System.out.println(bst.toString());
            assertEquals(bst.isValidRedBlackTree(), true);
        } catch (Error e) {}

        try {
            bst.topDownDelete(100);
            bst.topDownDelete(-2);
            bst.topDownDelete(-1);
        } catch (Error e) {}
        try {
            bst.topDownDelete(5);
            bst.topDownDelete(-5);
            bst.topDownDelete(9);
        } catch (Error e) {}
        try {
            bst.topDownDelete(2);
            bst.topDownDelete(8);
            bst.topDownDelete(6);
            bst.topDownDelete(4);
        } catch (Error e) {}
        try {
            bst.topDownDelete(0);
            bst.topDownDelete(-6);
            bst.topDownDelete(-4);
            bst.topDownDelete(3);
        } catch (Error e) {}
        
        bst.bottomUpInsert(5);
        bst.bottomUpInsert(6);
        bst.bottomUpInsert(7);
        bst.bottomUpInsert(0);
        
        System.out.println(bst.toString());
        assertEquals(bst.isValidRedBlackTree(), true);

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