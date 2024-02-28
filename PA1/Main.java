public class Main {
    public static void main(String[] args) {

        RecursiveArray array1 = new RecursiveArray();
        RecursiveArray array2 = new RecursiveArray("1,2,3,4,5,6,7,8,9,10");
        RecursiveArray array3 = new RecursiveArray("1,1,3,4,9,6,2,8,7,13");

        // System.out.println("Array 1: " + array1.array);

        for (int i=0; i<array2.array.length; i++) {
            System.out.print(array2.array[i]);
        }
        System.out.println();

        System.out.println("Array 2: " + array2.toString());

        array1.append(6);
        System.out.println("Array 1: " + array1.toString());
        array1.prepend(10);
        System.out.println("Array 1: " + array1.toString());

        System.out.println("Array 1 contains 10: " + array1.contains(10));
        System.out.println("Array 1 contains 2: " + array1.contains(2));

        array2.sortDescending();
        System.out.println("Array 2: " + array2.toString());

        System.out.println("Array before sort 3: " + array3.toString());
        array3.sortDescending();
        System.out.println("Array after sort 3: " + array3.toString());

        array3.sortDescending();
        System.out.println("Array 3: " + array3.toString());

        array1.sortAscending();
        System.out.println("Array 1: " + array1.toString());
        System.out.println("Array 1 is in ascending: " + array1.isAscending());

        array1.sortDescending();
        System.out.println("Array 1: " + array1.toString());
        System.out.println("Array 1 is in descending: " + array1.isDescending());
        System.out.println("Array 1 is in ascending: " + array1.isAscending());
    }
}