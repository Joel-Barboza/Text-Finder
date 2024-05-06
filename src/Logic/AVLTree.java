package Logic;

import java.io.File;
import java.util.ArrayList;

class AVLNode {
    String key;
    int height;
    AVLNode left, right;

    ArrayList<ArrayList> occurrencesList = new ArrayList<>();

    AVLNode(String key) {
        ArrayList<File> fileOfOccurrence = new ArrayList<>();
        ArrayList<Integer> occurrencePosition = new ArrayList<>();
        occurrencesList.add(fileOfOccurrence);
        occurrencesList.add(occurrencePosition);
        this.key = key;
        this.height = 1;
        this.left = this.right = null;
    }
}

public class AVLTree {
    private AVLNode root;

    // Get height of node
    private int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Get balance factor of node
    private int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Right rotate subtree rooted with y
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a key into the tree
    public void insert(String key, File file) {
        root = insertRecursive(root, key, file);
    }

    // Recursive function to insert a key into the tree
    private AVLNode insertRecursive(AVLNode node, String key, File file) {

        // Perform normal BST insertion
        if (node == null) {
            AVLNode newNode = new AVLNode(key);
            newNode.occurrencesList.get(0).add(file);
            newNode.occurrencesList.get(1).add(1);
            return newNode;
        }


        if (key.compareTo(node.key) < 0) {
            node.left = insertRecursive(node.left, key, file);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertRecursive(node.right, key, file);
        } else {
            ArrayList<File> fileList = node.occurrencesList.get(0);
            ArrayList<Integer> positionList = node.occurrencesList.get(1);




            if (fileList.contains(file)) {
                int lastIndexOfFile = fileList.lastIndexOf(file);
                positionList.add(positionList.get(lastIndexOfFile) + 1);
                fileList.add(file);
            } else {
                positionList.add(1);
                fileList.add(file);

            }

            return node;
        }

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);

        // If node becomes unbalanced, perform rotations
        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
}
