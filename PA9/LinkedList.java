public class LinkedList<T> {
    private Node<T> head; // head of the list
    private int size; // tracks the number of elements in the list

    // Node class
    private static class Node<T> {
        T data;
        Node<T> next;

        // Constructor
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Constructor
    public LinkedList() {
        head = null;
        size = 0;
    }

    // Method to add an element to the end of the list
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
        size++;
    }

    // Method to get the data at the specified index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Method to get the index of an element
    // (returns -1 if the element is not found)
    public int indexOf(T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return index;
            } 
            current = current.next;
            index++;
        }
        return -1; // element not found
    }

    // Method to remove an element
    public boolean remove(T data) {
        if (head == null) {
            return false;
        }
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Method to remove an element by index
    public void removeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) { // Removing the head
            head = head.next;
            size--;
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) { // Traverse to the node before the one to be removed
            current = current.next;
        }
        current.next = current.next.next; // Remove the node
        size--;
    }

    // Method to return the size of the list
    public int size() {
        return size;
    }

    // Method to print list data
    public void printList() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

}
