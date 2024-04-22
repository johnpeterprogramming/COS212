public class RedBlackTree<T extends Comparable<T>> {

    /*
     * Sentinel is not the root. Go check the text book if this doesn't make sense
     */
    public RedBlackNode<T> SENTINEL;
    public RedBlackNode<T> NULL_NODE;

    public static final int RED = 1;
    public static final int BLACK = 0;

    public RedBlackTree() {
        // Null_Node doesn't have any children
        // Also don't need to set colour to black, because they are black by default
        NULL_NODE = new RedBlackNode<T>(null);

        SENTINEL = new RedBlackNode<T>(null);
        // Initial children of the sentinel are null nodes
        SENTINEL.left = SENTINEL.right = NULL_NODE;
    }

    public RedBlackNode<T> getRoot() {
        // Sentinel.right is NULL_NODE by default so don't need to add check if it's null
        return SENTINEL.right;
    }

    public boolean isValidRedBlackTree() {
        // Case 2: Root of tree must be BLACK
        if(getRoot().colour != BLACK) return false;

        // Case 4: All paths from a node to its leaves have the same number of black nodes
        // System.out.println("BLACKS:" + getNumBlackNodesFromOnePath(getRoot()));
        if (rootToLeafSameNumBlackNodes(getRoot(), 0, getNumBlackNodesFromOnePath(getRoot())) == false) return false;

        return traverser(getRoot());
    }

    private boolean traverser(RedBlackNode<T> currentNode) {
        if(currentNode != NULL_NODE) {
            // System.out.println(currentNode.toString());

            // Case 1: Either red or black
            if (currentNode.colour != RED && currentNode.colour != BLACK ) return false;
            
            // Case 3: If a red node has a red child
            if (currentNode.colour == RED && (currentNode.left.colour == RED || currentNode.right.colour == RED)) return false;

            if (traverser(currentNode.left) == false) return false;
            if (traverser(currentNode.right) == false) return false;
        }
        return true;
    }

    // Goes all the way to left and gets count to compare with other paths
    private int getNumBlackNodesFromOnePath(RedBlackNode<T> currentNode) {
        if (currentNode != NULL_NODE)
            return getNumBlackNodesFromOnePath(currentNode.left) + (currentNode.colour == BLACK ? 1 : 0);
        else
            return 0;
    }

    private boolean rootToLeafSameNumBlackNodes(RedBlackNode<T> currentNode, int currentBlackNodeCount, int finalBlackNodeCount) {
        // System.out.println("AT NODE: " + currentNode.data);
        if (currentNode == NULL_NODE) {
            // System.out.println(currentBlackNodeCount + "  " + finalBlackNodeCount);
            return (currentBlackNodeCount == finalBlackNodeCount);
        }
        else {
            // continue traversing
            if (currentNode.colour == BLACK)
                currentBlackNodeCount++;
            
            if (rootToLeafSameNumBlackNodes(currentNode.left, currentBlackNodeCount, finalBlackNodeCount) == false) {
                return false;
            }
            if (rootToLeafSameNumBlackNodes(currentNode.right, currentBlackNodeCount, finalBlackNodeCount) == false) {
                return false;
            }
            
        }
        // System.out.println("returning TRUE");
        // If it gets the end without returning false, there where no issues and returns true
        return true;
    }

    public void bottomUpInsert(T data) {
        RedBlackNode<T> currentNode = getRoot();

        RedBlackNode<T> newNode = new RedBlackNode<T>(data);
        newNode.left = NULL_NODE;
        newNode.right = NULL_NODE;
        newNode.colour = RED;
        newNode.parent = NULL_NODE;

        while (currentNode != NULL_NODE) {
            newNode.parent = currentNode;
            if (data.compareTo(currentNode.data) > 0)
                currentNode = currentNode.right;
            else if (data.compareTo(currentNode.data) < 0)
                currentNode = currentNode.left;
            else 
                // Do nothing when the key already exists
                return;
        }
        
        if (newNode.parent == NULL_NODE)  {
            // Case where node is inserted as root
            SENTINEL.right = newNode;
            newNode.parent = SENTINEL;
        }
        else if (data.compareTo(newNode.parent.data) > 0) 
            newNode.parent.right = newNode;
        else if (data.compareTo(newNode.parent.data) < 0)
            newNode.parent.left = newNode;

        // System.out.println(toString());
        // Fix properties after insert
        fixRBProperties(newNode);
    }

    private void fixRBProperties(RedBlackNode<T> insertedNode)  {
        RedBlackNode<T> parent = insertedNode.parent;

        // case 1: new node is root
        if (getRoot() == insertedNode) {
            insertedNode.colour = BLACK;
            return;
        }

        // if parent is black, no further need for fixup
        if (parent.colour == BLACK)
            return;
        // After that check the parent is red, the newly inserted node is red aswell so that causes an issue
        

        RedBlackNode<T> grandparent = parent.parent;
        RedBlackNode<T> uncle = getUncle(parent);

        // System.out.println("Uncle: " + uncle.toString() + " Parent: " + parent.toString() + " node: " + insertedNode.toString());

        // case 2: uncle is red -> recolour parent, grandparent and uncle
        if (uncle != NULL_NODE && uncle.colour == RED) {
            // System.out.println(toString());
            insertedNode.parent.colour = BLACK;
            grandparent.colour = RED;
            uncle.colour = BLACK;

            // System.out.println("Checkpoint: uncle is red!");
            fixRBProperties(grandparent);
        } else if (parent == grandparent.left) {
            // Case where uncle is black and node is an inner child (left right)
            if (insertedNode == parent.right) {
                rotateLeft(parent);
                // System.out.println("After left rotation");
                // System.out.println(toString());

                parent = insertedNode;
            }

            rotateRight(grandparent);

            parent.colour = BLACK;
            grandparent.colour = RED;
        } else {
            // Uncle is black and node is inner child (right left)
            if (insertedNode == parent.left) {
                 rotateRight(parent); 

                parent = insertedNode;
            }

            rotateLeft(grandparent);

            parent.colour = BLACK;
            grandparent.colour = RED;
        }

        
    }

    private RedBlackNode<T> getUncle(RedBlackNode<T> parent) {
        // If there is no uncle return NULL_NODE
        try {
            RedBlackNode<T> grandparent = parent.parent;
    
            if (grandparent.right == parent)
                return grandparent.left;
            else
                return grandparent.right;
        } catch (NullPointerException e) {
            return NULL_NODE;
        }
    }

    private void rotateRight(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.parent;
        RedBlackNode<T> leftChildNode = node.left; 

        node.left = leftChildNode.right;
        if (leftChildNode.right != NULL_NODE)
            leftChildNode.right.parent = node;

        leftChildNode.right = node;
        node.parent = leftChildNode;

        replaceParentsChild(parent, node, leftChildNode);
    }

    private void rotateLeft(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.parent;
        RedBlackNode<T> rightChildNode = node.right; 

        node.right = rightChildNode.left;
        if (rightChildNode.left != NULL_NODE)
            rightChildNode.left.parent = node;

        rightChildNode.left = node;
        node.parent = rightChildNode;

        replaceParentsChild(parent, node, rightChildNode);
    }

    private RedBlackNode<T> searchNode(T data) {
        RedBlackNode<T> currentNode = getRoot();
        while (currentNode != NULL_NODE) {
            if (currentNode.data.compareTo(data) > 0)
                currentNode = currentNode.left;
            else if (currentNode.data.compareTo(data) < 0)
                currentNode = currentNode.right;
            else 
                return currentNode;
        }
        // Couldn't find node
        return NULL_NODE;
    }

    public void topDownDelete(T data) {
        RedBlackNode<T> deletedNode = searchNode(data);

        // DO nothing when noe doesn't exist
        if (deletedNode == NULL_NODE)
            return;

        int deletedNodeColour = deletedNode.colour;        
        RedBlackNode<T> replacementNode;

        if (deletedNode.left == NULL_NODE || deletedNode.right == NULL_NODE) {
            replacementNode = deleteNodeNoneOrOneChild(deletedNode);
            deletedNodeColour = deletedNode.colour;
        } else {
            // Node has exactly two children
            RedBlackNode<T> minNode = inOrderSuccessor(deletedNode.right);

            deletedNode.data = minNode.data;

            replacementNode = deleteNodeNoneOrOneChild(minNode);
            deletedNodeColour = minNode.colour;
        }

        // Nothing has to happen if the deleted node was red
        if (deletedNodeColour == BLACK) {
            // System.out.println("Replacement Node: " + replacementNode.toString());
            try {
                fixRBPropertiesDelete(replacementNode);
            } catch (NullPointerException e) {

            }
        }
    }

    private void fixRBPropertiesDelete(RedBlackNode<T> node) {
        if (node == getRoot()) {
            node.colour = BLACK;
            return;
        }

        RedBlackNode<T> sibling = getSibling(node);

        // Case where sibling is red
        if (sibling.colour == RED) {
            sibling.colour = BLACK;
            node.parent.colour = RED;

            if (node == node.parent.left) 
                rotateLeft(node.parent);
            else
                rotateRight(node.parent);
            
            sibling = getSibling(node);
        }

        // Case where black sibling has two black children
        if (sibling.left.colour == BLACK && sibling.right.colour == BLACK) {
            sibling.colour = RED;

            if (node.parent.colour == RED)
                node.parent.colour = BLACK;
            else 
                fixRBPropertiesDelete(node.parent);

        } else {
            // black sibling with atleast one red child
            if (node == node.parent.left && sibling.right.colour == BLACK) {
                sibling.left.colour = BLACK;
                sibling.colour = RED;
                rotateRight(sibling);
                sibling = node.parent.right;
            } else if (node != node.parent.left && sibling.left.colour == BLACK) {
                sibling.right.colour = BLACK;
                sibling.colour = RED;
                rotateLeft(sibling);
                sibling = node.parent.left;
            }

            sibling.colour = node.parent.colour;
            node.parent.colour = BLACK;
            if (node == node.parent.left) {
                sibling.right.colour = BLACK;
                rotateLeft(node.parent);
            } else {
                sibling.left.colour = BLACK;
                rotateRight(node.parent);
            }
        }
    }

    private RedBlackNode<T> getSibling(RedBlackNode<T> node) {
        if (node.parent.left == node)
            return node.parent.right;
        else 
            return node.parent.left;
    }

    private void replaceParentsChild(RedBlackNode<T> parent, RedBlackNode<T> oldNode, RedBlackNode<T> replacementNode) {
        // This function assumes that the child does indeed exist, because I will not be checking the case where it doesn't
        if (parent.left == oldNode)
            parent.left = replacementNode;
        else
            parent.right = replacementNode;
        
        if (replacementNode != NULL_NODE)
            replacementNode.parent = parent;
    }

    private RedBlackNode<T> deleteNodeNoneOrOneChild(RedBlackNode<T> deleteNode) {
        if (deleteNode.left != NULL_NODE) {
            replaceParentsChild(deleteNode.parent, deleteNode, deleteNode.left);
            return deleteNode.left;
        } else if (deleteNode.right != NULL_NODE) {
            replaceParentsChild(deleteNode.parent, deleteNode, deleteNode.right);
            return deleteNode.right;
        } else {
            // Case that node has absolutely no children
            NULL_NODE.parent = deleteNode.parent;
            replaceParentsChild(deleteNode.parent, deleteNode, NULL_NODE);
            return NULL_NODE;
        }

    }

    private RedBlackNode<T> inOrderSuccessor(RedBlackNode<T> node) {
        // This function should be called on the right child of the node we want the next inorder successor
        if (node.left != NULL_NODE)
            return inOrderSuccessor(node.left);
        else 
            return node;
    }

    // private RedBlackNode<T> postOrderSuccessor(RedBlackNode<T> node) {
    //     // This function should be called on the left child of the node we want the next the highest value node thats smaller that the node
    //     if (node != NULL_NODE)
    //         return postOrderSuccessor(node.right);
    //     else 
    //         return node.parent;
    // }

    /* -------------------------------------------------------------------------- */
    /* Private methods, which shouldn't be called from outside the class */
    /* -------------------------------------------------------------------------- */

    /* -------------------------------------------------------------------------- */
    /* Please don't change this toString, I worked really hard to make it pretty. */
    /* Also, it matches the website. -------------------------------------------- */
    /* Also, also, we might test against it ;) ---------------------------------- */
    /* -------------------------------------------------------------------------- */
    private StringBuilder toString(RedBlackNode<T> node, StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if (node.right != NULL_NODE) {
            toString(node.right, new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(node.toString()).append("\n");
        if (node.left != NULL_NODE) {
            toString(node.left, new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }
        return sb;
    }

    @Override
    public String toString() {
        return SENTINEL.right == NULL_NODE ? "Empty tree"
                : toString(SENTINEL.right, new StringBuilder(), true, new StringBuilder()).toString();
    }

    public String toVis() {
        return toVisHelper(getRoot());
    }

    private String toVisHelper(RedBlackNode<T> node) {
        if (node == NULL_NODE) {
            return "{}";
        }
        String leftStr = toVisHelper(node.left);
        String rightStr = toVisHelper(node.right);
        return "{" + node.toString() + leftStr + rightStr + "}";
    }

}
