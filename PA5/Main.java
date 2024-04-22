public class Main {
    public static int PRINT_MODE = 1;

    public static int SUITES_RUN = 0;
    public static int SUITES_PASSED = 0;
    public static int TESTS_RUN = 0;
    public static int TESTS_PASSED = 0;

    public static void main(String[] args) {
        task1();
        task2();
    }

    public static void task1() {
        startSuite("Prime Number generator"); 
        PrimeNumberGenerator rng = new PrimeNumberGenerator();

        assertEquals(rng.toString(), "[2]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[3]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[5]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[7]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[11]->[13]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[13]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[17]->[19]->[23]");
        rng.nextPrime();

        assertEquals(rng.toString(), "[19]->[23]");

        endSuite();
    }

    public static void task2() {
        startSuite("Hashmap");

        Hashmap hm = new Hashmap("5[-,u1:1%,u6:12%,u3:1%]");
        Hashmap hm2 = new Hashmap();
        Hashmap hm3 = new Hashmap("4[u1:1%,u3:1%]");
        System.out.println(hm3.toString());
        assertEquals(hm.prime.currentPrime(), 5);

        // System.out.println(hm.hash(1));
        // System.out.println(hm.hash(6));
        // System.out.println(hm.hash(3));
        System.out.println(hm.toString());

        System.out.println(hm.search(1));
        System.out.println(hm.search(6));
        System.out.println(hm.search(3));
        System.out.println((hm.search(2) == null));

        hm2.insert(8, 6);
        System.out.println(hm2.toString());
        hm2.insert(6, 6);
        System.out.println(hm2.toString());
        hm2.insert(4, 6);
        System.out.println(hm2.toString());
        hm2.insert(9, 6);
        System.out.println(hm2.toString());
        hm2.insert(10, 6);
        System.out.println(hm2.toString());

        hm2.remove(10);
        hm2.remove(1);
        hm2.remove(8);
        System.out.println(hm2.toString());

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