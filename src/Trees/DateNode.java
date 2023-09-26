package Trees;

import Proj3.DateStack;

public class DateNode extends TreeNode {
	public DateNode(Object element) {
		super(element);
	}

	/*
	 * Compare the DateNode with another TreeNode based on their element's date values.
	 * return 1 if the date of the current DateNode's element is later than the other TreeNode's element's date,
	 *         -1 if it is earlier, 0 if they are equal.
	 */
	@Override
	public int compareTo(TreeNode o) {
		if (((DateStack) getElement()).getDate().compareTo(((DateStack) o.getElement()).getDate()) > 0)
			return 1;
		else if (((DateStack) getElement()).getDate().compareTo(((DateStack) o.getElement()).getDate()) < 0)
			return -1;
		return 0;
	}

	/*
	 * Check if the DateNode's element is equal to the provided object.
	 * return True if the dates of the DateNode's element and the provided object are equal, False otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (((DateStack) getElement()).getDate().equals(((DateStack) obj).getDate()))
			return true;
		return false;
	}
}
