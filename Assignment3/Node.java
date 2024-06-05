public class Node<T> {
    public T data;
    public Node<T> next;

    // Constructor
    Node(T data) {
        this.data = data;
        this.next = null;
    }
}
