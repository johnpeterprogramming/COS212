// ALL BTREES WILL HAVE ODD DEGREES
public class BTree<T extends Comparable<T>> {
    public BTreeNode<T> root;
    public int m;

    public BTree(int m) {
        this.m = m;
        this.root = null;
    }

    public void insert(T data) {
        if (root == null) {
            root = new BTreeNode<>(m);
            root.nodeData[0] = data;
            // Increment num keys
            root.n = 1;
        } else {
            if (root.n == m - 1) {
                // This mean that there will be overflow and splits will happen
                BTreeNode<T> newNode = new BTreeNode<>(m);
                newNode.isLeaf = false;
                newNode.nodeChildren[0] = root;
                // newNode.nodeChildren[0].parent = newNode;

                newNode.insertIntoNode(data);
            
                root = newNode;
            } else {
                root.insertIntoNode(data);
            }
        }

    }

    public String printPath(T key) {
        return null;
    }

    /* -------------------------------------------------------------------------- */
    /* Please don't change this toString, I tried to make it pretty. */
    /* -------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------- */
    /* Also we may test against it */
    /* -------------------------------------------------------------------------- */

    @Override
    public String toString() {
        if (root == null) {
            return "The B-Tree is empty";
        }
        StringBuilder builder = new StringBuilder();
        buildString(root, builder, "", true);
        return builder.toString();
    }

    private void buildString(BTreeNode<T> node, StringBuilder builder, String prefix, boolean isTail) {
        if (node == null)
            return;

        builder.append(prefix).append(isTail ? "└── " : "├── ");
        for (int i = 0; i < node.nodeData.length; i++) {
            if (node.nodeData[i] != null) {
                builder.append(node.nodeData[i]);
                if (i < node.nodeData.length - 1 && node.nodeData[i + 1] != null) {
                    builder.append(", ");
                }
            }

        }
        if (node.parent != null)
            builder.append("\t(p:" + node.parent.nodeData[0].toString() + ")");
        builder.append("\n");

        int numberOfChildren = m;
        for (int i = 0; i < numberOfChildren; i++) {

            BTreeNode<T> child = node.descend(i);
            buildString(child, builder, prefix + (isTail ? "    " : "│   "), i == numberOfChildren - 1);
        }
    }
}
