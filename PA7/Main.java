public class Main {
    public static void main(String[] args) {

        MaxSkewHeap heap1 = new MaxSkewHeap("{}");
        MaxSkewHeap heap2 = new MaxSkewHeap("{123{122{}{}}{12{1{}{}}{}}}");
        MaxSkewHeap heap3 = new MaxSkewHeap("{30{20{15{}{}}{10{}{}}}{10{8{}{}}{6{}{}}}}");

        System.out.println(heap1.toStringOneLine());
        System.out.println(heap2.toStringOneLine());
        System.out.println(heap2.toString());
        System.out.println(heap3.toString());

        heap2.insert(15);
        heap2.insert(200);
        heap2.insert(150);
        System.out.println(heap2.toString());

        // Should return true
        System.out.println(heap1.search(0) == null);

        System.out.println(heap2.search(12).data);
        System.out.println(heap2.search(15).data);
        System.out.println(heap2.search(123).data);

        heap2.remove(123);
        heap2.remove(200);
        System.out.println(heap2.toString());
        System.out.println(heap3.searchPath(15));

        System.out.println(heap2.isLeftist());
        System.out.println(heap2.isRightist());
        System.out.println(heap3.isLeftist());
        System.out.println(heap3.isRightist());
    }
}