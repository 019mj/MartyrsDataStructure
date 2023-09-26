package Proj3;

import Trees.AVLDate;
import Trees.AVLNames;

/*
 * A class to hold the location information which are location, an avl tree for names and an avl tree for dates
 */
public class LocationRecord implements Comparable<LocationRecord> {
	private String location; // Represents the location associated with the record.
	private AVLNames avlNames = new AVLNames(); // AVL tree to store names associated with the location.
	private AVLDate avlDate = new AVLDate(); // AVL tree to store dates associated with the location.

	public LocationRecord(String location) {
		this.location = location; // Constructor that sets the location for the record.
	}

	public String getLocation() {
		return location; // Returns the location associated with the record.
	}

	public void setLocation(String location) {
		this.location = location; // Sets the location for the record.
	}

	public AVLNames getAvlNames() {
		return avlNames; // Returns the AVL tree storing names associated with the location.
	}

	public void setAvlNames(AVLNames avlNames) {
		this.avlNames = avlNames; // Sets the AVL tree storing names associated with the location.
	}

	public AVLDate getAvlDate() {
		return avlDate; // Returns the AVL tree storing dates associated with the location.
	}

	public void setAvlDate(AVLDate avlDate) {
		this.avlDate = avlDate; // Sets the AVL tree storing dates associated with the location.
	}

	@Override
	public int compareTo(LocationRecord o) {
		if (this.location.compareToIgnoreCase(o.location) > 0)
			return 1;
		else if (this.location.compareToIgnoreCase(o.location) < 0)
			return -1;
		return 0;
	}

}
