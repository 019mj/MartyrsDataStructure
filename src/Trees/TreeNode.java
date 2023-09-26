package Trees;

public class TreeNode implements Comparable<TreeNode> {

	private Object element;
	private TreeNode left;
	private TreeNode right;

	public TreeNode(Object element) {
		this(element, null, null);
	}

	public TreeNode(Object element, TreeNode left, TreeNode right) {
		this.element = element;
		this.left = left;
		this.right = right;
	}

	/*
	 * return The element stored in the TreeNode.
	 */
	public Object getElement() {
		return element;
	}

	/*
	 * Set the element stored in the TreeNode.
	 */
	public void setElement(Object element) {
		this.element = element;
	}

	/*
	 * return The left child of the TreeNode.
	 */
	public TreeNode getLeft() {
		return left;
	}

	/*
	 * Set the left child of the TreeNode.
	 */
	public void setLeft(TreeNode left) {
		this.left = left;
	}

	/*
	 * return The right child of the TreeNode.
	 */
	public TreeNode getRight() {
		return right;
	}

	/*
	 * Set the right child of the TreeNode.
	 */
	public void setRight(TreeNode right) {
		this.right = right;
	}

	/*
	 * return True if the TreeNode is a leaf node, False otherwise.
	 */
	public boolean isLeaf() {
		return !hasLeft() && !hasRight();
	}

	/*
	 * return True if the TreeNode has a left child, False otherwise.
	 */
	public boolean hasLeft() {
		return left != null;
	}

	/*
	 * return True if the TreeNode has a right child, False otherwise.
	 */
	public boolean hasRight() {
		return right != null;
	}

	/*
	 * Compare the TreeNode with another TreeNode based on their element values.
	 * return 1 if the element value of the current TreeNode is greater than the other TreeNode,
	 *         -1 if it is smaller, 0 if they are equal.
	 */
	@Override
	public int compareTo(TreeNode o) {
		if (((Integer) element).compareTo((Integer) o.getElement()) > 0)
			return 1;
		else if (((Integer) element).compareTo((Integer) o.getElement()) < 0)
			return -1;
		return 0;
	}

}
