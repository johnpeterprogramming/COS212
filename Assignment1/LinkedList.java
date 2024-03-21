public class LinkedList {
    public CoordinateNode head;

    public LinkedList() {
        this.head = null;
    }

    public LinkedList(int x, int y) {
        this.head = new CoordinateNode(x, y);
    }

    public void append(int x, int y) {
        if (head == null) {
            head = new CoordinateNode(x, y);
        } else {
            appendHelper(head, x, y);
        }
    }
    private void appendHelper(CoordinateNode node, int x, int y) {
        if (node.next == null) {
            node.next = new CoordinateNode(x, y);
        } else {
            appendHelper(node.next, x, y);
        }
    }

    public void appendList(LinkedList other) {
        appendListHelper(other.head);
    }
    private void appendListHelper(CoordinateNode other) {
        if (other != null) {
            append(other.x, other.y);
            appendListHelper(other.next);;
        }
    }

    public boolean contains(int x, int y) {
        return containsHelper(head, x, y);
    }
    private boolean containsHelper(CoordinateNode node, int x, int y) {
        if (node == null) {
            return false;
        } else if (node.x == x && node.y == y) {
            return true;
        } else {
            return containsHelper(node.next, x, y);
        }
    }

    @Override
    public String toString() {
        return toStringHelper(head);
    }
    private String toStringHelper(CoordinateNode node) {
        if (node == null) {
            return "Empty List";
        } else if (node.next == null) {
            return "[" + node.x + "," + node.y + "]";
        } else {
            return "[" + node.x + "," + node.y + "] -> " + toStringHelper(node.next);
        }
    }

    public int length() {
        return lengthHelper(head);
    }
    private int lengthHelper(CoordinateNode node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + lengthHelper(node.next);
        }
    }

    public LinkedList reversed() {
        LinkedList reversedLinkedList = new LinkedList();
        // First a deep copy of the linked list
        reversedLinkedList.head = deepCopy(head);

        // Then it can be reversed
        reversedLinkedList.head = reversedHelper(reversedLinkedList.head);

        return reversedLinkedList;
    }
    private CoordinateNode deepCopy(CoordinateNode node) {
        if (node == null) {
            return null;
        } else {
            CoordinateNode newNode = new CoordinateNode(node.x, node.y);
            newNode.next = deepCopy(node.next);
            return newNode;
        }
    }
    private CoordinateNode reversedHelper(CoordinateNode node) {
        if (node == null || node.next == null) {
            return node;
        } else {
            CoordinateNode reversedListCoordinateNode = reversedHelper(node.next);
            node.next.next = node;
            node.next = null;
            return reversedListCoordinateNode;
        }
    }

    public void pop_back() {
        if (head == null) {
            return;
        } else if (head.next == null) {
            head = null;
        } else {
            pop_backHelper(head);
        }
    }
    private void pop_backHelper(CoordinateNode node) {
        if (node.next.next == null) {
            node.next = null;
        } else {
            pop_backHelper(node.next);
        }
    }
}