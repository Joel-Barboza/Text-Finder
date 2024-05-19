package Logic;

import Interface.App;

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



        return balance(node, key);
    }

    private AVLNode balance(AVLNode node, String key) {
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

    public ArrayList<ArrayList> search(String key) {
        return searchRecursive(root, key);
    }

    private ArrayList<ArrayList> searchRecursive(AVLNode root, String key) {
        if (root == null || root.key.compareTo(key) == 0) {
//            assert root != null;
            //System.out.println(root.key);
            if (root == null) {
                return null;
            } else {
                return root.occurrencesList;
            }
        }

        if (root.key.compareTo(key) < 0) {
            return searchRecursive(root.right, key);
        }

        return searchRecursive(root.left, key);
    }

    public void delete(String key) {
        root = deleteRecursive(root, key);
//        root = deleteNode(root, key);
    }

    private AVLNode deleteRecursive(AVLNode root, String key) {
        if (root == null) {
            return null;
        }

        if (key.compareTo(root.key) < 0) {
            root.left = deleteRecursive(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = deleteRecursive(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.key = minValue(root.right);
            root.right = deleteRecursive(root.right, root.key);
        }
        balance(root, key);
        return root;
    }

    private String minValue(AVLNode root) {
        String minValue = root.key;
        while (root.left != null) {
            minValue = root.left.key;
            root = root.left;
        }
        return minValue;
    }

    public void inOrder(File file) {
        inOrderRecursive(root, file);
    }

    private void inOrderRecursive(AVLNode root, File file) {
        if (root != null) {
            App.library.indexer.deindexFile(file, root);
            inOrderRecursive(root.left, file);
            //System.out.println(root.key);
            //root.occurrencesList.contains(file);
//            System.out.println(root.key);
//            System.out.println(file);
//            System.out.println(root);
            inOrderRecursive(root.right, file);
        }
    }

//    public AVLNode getRoot() {
//        return root;
//    }

    /* Given a non-empty binary search tree, return the
node with minimum key value found in that tree.
Note that the entire tree does not need to be
searched. */
    private AVLNode minValueNode(AVLNode node)
    {
        AVLNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    private AVLNode deleteNode(AVLNode root, String key)
    {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key.compareTo(root.key) < 0)
            root.left = deleteNode(root.left, key);

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key.compareTo(root.key) > 0)
            root.right = deleteNode(root.right, key);

            // if key is same as root's key, then this is the node
            // to be deleted
        else
        {

            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                AVLNode temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                AVLNode temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public void clear(){
        if (root != null){
            root = null;

        }
    }
}
