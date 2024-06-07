
public class Main {
    public static void main(String[] args) throws Exception {

        Integer test[] = {1,2,3};

        Heap<Integer> test1 = new MinHeap<>(); 
        Heap<Integer> test2 = new MinHeap<>(test); 

        test1.push(5);
        test1.push(1);
        test1.push(9);
        test1.push(3);

        test1.pop();
        System.out.println(test1.toString());
        test1.pop();
        System.out.println(test1.toString());
        test1.pop();
        System.out.println(test1.toString());
        test1.pop();
        System.out.println(test1.toString());

        System.out.println(test2.toString());

        System.out.println(test2.peek().toString());
        System.out.println(test1.peek() == null);

    }
}
