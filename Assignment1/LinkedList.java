public class LinkedList {
    private CoordinateNode head;

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
        if (head == null) {
            head = other.head;
        } else {
            appendListHelper(head, other.head);
        }
    }
    private void appendListHelper(CoordinateNode node, CoordinateNode other) {
        if (node.next == null) {
            node.next = other;
        } else {
            appendListHelper(node.next, other);
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
        reversedLinkedList.head = reversedHelper(head);
        return reversedLinkedList;
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
}