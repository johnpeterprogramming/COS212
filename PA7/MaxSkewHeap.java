public class MaxSkewHeap {
    Node root;

    @Override
    public String toString() {
        return (root == null ? "Empty Tree" : toString(root, "", true));
    }

    public String toString(Node node, String prefix, boolean end) {
        String res = "";
        if (node.right != null) {
            res += toString(node.right, prefix + (end ? "│   " : "    "), false);
        }
        res += prefix + (end ? "└── " : "┌── ") + node.toString() + "\n";
        if (node.left != null) {
            res += toString(node.left, prefix + (end ? "    " : "│   "), true);
        }
        return res;
    }

    public String toStringOneLine() {
        return (root == null ? "{}" : toStringOneLine(root));
    }

    public String toStringOneLine(Node ptr) {
        if (ptr == null) {
            return "{}";
        }
        return "{" + ptr.toString() + toStringOneLine(ptr.left) + toStringOneLine(ptr.right) + "}";
    }

    public MaxSkewHeap() {
        root = null;
    }

    public MaxSkewHeap(String input) {
        root = SkewHeapHelper(input, null);
    }

    private Node SkewHeapHelper(String input, Node node) {
        if (input.equals("{}")) {
            return null;
        }

        input = input.substring(1, input.length() - 1);

        String nodeString = input.substring(0, input.indexOf('{'));
        Node newNode = new Node(Integer.parseInt(nodeString));


        String leftSubTree = input.substring(input.indexOf('{'), findClosingBracket(input, input.indexOf('{')) + 1);
        input = input.substring(findClosingBracket(input, input.indexOf('{')) + 1, input.length());
        String rightString = input;
        
        newNode.left = SkewHeapHelper(leftSubTree, newNode);
        newNode.right = SkewHeapHelper(rightString, newNode);

        return newNode;
    }

    private int findClosingBracket(String input, int openingBracketPos) {
        int bracketCounter = 1;
        for (int i = openingBracketPos+ 1; i < input.length(); i++) {
            if (input.charAt(i) == '{') {
                bracketCounter++;
            } else if (input.charAt(i) == '}') {
                bracketCounter--;
                if (bracketCounter == 0) {
                    return i;
                }
            }
        }
        return -1; // This should never happen though because every opening bracket has a closing bracket
    }


    private Node merge(Node heap1, Node heap2) {
        if (heap1 == null)
            return heap2;
        if (heap2 == null)
            return heap1;

        if (heap1.data < heap2.data)  {
            Node temp = heap1;
            heap1 = heap2;
            heap2 = temp;
        }

        Node temp = heap1.left;
        heap1.left = heap1.right;
        heap1.right = temp;

        heap1.left = merge(heap2, heap1.left);

        return heap1;
        
    }

    public void insert(int data) {
        Node newNode = new Node(data);
        root = merge(root, newNode);
    }

    public void remove(int data) {
        root = removeHelper(root, data);
        
    }
    private Node removeHelper(Node node, int data) {
        if(node == null)
            return null;

        if (node.data == data) {
            return merge(node.left, node.right);
        }
        node.left = removeHelper(node.left, data);
        node.right = removeHelper(node.right, data);
        return node;
    }

    public Node search(int value) {
        return searchHelper(root, value);
    }
    private Node searchHelper(Node curr, int value) {
        if (curr == null || curr.data < value)
            return null;

        if (curr.data == value)
            return curr;

        Node searchNode = searchHelper(curr.right, value);
        if (searchNode == null)
            searchNode = searchHelper(curr.left, value);
        
        return searchNode;

    }

    public String searchPath(int value) {
        return searchPathHelper(root, value, "");
    }

    private String searchPathHelper(Node node, int value, String path) {
        if (node == null) {
            return path;
        }
        if (node.data == value) {
            return path + "[" + value + "]";
        } else
            path += node.data;

        if (node.data < value) {
            return path;
        }
        path += "->";
        String foundPath = searchPathHelper(node.right, value, path);
        if (!foundPath.endsWith("[" + value + "]")) {
            foundPath = searchPathHelper(node.left, value, path);
        }
        return foundPath;
    }

    public boolean isLeftist() {
        return isLeftistHelper(root);
    }

    public boolean isRightist() {
        return isRightistHelper(root);
    }

    private boolean isLeftistHelper(Node curr) {
        if (curr == null) 
            return true;

        int leftHeight = getHeight(curr.left);
        int rightHeight = getHeight(curr.right);

        if (leftHeight >= rightHeight) {
            return isLeftistHelper(curr.left) && isLeftistHelper(curr.right);
        }

        return false;
    }

    private boolean isRightistHelper(Node curr) {
        if (curr == null) 
            return true;

        int leftHeight = getHeight(curr.left);
        int rightHeight = getHeight(curr.right);

        if (leftHeight <= rightHeight) {
            return isRightistHelper(curr.left) && isRightistHelper(curr.right);
        }

        return false;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }
}
