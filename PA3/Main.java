public class Main {

    public static int PRINT_MODE = 1;

    public static int SUITES_RUN = 0;
    public static int SUITES_PASSED = 0;
    public static int TESTS_RUN = 0;
    public static int TESTS_PASSED = 0;

    public static void main(String[] args) {
        task1();

    }

    public static void task1() {
        startSuite("Constructor Testing");

        SplayTree tree = new SplayTree();
        SplayTree tree2 = new SplayTree("{[u10:null%]{}{}}");
        SplayTree tree3 = new SplayTree("{[u10:50%]{[u5:40%]{}{}}{}}");
        SplayTree tree4 = new SplayTree("{[u10:50%]{[u5:40%]{}{}}{[u15:60%]{}{}}}");

        assertEquals(tree.toString(), "Empty Tree");
        assertEquals(tree2.toString(), "└── [u10:null%]\n");
        assertEquals(tree3.toString(), "└── [u10:50%]\n" +
                                       "    └── [u5:40%]\n");
        assertEquals(tree4.toString(),  "│   ┌── [u15:60%]\n" +
                                        "└── [u10:50%]\n" + 
                                        "    └── [u5:40%]\n");

        assertEquals(tree4.sortByStudentNumber(), "[u5:40%][u10:50%][u15:60%]\n");
        assertEquals(tree3.sortByStudentNumber(), "[u5:40%][u10:50%]\n");

        System.out.print(tree2.sortByMark().toString());
        System.out.print(tree3.sortByMark().toString());

        // System.out.println(tree4.access(10).toString());
        // System.out.println(tree4.access(0).toString());
        // System.out.println(tree4.access(15, 100).toString());
        // System.out.println(tree3.access(5).toString());

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