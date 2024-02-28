public class RecursiveArray {
    public Integer[] array;

    public RecursiveArray() {
        array = new Integer[0];
    }

    public RecursiveArray(String input) {
        if (input.isEmpty()) {
            array = new Integer[0];
        } else {
            String[] split_ints = input.split(",");
            array = new Integer[split_ints.length];
            parseInts(split_ints, 0);
        }
    }

    private void parseInts(String[] ints, int index) {
        if (index < ints.length) {
            array[index] = Integer.parseInt(ints[index]);
            parseInts(ints, index + 1);
        }
    }

    @Override
    public String toString() {
        if (array.length == 0) {
            return "Empty Array";
        } else {
            return "[" + parseChars(0) + "]";
        }
    }

    private String parseChars(int index) {
        if (index < array.length - 1) {
            return array[index] + "," + parseChars(index + 1);
        } else {
            return String.valueOf(array[index]);
        }
    }

    public void append(Integer value) {
        Integer[] newArray = new Integer[array.length + 1];
        appendHelper(newArray, value, 0);
        array = newArray;
    }

    private void appendHelper(Integer[] newArray, Integer value, int index) {
        if (index < array.length) {
            newArray[index] = array[index];
            appendHelper(newArray, value, index + 1);
        } else {
            newArray[index] = value;
        }
    }

    public void prepend(Integer value) {
        Integer[] newArray = new Integer[array.length + 1];
        prependHelper(newArray, value, 0);
        array = newArray;
    }

    private void prependHelper(Integer[] newArray, Integer value, int index) {
        if (index < array.length) {
            newArray[index + 1] = array[index];
            prependHelper(newArray, value, index + 1);
        } else {
            newArray[0] = value;
        }
    }

    public boolean contains(Integer value) {
        return containsHelper(value, 0);
    }

    private boolean containsHelper(Integer value, int index) {
        if (index < array.length) {
            if (array[index] == value) {
                return true;
            } else {
                return containsHelper(value, index + 1);
            }
        } else {
            return false;
        }
    }

    public boolean isAscending() {
        return isAscendingHelper(0);
    }

    private boolean isAscendingHelper(int index) {
        if (index < array.length - 1) {
            if (array[index] > array[index + 1]) {
                return false;
            } else {
                return isAscendingHelper(index + 1);
            }
        } else {
            return true;
        }
    }

    public boolean isDescending() {
        return isDescendingHelper(0);
    }

    private boolean isDescendingHelper(int index) {
        if (index < array.length - 1) {
            if (array[index] < array[index + 1]) {
                return false;
            } else {
                return isDescendingHelper(index + 1);
            }
        } else {
            return true;
        }
    }

    public void sortAscending() {
        sortAscendingHelper(0);
    }

    private void sortAscendingHelper(int index) {
        if (index < array.length) {
            int minIndex = findMinIndex(index, index);
            if (minIndex != index) {
                swap(index, minIndex);
            }
            sortAscendingHelper(index + 1);
        }
    }

    private int findMinIndex(int currentIndex, int minIndex) {
        if (currentIndex < array.length) {
            if (array[currentIndex] < array[minIndex]) {
                return findMinIndex(currentIndex + 1, currentIndex);
            } else {
                return findMinIndex(currentIndex + 1, minIndex);
            }
        }
        return minIndex;
    }

    private void swap(int index1, int index2) {
        int tempInt = array[index1];
        array[index1] = array[index2];
        array[index2] = tempInt;
    }

    public void sortDescending() {
        sortDescendingHelper(0);
    }

    private void sortDescendingHelper(int index) {
        if (index < array.length) {
            int maxIndex = findMaxIndex(index, index);
            if (maxIndex != index) {
                swap(index, maxIndex);
            }
            sortDescendingHelper(index + 1);
        }
    }

    private int findMaxIndex(int currentIndex, int maxIndex) {
        if (currentIndex < array.length) {
            if (array[maxIndex] > array[currentIndex]) {
                return findMaxIndex(currentIndex + 1, maxIndex);
            } else {
                return findMaxIndex(currentIndex + 1, currentIndex);
            }
        }
        return maxIndex;
    }
}
