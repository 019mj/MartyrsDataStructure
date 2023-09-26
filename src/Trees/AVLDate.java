package Trees;

import java.util.GregorianCalendar;

import Proj3.DateStack;
import Proj3.Martyr;
import StackList.Stack;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AVLDate extends AVLTree {
	
	// Constructor
	public AVLDate() {
		
	}
	
	// Add method
	public void add(Object element) {
		if (isEmpty()) {
			DateStack dateStack = new DateStack(((Martyr) element).getDate());
			dateStack.getStack().push(element);
			root = new DateNode(dateStack);
		} else {
			add(element, root);
			root = rebalance(root);
		}
	}
	
	private void add(Object element, TreeNode root) {
		// Compare the dates
		int compare = ((DateStack) root.getElement()).getDate().compareTo(((Martyr) element).getDate());
		
		if (compare == 0) {
			((DateStack) root.getElement()).getStack().push(element);
		} else if (compare > 0) {
			if (root.hasLeft()) {
				add(element, root.getLeft());
				root.setLeft(rebalance(root.getLeft()));
			} else {
				DateStack dateStack = new DateStack(((Martyr) element).getDate());
				dateStack.getStack().push(element);
				DateNode node = new DateNode(dateStack);
				root.setLeft(node);
			}
		} else if (compare < 0) {
			if (root.hasRight()) {
				add(element, root.getRight());
				root.setRight(rebalance(root.getRight()));
			} else {
				DateStack dateStack = new DateStack(((Martyr) element).getDate());
				dateStack.getStack().push(element);
				DateNode node = new DateNode(dateStack);
				root.setRight(node);
			}
		}
	}
	
	// Delete Martyr method
	public Object deleteMartyr(Object element) {
		DateStack dateStack = (DateStack) find(element).getElement();
		return dateStack.removeMartyr((Martyr) element);
	}
	
	// Find method
	@Override
	public TreeNode find(Object element) {
		return find(element, root);
	}
	
	private TreeNode find(Object element, TreeNode root) {
		if (root != null) {
			int comp = ((DateStack) root.getElement()).getDate().compareTo(((Martyr) element).getDate());
			if (comp == 0)
				return root;
			else if (comp == 1)
				return find(element, root.getLeft());
			else if (comp == -1)
				return find(element, root.getRight());
		}
		return null;
	}
	
	// Print Back method
	public void printBack(TextArea ta, TextField tf) {
		if (root != null) {
			DateStack ds = ((DateStack) root.getElement());
			ds = printBack(root, ta, ds);
			GregorianCalendar gc = ds.getDate();
			String[] tkz = gc.getTime().toGMTString().split(" ");
			String dateString = Integer.parseInt(tkz[0]) + 1 + " " + tkz[1] + " " + tkz[2];
			tf.setText(dateString);
		}
	}
	
	private DateStack printBack(TreeNode root, TextArea ta, DateStack ds) {
		if (root != null) {
			if (root.hasRight())
				ds = printBack(root.getRight(), ta, ds);
			if (((DateStack) root.getElement()).getStack().size() >= ds.getStack().size())
				ds = (DateStack) root.getElement();
			((DateStack) root.getElement()).printTA(ta);
			if (root.hasLeft())
				ds = printBack(root.getLeft(), ta, ds);
		}
		return ds;
	}
	
	
	// extra
	public GregorianCalendar findLocWithMaxPersons() {
		if (root == null)
			return null;
		TreeNode current = root;
		while (current.getLeft() != null) {
			current = current.getLeft();
		}
		GregorianCalendar maxCity = ((DateStack) current.getElement()).getDate();
		int maxPersons = ((DateStack) current.getElement()).getStack().size();
		while (current != null) {
			if (((DateStack) current.getElement()).getStack().size() > maxPersons) {
				maxPersons = ((DateStack) current.getElement()).getStack().size();
				maxCity = ((DateStack) current.getElement()).getDate();
			}
			current = successor(current);
		}
		return maxCity;
	}
	
	private TreeNode successor(TreeNode node) {
		if (node.getRight() != null) {
			TreeNode successor = node.getRight();
			while (successor.getLeft() != null) {
				successor = successor.getLeft();
			}
			return successor;
		} else {
			TreeNode successor = null;
			TreeNode current = root;
			while (current != null) {
				if (((DateStack) node.getElement()).getDate().compareTo(((DateStack) current.getElement()).getDate()) < 0) {
					successor = current;
					current = current.getLeft();
				} else if (((DateStack) node.getElement()).getDate().compareTo(((DateStack) current.getElement()).getDate()) > 0) {
					current = current.getRight();
				} else {
					break;
				}
			}
			return successor;
		}
	}
}
