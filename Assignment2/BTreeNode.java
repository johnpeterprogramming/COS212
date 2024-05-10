// TODO:
// Make use of t which is ceil of m/2
// Make sure node inserted first and then split happens


public class BTreeNode<T extends Comparable<T>> {
    public Comparable<T>[] nodeData; // size - 1
    // THE CHILDREN OF A KEY AT INDEX I WILL BE AT NODECHILDREN[I AND I + 1]
    public BTreeNode<T>[] nodeChildren; // size
    public BTreeNode<T> parent;
    public int size; // Max number of children
    public boolean isLeaf;

    // current number of keys in Node, ie amount of non-null objects in nodeData
    public int n;


    @SuppressWarnings("unchecked")
    public BTreeNode(int size) {
        this.size = size;

        // Every node has at MOST m children
        // Every node (except root and leaves) always has AT LEAST ⌈m/2⌉ children
        nodeChildren = new BTreeNode[size + 1]; // logical size is (size)

        nodeData = new Comparable[size]; // logical size is (size - 1),but there is an empty slot for before split

        parent = null;

        // Node is set to be leaf by default - so I HAVE TO SET IT TO FALSE IF IT ISNT A LEAF
        isLeaf = true;

        n = 0;
    }

    // Returns null if index out of bounds or no node in that index, else return Comparable at that index
    public Comparable<T> getIndex(int i) {
        // Index out of bounds
        if (i < 0 || i >= nodeData.length)
            return null;
        if (nodeData[i] != null)
            return nodeData[i];
        return null;
    }

    // Parent is set to be null by default, so I should checkfor null when calling this function
    public BTreeNode<T> ascend() {
        return parent;
    }

    public BTreeNode<T> descend(int i) {
        if (i < 0 || i >= nodeChildren.length)
            return null;
        if (nodeChildren[i] != null)
            return nodeChildren[i];
        return null;
    }

    /* -------------------------------------------------------------------------- */
    /* Helpers */
    /* -------------------------------------------------------------------------- */

    public void insertIntoNode(T k) {
        int i = n - 1;
        if (isLeaf) {
            // DOESNT HAVE CHILDREN
            // Inserts key in node array in ascending order, shift values that are higher than the key
            while (i >= 0 && nodeData[i].compareTo(k) > 0) {
                nodeData[i + 1] = nodeData[i];
                i--;
            }
            nodeData[i + 1] = k;
            n++;

        } else {
            // HAS CHILDREN
            while (i >= 0 && nodeData[i].compareTo(k) > 0) {
                i--;
            }
            // if CHILD that is to be inserted is full then split
            // nodeChildren[i + 1] is the Node to be inserted
            
            // nodeChildren[i + 1].parent = this;
            nodeChildren[i + 1].insertIntoNode(k);

            if (nodeChildren[i + 1].n > size - 1) {
                split(i + 1, nodeChildren[i + 1]);
            }
        }
    }

    // SPLIT MUST BE CALLED ON THE PARENT
    public void split(int splitIndex, BTreeNode<T> splitNode) {
        // System.out.println("I'm splitting rn");
        BTreeNode<T> newRightChildNode = new BTreeNode<T>(size);
        newRightChildNode.isLeaf = splitNode.isLeaf;

        // newRightChildNode.parent = this;
        // splitNode.parent = this;

        int median = (size - 1) / 2; 
        Comparable<T> medianValue = splitNode.nodeData[median];
        // System.out.println("Median is: " + medianValue);
        
        // Copies Right side of split node over to new node excluding the median
        for (int j = 0; j < median; j++) {
            // Moving the values to the new right child node
            newRightChildNode.nodeData[j] = splitNode.nodeData[j + median + 1];
            splitNode.nodeData[j + median + 1] = null;
            newRightChildNode.n++;
            splitNode.n--;
        }
        // System.out.println("Num values for left side: " + splitNode.n);
        // System.out.println("Num values for right side: " + newRightChildNode.n);
        

        splitNode.nodeData[median] = null;
        splitNode.n--;

        // IF IT HAS CHILDREN - then copy children to newRightChildNode
        if (!splitNode.isLeaf) {
            for (int j = 0; j < size / 2; j++) {
                newRightChildNode.nodeChildren[j] = splitNode.nodeChildren[j + size / 2];
                splitNode.nodeChildren[j + size / 2] = null;
            }
        }


        // Now shift up children so newRightChildNode can be inserted
        for (int j = n; j > splitIndex; j--) {
            nodeChildren[j + 1] = nodeChildren[j];
        }

        // Now newRightChildNode is set to be the RIGHT Side of the split node
        nodeChildren[splitIndex + 1] = newRightChildNode;
        for (int j = n - 1; j >= splitIndex; j--) {
            nodeData[j + 1] = nodeData[j];
        }
        // Move median key up to parent
        nodeData[splitIndex] = medianValue;
        n++;
    }

    public String toString() {
        String out = "|";
        for (int i = 0; i < nodeData.length; i++) {
            if (nodeData[i] == null) {
                out += "null|";
            } else {
                out += nodeData[i].toString() + "|";
            }

        }
        return out;
    }

}
