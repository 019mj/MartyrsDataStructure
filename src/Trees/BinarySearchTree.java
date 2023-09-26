package Trees;

import QueueList.Queue;

public class BinarySearchTree {

    protected TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    // Check if the binary search tree is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Add an element to the binary search tree
    public void add(Object element) {
        if (isEmpty())
            root = new TreeNode(element);
        else
            add(element, root);
    }

    // Helper method to recursively add an element to the binary search tree
    private void add(Object element, TreeNode root) {
        TreeNode node = new TreeNode(element);
        if (root.compareTo(node) == 1 || root.compareTo(node) == 0)
            if (root.hasLeft())
                add(element, root.getLeft());
            else
                root.setLeft(node);
        else if (root.hasRight())
            add(element, root.getRight());
        else
            root.setRight(node);
    }

    // Find a node containing the specified element in the binary search tree
    public TreeNode find(Object element) {
        return find(element, root);
    }

    // Helper method to recursively find a node containing the specified element
    private TreeNode find(Object element, TreeNode root) {
        if (root != null) {
            int comp = ((Integer) root.getElement()).compareTo((Integer) element);
            if (comp == 0)
                return root;
            else if (comp == 1)
                return find(element, root.getLeft());
            else if (comp == -1)
                return find(element, root.getRight());
        }
        return null;
    }

    // Check if the binary search tree contains the specified element
    public boolean contains(Object element) {
        return contains(element, root);
    }

    // Helper method to recursively check if the binary search tree contains the specified element
    private boolean contains(Object element, TreeNode root) {
        if (root != null) {
            int comp = ((Integer) root.getElement()).compareTo((Integer) element);
            if (comp == 0)
                return true;
            else if (comp == 1)
                return contains(element, root.getLeft());
            else if (comp == -1)
                return contains(element, root.getRight());
        }
        return false;
    }

    // Find the node with the minimum value in the binary search tree
    public TreeNode findMin() {
        return findMin(root);
    }

    // Helper method to recursively find the node with the minimum value
    protected TreeNode findMin(TreeNode root) {
        if (root == null)
            return null;

        TreeNode curr = root;
        while (curr.hasLeft())
            curr = curr.getLeft();

        return curr;
    }

    // Find the node with the maximum value in the binary search tree
    public TreeNode findMax() {
        return findMax(root);
    }

    // Helper method to recursively find the node with the maximum value
    protected TreeNode findMax(TreeNode root) {
        if (root == null)
            return null;

        TreeNode curr = root;
        while (curr.hasRight())
            curr = curr.getRight();

        return curr;
    }

    // Perform an in-order traversal of the binary search tree
    public void InOrder() {
        InOrder(root);
        System.out.println();
    }

    // Helper method to recursively perform an in-order traversal
    private void InOrder(TreeNode root) {
        if (root != null) {
            if (root.hasLeft())
                InOrder(root.getLeft());

            System.out.print(root.getElement() + " ");

            if (root.hasRight())
                InOrder(root.getRight());
        }
    }

    // Calculate the height of the binary search tree
    public int height() {
        return height(root);
    }

    // Helper method to recursively calculate the height of the binary search tree
    protected int height(TreeNode root) {
        if (root == null)
            return 0;
        if (root.isLeaf())
            return 1;
        return 1 + Math.max(height(root.getLeft()), height(root.getRight()));
    }

    // Calculate the number of nodes in the binary search tree
    public int size() {
        return size(root);
    }

    // Helper method to recursively calculate the number of nodes in the binary search tree
    private int size(TreeNode root) {
        if (root == null)
            return 0;
        else if (root.isLeaf())
            return 1;
        return 1 + size(root.getLeft()) + size(root.getRight());
    }

    // Perform a pre-order traversal of the binary search tree
    public void PreOrder() {
        PreOrder(root);
        System.out.println();
    }

    // Helper method to recursively perform a pre-order traversal
    private void PreOrder(TreeNode root) {
        if (root != null) {
            System.out.print(root.getElement() + " ");

            if (root.hasLeft())
                PreOrder(root.getLeft());

            if (root.hasRight())
                PreOrder(root.getRight());
        }
    }

    // Perform a post-order traversal of the binary search tree
    public void PostOrder() {
        PostOrder(root);
        System.out.println();
    }

    // Helper method to recursively perform a post-order traversal
    private void PostOrder(TreeNode root) {
        if (root != null) {
            if (root.hasLeft())
                PostOrder(root.getLeft());

            if (root.hasRight())
                PostOrder(root.getRight());

            System.out.print(root.getElement() + " ");
        }
    }

    // Perform a level-order traversal of the binary search tree
    public void LevelOrder() {
        if (!isEmpty()) {
            Queue queue = new Queue();
            queue.enQueue(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode currentNode = (TreeNode) queue.deQueue();
                    System.out.print(currentNode.getElement() + " ");

                    if (currentNode.hasLeft()) {
                        queue.enQueue(currentNode.getLeft());
                    }
                    if (currentNode.hasRight()) {
                        queue.enQueue(currentNode.getRight());
                    }
                }

                System.out.println();
            }
        }
    }

    // Remove a node with the specified element from the binary search tree
    public TreeNode remove(Object element) {
        TreeNode current = root;
        TreeNode parent = root;

        boolean isLeft = true;

        while (current != null && !current.getElement().equals(element)) {
            parent = current;
            if (((Integer) current.getElement()).compareTo((Integer) element) > 0) {
                current = current.getLeft();
                isLeft = true;
            } else if (((Integer) current.getElement()).compareTo((Integer) element) < 0) {
                current = current.getRight();
                isLeft = false;
            }
        }

        if (current == null)
            return null;

        if (current.isLeaf())
            if (isLeft)
                parent.setLeft(null);
            else
                parent.setRight(null);
        else if (current.hasLeft() && !current.hasRight())
            if (current == root)
                root = current.getLeft();
            else if (isLeft)
                parent.setLeft(current.getLeft());
            else
                parent.setRight(current.getLeft());
        else if (current.hasRight() && !current.hasLeft())
            if (current == root)
                root = current.getRight();
            else if (isLeft)
                parent.setLeft(current.getRight());
            else
                parent.setRight(current.getRight());
        else if (current.hasLeft() && current.hasRight()) {
            TreeNode currTemp = new TreeNode(current.getElement());
            currTemp.setLeft(current.getLeft());
            currTemp.setRight(current.getRight());

            TreeNode min = findMin(current.getRight());
            remove(min.getElement());
            current.setElement(min.getElement());
            return currTemp;
        }
        return current;
    }

    // Print the binary search tree structure
    public void printBST() {
        printBSTHelper(root, 0);
    }

    // Helper method to recursively print the binary search tree structure
    private void printBSTHelper(TreeNode node, int level) {
        if (node == null) {
            return;
        }

        printBSTHelper(node.getRight(), level + 1);

        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }

        System.out.println(node.getElement());

        printBSTHelper(node.getLeft(), level + 1);
    }

    // Clear the binary search tree
    public void clear() {
        if (!isEmpty())
            root = null;
    }
}
