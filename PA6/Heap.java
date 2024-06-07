public abstract class Heap<T extends Comparable<T>> {

    public Comparable<T>[] data;
    public int size;

    public Heap() {
        this.data = (Comparable<T>[]) new Comparable[2];
        size = 0;
    }

    public Heap(T[] array) {
        // Assume array is never empty
        this.data = (Comparable<T>[]) new Comparable[array.length];
        this.size = array.length;
        System.arraycopy(array, 0, this.data, 0, this.size);

        // Floyds algorithm
        // Last non leaf node
        int i = getParentIndex(size - 1);
        while (i >= 0) {
            // Comparable<T> curr = data[i];
            while (!isLeafNode(i)) {
                int largestNodeIndex = i;
                // Swap node with node with larger child
                if (hasLeftChild(i) && compare(data[getLeftChildIndex(i)], data[largestNodeIndex])) {
                    largestNodeIndex = getLeftChildIndex(i);
                }
                if (hasRightChild(i) && compare(data[getRightChildIndex(i)], data[largestNodeIndex])) {
                    largestNodeIndex = getRightChildIndex(i);
                }

                if (largestNodeIndex != i) {
                    swap(largestNodeIndex, i);
                    i = largestNodeIndex;
                } else
                    break;
            }
            i--;
        }


    }

    protected abstract boolean compare(Comparable<T> child, Comparable<T> parent);

    public void push(T item) {
        if (this.size == this.data.length) {
            // Have to double size of data array
            Comparable<T>[] temp = (Comparable<T>[]) new Comparable[this.data.length * 2];
            System.arraycopy(this.data, 0, temp, 0, this.size);
            this.data = temp;
        }
        this.data[this.size++] = item;
        int i = size - 1;
        while (i > 0 && compare(this.data[i], data[getParentIndex(i)])) {
            swap(i, getParentIndex(i));

            i = getParentIndex(i);
        }
    }

    public Comparable<T> pop() {
        if (size == 0) return null;
        Comparable<T> poppedValue = this.data[0];
        this.data[0] = this.data[--this.size];
        
        int i = 0;
        while (!isLeafNode(i)) {
            int largestNodeIndex = i;
            // Swap node with node with larger child
            if (hasLeftChild(i) && compare(data[getLeftChildIndex(i)], data[largestNodeIndex])) {
                largestNodeIndex = getLeftChildIndex(i);
            }
            if (hasRightChild(i) && compare(data[getRightChildIndex(i)], data[largestNodeIndex])) {
                largestNodeIndex = getRightChildIndex(i);
            }

            if (largestNodeIndex != i) {
                swap(largestNodeIndex, i);
                i = largestNodeIndex;
            } else
                break;
        }

        return poppedValue;
    }

    public Comparable<T> peek() {
        if (size == 0) 
            return null;
        return this.data[0];
    }

    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private void swap(int i1, int i2) {
        Comparable<T> temp = data[i1];
        data[i1] = data[i2];
        data[i2] = temp;
    }
    
    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean isLeafNode(int index) {
        return !(hasLeftChild(index) || hasRightChild(index));
    }

    /*
     * 
     * Functions used for the toString
     * Do not change them but feel free to use them
     * 
     */

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(0, "", true, sb); // Start from the root
        return sb.toString();
    }

    private void buildString(int index, String prefix, boolean isTail, StringBuilder sb) {
        if (index < size) {
            String linePrefix = isTail ? "└── " : "┌── ";
            if (getRightChildIndex(index) < size) { // Check if there is a right child
                buildString(getRightChildIndex(index), prefix + (isTail ? "|   " : "    "), false, sb);
            }
            sb.append(prefix).append(linePrefix).append(data[index]).append("\n");
            if (getLeftChildIndex(index) < size) { // Check if there is a left child
                buildString(getLeftChildIndex(index), prefix + (isTail ? "    " : "│   "), true, sb);
            }
        }
    }

}
