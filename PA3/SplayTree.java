public class SplayTree {
    public Node root;
    /*
     * The functions below this line was given
     */

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
        return (root == null ? "Empty Tree" : "{" + toStringOneLine(root) + "}");
    }

    public String toStringOneLine(Node node) {
        return node.toString()
                + (node.left == null ? "{}" : "{" + toStringOneLine(node.left) + "}")
                + (node.right == null ? "{}" : "{" + toStringOneLine(node.right) + "}");
    }

    public SplayTree() {
        root = null;
    }

    /*
     * The functions above this line was given
     */

    public SplayTree(String input) {
        if (input.equals("Empty Tree"))
            root = null;
        else {
            root = SplayTreeHelper(input, null);
        }
    }
    private Node parseNodeString(String input) {
        // Assume that the input is in the format <studentNumber>:<mark>
        String[] splitRootString = input.split(":");

        int studentNumber = Integer.parseInt(splitRootString[0]);
        if (splitRootString[1].equals("null"))
            return new Node(studentNumber, null);
        else {
            return new Node(studentNumber, Integer.valueOf(splitRootString[1]));
        }
    }
    private Node SplayTreeHelper(String input, Node node) {
        // Assume that the input is in the format {[node]{left}{right}}
        if (input.equals("{}")) {
            // System.out.println("This is empty");
            return null;
        }
        // else
        //     System.out.println("This isn't empty: " + input);

        // Remove leading and trailing curly braces -- [node]{left}{right}
        input = input.substring(1, input.length() - 1);

        // System.out.println(input);
        // To parse the Node - delete the percentage character at the end and the 'u' at the start
        String nodeString = input.substring(2, input.indexOf(']') - 1);
        Node newNode = parseNodeString(nodeString);

        // Now parse left and right nodes
        String leftSubTree = input.substring(input.indexOf('{'), findClosingBracket(input, input.indexOf('{')) + 1);
        input = input.substring(findClosingBracket(input, input.indexOf('{')) + 1, input.length());
        String rightString = input;
        // System.out.println("Left: " + leftSubTree);
        // System.out.println("Right: " + rightString);

        
        newNode.left = SplayTreeHelper(leftSubTree, newNode);
        newNode.right = SplayTreeHelper(rightString, newNode);

        return newNode;
    }
    public int findClosingBracket(String str, int openingBracketPosition) {
        int bracketCounter = 1;
        for (int i = openingBracketPosition + 1; i < str.length(); i++) {
            if (str.charAt(i) == '{') {
                bracketCounter++;
            } else if (str.charAt(i) == '}') {
                bracketCounter--;
                if (bracketCounter == 0) {
                    return i;
                }
            }
        }
        return -1; // This should never happen though because every opening bracket has a closing bracket
    }

    public Node access(int studentNumber) {
        return access(studentNumber, null);
    }
    public Node access(int studentNumber, Integer mark) {
        return null;
    }
    public Node remove(int studentNumber) {
        return null;
    }

    public String sortByStudentNumber() {
        if (root == null) return "Empty Tree";
        
        return traversal(root) + '\n';
    }
    private String traversal(Node node) {
        if (node != null) {
            String leftString = traversal(node.left);
            String rightString = traversal(node.right);

            return leftString + node.toString() + rightString;
        } else
            return "";
    }

    public String sortByMark() {
        return "";
    }

}
