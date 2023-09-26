package CircularDoubleLinkedList;


import java.io.File;
import java.io.PrintWriter;

import Proj3.LocationRecord;
import Proj3.Martyr;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CircularDoubleLinkedList {
	private Node front;
	private Node back;
	private int size = 0;

	public CircularDoubleLinkedList() {
	}

	// Add a new node with the given car brand and car object at the beginning of
	// the list.
	public void addFirst(String location, Martyr martyr) {
		LocationRecord locationRecord = new LocationRecord(location);
		if (martyr != null) {
			locationRecord.getAvlNames().add(martyr);
			locationRecord.getAvlDate().add(martyr);
		}
			
		Node node = new Node(locationRecord);
		if (isEmpty()) {
			front = back = node;
			front.setNext(back);
			back.setPrev(front);
		}
		else {
			node.setNext(front);
			front.setPrev(node);
			front = node;
			front.setPrev(back);
			back.setNext(front);
		}
		size++;
	}

	// Add a new node with the given CarBrand object at the beginning of the list.
	public void addFirst(LocationRecord locationRecord) {

		Node node = new Node(locationRecord);
		if (isEmpty()){
			front = back = node;
			front.setNext(back);
			back.setPrev(front);
		}
		else {
			node.setNext(front);
			front.setPrev(node);
			front = node;
			front.setPrev(back);
			back.setNext(front);
		}
		size++;
	}

	/*
	 * Returns the first element in the list.
	 */
	public Object getFirst() {
		if (isEmpty())
			return null;
		return front.getElement();
	}

	/*
	 * Append the given car brand and car object to the end of the list.
	 */
	public void addLast(String location, Martyr martyr) {
		LocationRecord locationRecord = new LocationRecord(location);
		if (martyr != null) {
			locationRecord.getAvlNames().add(martyr);
			locationRecord.getAvlDate().add(martyr);
		}
			
		Node node = new Node(locationRecord);

		if (isEmpty()) {
			front = back = node;
			front.setPrev(back);
			back.setNext(front);
		}
		else {
			back.setNext(node);
			node.setPrev(back);
			back = node;
			front.setPrev(back);
			back.setNext(front);
		}
		size++;
	}

	/*
	 * Append the given CarBrand object to the end of the list.
	 */
	public void addLast(LocationRecord locationRecord) {

		Node node = new Node(locationRecord);
		if (isEmpty()) {
			front = back = node;
			front.setPrev(back);
			back.setNext(front);
		}
		else {
			back.setNext(node);
			node.setPrev(back);
			back = node;
			front.setPrev(back);
			back.setNext(front);
		}
		size++;
	}

	/*
	 * Returns the last element in the list.
	 */
	public Object getLast() {
		if (isEmpty())
			return null;
		return back.getElement();
	}

	// Check if the list is empty.
	public boolean isEmpty() {
		return (size == 0);
	}

	// Returns the element at the specified position in the list.
	public Object get(int index) {
		if (isEmpty())
			return null;
		else if (index == 0)
			return getFirst();
		else if (index == size - 1)
			return getLast();
		else if (index > 0 && index < size - 1) {
			Node current = front;
			for (int i = 0; i < index; i++)
				current = current.getNext();
			return current.getElement();
		}
		return null;
	}

	/*
	 * Returns the number of elements in the list.
	 */
	public int size() {
		return this.size;
	}

	/*
	 * Removes the first element from the list.
	 */
	public boolean removeFirst() {
		if (isEmpty())
			return false;
		else if (size == 1)
			front = back = null;
		else {
			front = front.getNext();
			front.setPrev(back);
			back.setNext(front);
		}
		size--;
		return true;
	}

	/*
	 * Removes the last element from this list.
	 */
	public boolean removeLast() {
		if (isEmpty())
			return false;
		else if (size == 1)
			front = back = null;
		else {
			back = back.getPrev();
			front.setPrev(back);
			back.setNext(front);
		}
		size--;
		return true;
	}

	// Remove the element at the specified index from the list.
	public boolean remove(int index) {
		if (size == 0)
			return false;
		else if (index == 0)
			return removeFirst();
		else if (index == size - 1)
			return removeLast();
		else if (index > 0 && index < size - 1) {
			Node current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			current.getNext().getNext().setPrev(current);
			current.setNext(current.getNext().getNext());
			size--;
			return true;
		}

		return false;
	}

	// Print all the elements in the list.
	public void printAll() {
		Node current = front;
		while (current.getNext() != front) {
			System.out.println(((LocationRecord) current.getElement()).getLocation());
//			((LocationRecord) current.getElement()).getAvlNames().printBST();;
			current = current.getNext();
		}
		System.out.println();
	}


	// Clear the list, removing all elements.
	public void clear() {
		if (!isEmpty()) {
			front = back = null;
			size = 0;
		}
	}

	/*
	 * Checks if the list contains the specified element.
	 */
	boolean contains(Object o) {
		if (isEmpty())
			return false;
		Object object = find(o);
		return (object == null) ? false : true;
	}

	/*
	 * Returns the last index of the specified element in the list.
	 */
	public int lastIndexOf(Object o) {
		if (isEmpty())
			return -1;
		Node current = back;
		for (int i = size - 1; i >= 0; i++) {
			
			if (current.getElement() == o)
				return i;
			current = current.getPrev();
			
			if (current == back)
				break;

		}
		return -1;
	}

	/*
	 * Find the element that is in the list.
	 */
	public Object find(Object element) {
		if (isEmpty())
			return null;
		Node current = front;

		if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase((String) element))
			return current;

		current = current.getNext();

		while (current != front) {
			if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase((String) element))
				return current;
			current = current.getNext();
		}
		return null;
	}

	// Insert a new car brand and car object into the sorted list.
	public void insertSort(String location, Martyr martyr) {

		if (isEmpty())
			addFirst(location, martyr);
		else if (((LocationRecord) (front.getElement())).getLocation().compareToIgnoreCase(location) == 0) {
			if (martyr != null) {
				((LocationRecord) (front.getElement())).getAvlNames().add(martyr);
				((LocationRecord) (front.getElement())).getAvlDate().add(martyr);
			}
		} else if (((LocationRecord) (front.getElement())).getLocation().compareToIgnoreCase(location) > 0)
			addFirst(location, martyr);
		else if (((LocationRecord) (back.getElement())).getLocation().compareToIgnoreCase(location) == 0) {
			if (martyr != null) {
				((LocationRecord) (back.getElement())).getAvlNames().add(martyr);
				((LocationRecord) (back.getElement())).getAvlDate().add(martyr);
			}
		} else if (((LocationRecord) (back.getElement())).getLocation().compareToIgnoreCase(location) < 0) {
			addLast(location, martyr);
		} else {

			LocationRecord locationRecord = new LocationRecord(location);

			if (martyr != null) {
				locationRecord.getAvlNames().add(martyr);
				locationRecord.getAvlDate().add(martyr);
			}

			Node node = new Node(locationRecord);
			Node current = front;

			while (current.getNext() != front) {
				if (current.compareTo(node) == 0) {
					((LocationRecord) current.getElement()).getAvlNames().add(martyr);
					((LocationRecord) current.getElement()).getAvlDate().add(martyr);
					break;
				} else if (current.getNext().compareTo(node) > 0) {
					node.setNext(current.getNext());
					current.getNext().setPrev(node);
					current.setNext(node);
					node.setPrev(current);
					size++;
					break;
				}
				current = current.getNext();
			}

		}

	}

	// Print the elements of the list to a file.
	public void printToFile(File file) {
		try {
			PrintWriter pw = new PrintWriter(file);

			pw.println("Name,Age,location,Date of death,Gender");

			Node current = front;
			
			String location = ((LocationRecord) current.getElement()).getLocation();
			((LocationRecord) current.getElement()).getAvlNames().printToFile(pw, location);
			current = current.getNext();

			
			while (current != front) {
				location = ((LocationRecord) current.getElement()).getLocation();
				((LocationRecord) current.getElement()).getAvlNames().printToFile(pw, location);
				current = current.getNext();
			}

			pw.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No file has been chosen");
			alert.showAndWait();
		}

	}

	/*
	 * Checks if the list contains a car brand with the specified name.
	 */
	public boolean contains(String location) {
		if (isEmpty())
			return false;

		Node current = front;

		if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase((String) location))
			return true;

		current = current.getNext();

		while (current != front) {
			if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase((String) location))
				return true;
			current = current.getNext();
		}
		return false;
	}

	/*
	 * Remove the first occurrence of the specified car brand from the list.
	 */
	public boolean remove(String location) {
		if (size == 0)
			return false;
		else if (((LocationRecord) front.getElement()).getLocation().equalsIgnoreCase(location))
			return removeFirst();
		else if (((LocationRecord) back.getElement()).getLocation().equalsIgnoreCase(location))
			return removeLast();
		else {
			Node current = front;

			while (current.getNext() != front) {
				String locRec = ((LocationRecord) current.getNext().getElement()).getLocation();
				if (locRec.equalsIgnoreCase(location)) {
					location = locRec;
					current.getNext().getNext().setPrev(current);
					current.setNext(current.getNext().getNext());
					size--;
					return true;
				}
				current = current.getNext();
			}
		}
		return false;

	}

	/*
	 * Get the element with the specified brand name.
	 */
	public Object get(String location) {
		if (isEmpty())
			return null;

		Node current = front;

		if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase(location))
			return current.getElement();

		current = current.getNext();

		while (current != front) {
			if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase(location))
				return current.getElement();
			current = current.getNext();
		}
		return null;
	}

	/*
	 * Sort the list based on the specified car brand.
	 */
	public void sort(LocationRecord locRec) {

		remove(locRec.getLocation());

		if (isEmpty())
			addFirst(locRec);
		else if (((LocationRecord) (front.getElement())).compareTo(locRec) > 0)
			addFirst(locRec);
		else if (((LocationRecord) (back.getElement())).compareTo(locRec) < 0)
			addLast(locRec);
		else {
			Node node = new Node(locRec);
			Node current = front;
			while (current.getNext() != null) {
				if (current.compareTo(node) == 0) {
					current.setElement(locRec);
					break;
				} else if (current.getNext().compareTo(node) > 0) {
					node.setNext(current.getNext());
					current.getNext().setPrev(node);
					current.setNext(node);
					node.setPrev(current);
					size++;
					break;
				}
				current = current.getNext();
			}
		}

	}

	// get the specific node that has a specific car brand
	public Node findNode(String location) {
		if (isEmpty())
			return null;
		else {
			Node current = front;

			if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase(location))
				return current;

			current = current.getNext();

			while (current != front) {
				if (((LocationRecord) current.getElement()).getLocation().equalsIgnoreCase(location))
					return current;
				current = current.getNext();

			}
		}
		return null;
	}

	
	
	
}
