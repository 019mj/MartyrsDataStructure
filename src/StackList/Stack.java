package StackList;

public class Stack {
	private int size; // Tracks the size of the stack
	private Node front; // Points to the top/front node of the stack

	public Stack() {
		size = 0; // Initialize the size to 0
		front = null; // Initialize the front to null, indicating an empty stack
	}

	public void push(Object element) {
		// Create a new node and make it the new front of the stack
		Node node = new Node(element, front);
		front = node;
		size++;
	}

	public boolean isEmpty() {
		return (size == 0); // Returns true if the stack is empty, false otherwise
	}

	public Object pop() {
		if (isEmpty())
			return null; // If the stack is empty, return null

		Node top = front; 
		front = front.getNext(); 
		size--; 
		return top.getElement(); // Return the element of the top node
	}

	public Object peek() {
		if (isEmpty())
			return null;
		return front.getElement(); // Return the element of the top node without removing it
	}

	public int size() {
		return size; // Return the size of the stack
	}
}
