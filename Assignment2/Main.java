import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        // test1(); 
        // test2();
        test3();
    }
    
    public static void test1() {
        // Hashmap test
        Hashmap<Integer, Integer> hm = new Hashmap<>();
    
        System.out.println(hm.toString());
        hm.insert(5, 1);
        hm.insert(2, 2);
    
        hm.insert(1, 6);
        hm.insert(3, 8);
        System.out.println(hm.toString());
    
        System.out.println(hm.get(2));
        System.out.println(hm.get(3));
        System.out.println(hm.get(8));
    
    
        hm.delete(1);
        hm.delete(1);
        hm.delete(4);
        hm.delete(5);
        System.out.println(hm.toString());
    
        hm.insert(10, 5);
        hm.insert(15, 9);
        hm.insert(5, -1);
        System.out.println(hm.toString());
        
        Object keys[] = hm.getKeys();
        for (Object key : keys) {
            System.out.println(key);
        }
    
        hm.clear();
    
        System.out.println(hm.toString());

    }

    public static void test2() {
        BTree<Integer> btree = new BTree<>(5);

        btree.insert(1);
        btree.insert(3);
        btree.insert(6);
        btree.insert(9);
        System.out.println(btree.toString());

        // Here there will be split and  5 should be root
        btree.insert(5);
        System.out.println(btree.toString());

        btree.insert(10);
        btree.insert(11);
        System.out.println(btree.toString());

        // // The 7 will go to root and the 6-9 and 10-11 will be split
        btree.insert(7);
        System.out.println(btree.toString());

        btree.insert(13);
        btree.insert(13);
        btree.insert(13);
        btree.insert(8);
        btree.insert(8);
        btree.insert(9);
        btree.insert(8);
        btree.insert(8);
        btree.insert(8);
        btree.insert(8);
        System.out.println(btree.toString());
    }

    public static void test3() {
        BTree<Integer> btree = new BTree<>(3);

        for (int i=1; i<= 6; i++) {
            btree.insert(i);
            System.out.println(btree.toString());

        }

        // btree.insert(15);
        // System.out.println(btree.toString());

    }

}
