public class BST<T extends Comparable<T>> {

    public BinaryNode<T> root;

    public BST() {
        root = null;
    }

    // Delete by merge
    public void delete(T data) {
        root = deleteHelper(data, root);
    }
    private BinaryNode<T> deleteHelper(T data, BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.data) < 0) {
            node.left = deleteHelper(data, node.left);
        } else if (data.compareTo(node.data) > 0) {
            node.right = deleteHelper(data, node.right);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            node.data = inOrderSuccessorHelper(node.right).data;
            node.right = deleteHelper(node.data, node.right);
        }
        return node;
    }
    private BinaryNode<T> inOrderSuccessorHelper(BinaryNode<T> node) {
        if (node.left == null)
            return node;
        else 
            return inOrderSuccessorHelper(node.left);
    }

    public boolean contains(T data) {
        return containsHelper(data, root);
    }
    private boolean containsHelper(T data, BinaryNode<T> node) {
        if (node == null) 
            return false;
        if (data.compareTo(node.data) == 0) 
            return true;
        else if (data.compareTo(node.data) < 0) 
            return containsHelper(data, node.left);
        else 
            return containsHelper(data, node.right);
    }

    public void insert(T data) {
        root = insertHelper(data, root);
    }
    private BinaryNode<T> insertHelper(T data, BinaryNode<T> node) {
        if (node == null) {
            return new BinaryNode<T>(data);
        } else {
            if (data.compareTo(node.data) < 0)
                node.left = insertHelper(data, node.left);
            else if (data.compareTo(node.data) > 0) 
                node.right = insertHelper(data, node.right);
            return node;
        }
    }

    public String printSearchPath(T data) {
        return printSearchPathHelper(data, root);
    }
    private String printSearchPathHelper(T data, BinaryNode<T> node) {
        if (node == null)
            return "Null";
        if (data.compareTo(node.data) == 0) 
            return node.data.toString();
        else if (data.compareTo(node.data) < 0)
            return node.data.toString() + " -> " + printSearchPathHelper(data, node.left);
        else 
            return node.data.toString() + " -> " + printSearchPathHelper(data, node.right);
    }

    public BinaryNode<T> findMax() {
        if (root == null)
            return null;
        return findMaxHelper(root); 
    }
    private BinaryNode<T> findMaxHelper(BinaryNode<T> node) {
        if (node.right == null)
            return node;
        return findMaxHelper(node.right);
    }

    public BinaryNode<T> findMin() {
        if (root == null)
            return null;
        return findMinHelper(root); 
    }
    private BinaryNode<T> findMinHelper(BinaryNode<T> node) {
        if (node.left == null)
            return node;
        return findMinHelper(node.left);
    }

    public BinaryNode<T> getNode(T data) {
        return getNodeHelper(data, root);
    }
    private BinaryNode<T> getNodeHelper(T data, BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.data) < 0) 
            return getNodeHelper(data, node.left);
        else if (data.compareTo(node.data) > 0) 
            return getNodeHelper(data, node.right);
        else 
            return node;
    }

    public int getHeight() {
        return getHeightHelper(root);
    }
    private int getHeightHelper(BinaryNode<T> node) {
        if (node == null)
            return 0;
        return 1 + Math.max(getHeightHelper(node.left), getHeightHelper(node.right));
    }


    public int getNumLeaves() {
        return getNumLeavesHelper(root);
    }
    private int getNumLeavesHelper(BinaryNode<T> node) {
        if (node == null) 
            return 0;
        if (node.left == null && node.right == null) 
            return 1;
        return getNumLeavesHelper(node.left) + getNumLeavesHelper(node.right);
    }

    public boolean isSuperficiallyBalanced() {
        return false;
    }

    public BST<T> extractBiggestSuperficiallyBalancedSubTree() {
        return null;
    }
    ///////////////

    private StringBuilder toString(BinaryNode<T> node, StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if (node.right != null) {
            toString(node.right, new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(node.data.toString()).append("\n");
        if (node.left != null) {
            toString(node.left, new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }
        return sb;
    }

    @Override
    public String toString() {
        return root == null ? "Empty tree" : toString(root, new StringBuilder(), true, new StringBuilder()).toString();
    }

}
