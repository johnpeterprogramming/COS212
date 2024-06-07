import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
    public Node<T> head; // head of the list
    public int size; // tracks the number of elements in the list

    // Constructor
    public LinkedList() {
        head = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    return null;
                }

                T data = current.data;
                this.current = current.next;
                return data;
            }

            @Override
            public void remove() {
            }
        };
    }

    // Method to add an element to the end of the list
    public void enqueue(T data) {
        Node<T> newNode = new Node<T>(data);
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

    // Removes an returns the last element in the list
    public T pop() {
        if (head == null) {
            return null;
        }
        T nodeToDelete = this.get(this.size - 1);

        this.remove(nodeToDelete);

        return nodeToDelete;
    }

    public T dequeue() {
        if (head == null) {
            return null;
        }
        T nodeToDelete = this.get(0);

        this.remove(nodeToDelete);

        return nodeToDelete;
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

    public Vertex[] toVertexArray() {
        Vertex[] array = new Vertex[this.size];
        int i = 0;
        Node<Vertex> current = (Node<Vertex>) this.head;
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        return array;
    }

    public Vertex[] getVertexesSorted() {
        Vertex[] vertexArray = this.toVertexArray();

        for (int i = 0; i < vertexArray.length-1; i++) {
            for (int j=i + 1; j< vertexArray.length; j++) {
                if (vertexArray[i].toString().compareTo(vertexArray[j].toString()) > 0) {
                    Vertex temp = vertexArray[i];
                    vertexArray[i] = vertexArray[j];
                    vertexArray[j] = temp;
                }
            }
        }
        return vertexArray;
    }

    public Edge[] toEdgeArray() {
        Edge[] array = new Edge[this.size];
        int i = 0;
        Node<Edge> current = (Node<Edge>) this.head;
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        return array;
    }

    public Edge[] getEdgesSorted() {
        Edge[] edgeArray = this.toEdgeArray();

        for (int i = 0; i < edgeArray.length-1; i++) {
            for (int j=i + 1; j< edgeArray.length; j++) {
                if (edgeArray[i].toString().compareTo(edgeArray[j].toString()) > 0) {
                    Edge temp = edgeArray[i];
                    edgeArray[i] = edgeArray[j];
                    edgeArray[j] = temp;
                }
            }
        }

        return edgeArray;
    }

    public Edge[] getEdgesSortedByWeight() {
        Edge[] edgeArray = this.toEdgeArray();

        for (int i = 0; i < edgeArray.length-1; i++) {
            for (int j=i + 1; j< edgeArray.length; j++) {
                if (edgeArray[i].weight > edgeArray[j].weight) {
                    Edge temp = edgeArray[i];
                    edgeArray[i] = edgeArray[j];
                    edgeArray[j] = temp;
                }
            }
        }

        return edgeArray;
    }

}
