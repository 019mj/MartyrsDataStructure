package Trees;

import Proj3.Martyr;

public class NameNode extends TreeNode {

	public NameNode(Object element) {
		super(element);
	}

	/*
	 * Compare the NameNode with another TreeNode based on their element's name values.
	 * return 1 if the name of the current NameNode's element is greater than the other TreeNode's element's name,
	 *         -1 if it is smaller, 0 if they are equal.
	 */
	@Override
	public int compareTo(TreeNode o) {
		if (((Martyr) getElement()).getName().compareToIgnoreCase(((Martyr) o.getElement()).getName()) > 0)
			return 1;
		else if (((Martyr) getElement()).getName().compareToIgnoreCase(((Martyr) o.getElement()).getName()) < 0)
			return -1;
		return 0;
	}

	/*
	 * Check if the NameNode's element is equal to the provided object.
	 * return True if the names of the NameNode's element and the provided object are equal (case-insensitive), False otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (((Martyr) getElement()).getName().equalsIgnoreCase(((Martyr) obj).getName()))
			return true;
		return false;
	}

}
