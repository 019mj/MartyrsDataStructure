package Trees;

public class AVLTree extends BinarySearchTree {

	public AVLTree() {
	}

	// Perform a single rotation with the left child.
	private TreeNode rotateWithLeftChild(TreeNode parent) {
		TreeNode child = parent.getLeft();
		if (child == null) {
			return parent;
		}

		parent.setLeft(child.getRight());
		child.setRight(parent);

		return child;
	}

	// Perform a single rotation with the right child.
	private TreeNode rotateWithRightChild(TreeNode parent) {
		TreeNode child = parent.getRight();
		if (child == null) {
			return parent;
		}

		parent.setRight(child.getLeft());
		child.setLeft(parent);

		return child;
	}

	// Perform a double rotation: first with the left child and then with the right child.
	private TreeNode doubleWithLeftChild(TreeNode parent) {
		parent.setLeft(rotateWithRightChild(parent.getLeft()));
		return rotateWithLeftChild(parent);
	}

	// Perform a double rotation: first with the right child and then with the left child.
	private TreeNode doubleWithRightChild(TreeNode parent) {
		parent.setRight(rotateWithLeftChild(parent.getRight()));
		return rotateWithRightChild(parent);
	}

	// Get the balance factor of a node (difference in height between left and right subtrees).
	private int getBalanceFactor(TreeNode node) {
		return height(node.getLeft()) - height(node.getRight());
	}

	// Rebalance the tree after an insertion or deletion operation.
	protected TreeNode rebalance(TreeNode node) {
		int balance = getBalanceFactor(node);
		if (balance > 1) {
			if (getBalanceFactor(node.getLeft()) > 0)
				node = rotateWithLeftChild(node);
			else
				node = doubleWithLeftChild(node);
		} else if (balance < -1) {
			if (getBalanceFactor(node.getRight()) < 0)
				node = rotateWithRightChild(node);
			else
				node = doubleWithRightChild(node);
		}
		return node;
	}

	// Add an element to the AVL tree.
	public void add(Object element) {
		if (isEmpty())
			root = new TreeNode(element);
		else {
			TreeNode node = new TreeNode(element);
			add(node, root);

			root = rebalance(root);
		}
	}

	// Helper method to add a node to the AVL tree.
	protected void add(TreeNode node, TreeNode root) {

		if (root.compareTo(node) == 1 || root.compareTo(node) == 0) {
			if (root.hasLeft()) {
				add(node, root.getLeft());
				root.setLeft(rebalance(root.getLeft()));
			} else {
				root.setLeft(node);
			}
		} else if (root.hasRight()) {
			add(node, root.getRight());
			root.setRight(rebalance(root.getRight()));
		} else {
			root.setRight(node);
		}

	}	
	
	// Remove an element from the AVL tree.
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
			else
				if (isLeft)
					parent.setLeft(current.getLeft());
				else
					parent.setRight(current.getLeft());
		else if (current.hasRight() && !current.hasLeft())
			if (current == root)
				root = current.getRight();
			else
				if (isLeft)
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
		
		if (current != null)
			root = rebalance(root);
		
		return current;
	}
}
