package Trees;

import java.io.PrintWriter;

import Proj3.Martyr;
import QueueList.Queue;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AVLNames extends AVLTree {

	public AVLNames() {
	}

	// Override the add method to work with NameNode objects.
	public void add(Object element) {
		if (isEmpty())
			root = new NameNode(element);
		else {
			NameNode node = new NameNode(element);
			add(node, root);

			root = rebalance(root);
		}
	}

	// Override the remove method to work with NameNode objects.
	public TreeNode remove(Object element) {
		TreeNode current = root;
		TreeNode parent = root;

		boolean isLeft = true;

		while (current != null && !((NameNode) current).equals(element)) {
			parent = current;
			int compare = ((Martyr) current.getElement()).getName().compareToIgnoreCase(((Martyr) element).getName());

			if (compare > 0) {
				current = current.getLeft();
				isLeft = true;
			} else if (compare < 0) {
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
			remove((Martyr) min.getElement());
			current.setElement(min.getElement());
			if (currTemp != null)
				root = rebalance(root);
			return currTemp;
		}

		if (current != null)
			root = rebalance(root);

		return current;
	}

	// Find a node with a specific element.
	public TreeNode find(Object element) {
		return find(element, root);
	}

	private TreeNode find(Object element, TreeNode root) {
		if (root != null) {
			int comp = ((Martyr) root.getElement()).getName().compareTo(((Martyr) element).getName());
			if (comp == 0)
				return root;
			else if (comp > 0)
				return find(element, root.getLeft());
			else if (comp < 0)
				return find(element, root.getRight());
		}

		return null;
	}

	// Check if the tree contains a specific element.
	public boolean contains(Object element) {
		return contains(element, root);
	}

	private boolean contains(Object element, TreeNode root) {
		if (root != null) {
			int comp = ((Martyr) root.getElement()).getName().compareTo(((Martyr) element).getName());
			if (comp == 0)
				return true;
			else if (comp == 1)
				return contains(element, root.getLeft());
			else if (comp == -1)
				return contains(element, root.getRight());
		}

		return false;
	}

	// Print the elements of the tree in sorted order (in-order traversal).
	public void printBST() {
		printBSTHelper(root, 0);
	}

	private void printBSTHelper(TreeNode node, int level) {
		if (node == null)
			return;

		printBSTHelper(node.getRight(), level + 1);

		for (int i = 0; i < level; i++)
			System.out.print("   ");

		System.out.println(((Martyr) node.getElement()).getName());

		printBSTHelper(node.getLeft(), level + 1);
	}

	// Perform an in-order traversal of the tree and add the elements to an observable list.
	public void inOrder(ObservableList<Martyr> observableList) {
		inOrder(root, observableList);
		System.out.println();
	}

	private void inOrder(TreeNode root, ObservableList<Martyr> observableList) {
		if (root != null) {

			if (root.hasLeft())
				inOrder(root.getLeft(), observableList);

			observableList.add((Martyr) root.getElement());

			if (root.hasRight())
				inOrder(root.getRight(), observableList);
		}
	}

	// Perform a level-order traversal of the tree and display the elements.
	public void levelOrder(TextArea textArea, TextField textField) {
		int count = 0;
		if (!isEmpty()) {
			Queue queue = new Queue();
			queue.enQueue(root);

			int j = 0;
			while (!queue.isEmpty()) {
				int levelSize = queue.size();
				textArea.appendText("Level " + ++j + ":\t\t||  ");
				count += levelSize;
				for (int i = 0; i < levelSize; i++) {
					TreeNode currentNode = (TreeNode) queue.deQueue();
					textArea.appendText(((Martyr) currentNode.getElement()).getName() + "  ||  ");
					if (currentNode.hasLeft()) {
						queue.enQueue(currentNode.getLeft());
					}
					if (currentNode.hasRight()) {
						queue.enQueue(currentNode.getRight());
					}
				}

				textArea.appendText("\n");
			}
		}

		textField.setText(count + "");
	}

	// Print the elements of the tree to a file.
	public void printToFile(PrintWriter pw, String location) {
		printToFile(root, pw, location);
	}

	private void printToFile(TreeNode root, PrintWriter pw, String location) {
		if (root != null) {

			if (root.hasLeft())
				printToFile(root.getLeft(), pw, location);

			Martyr martyr = (Martyr) root.getElement();
			pw.println(martyr.fileInfo(location));
			if (root.hasRight())
				printToFile(root.getRight(), pw, location);
		}
	}
}
