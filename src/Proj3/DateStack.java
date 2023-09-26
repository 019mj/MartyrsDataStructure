package Proj3;

import java.util.GregorianCalendar;

import StackList.Stack;
import javafx.scene.control.TextArea;

public class DateStack {
	private GregorianCalendar date; // Represents the date associated with the stack.
	private Stack stack = new Stack(); // Stack to store Martyr objects.

	public DateStack(GregorianCalendar date) {
		setDate(date); // Constructor that sets the date for the stack.
	}

	public GregorianCalendar getDate() {
		return date; // Returns the date associated with the stack.
	}

	public void setDate(GregorianCalendar date) {
		this.date = date; // Sets the date for the stack.
	}

	public Stack getStack() {
		return stack; // Returns the stack of Martyr objects.
	}

	public void setStack(Stack stack) {
		this.stack = stack; // Sets the stack of Martyr objects.
	}

	// remove a specific martyr from the stack
	public Martyr removeMartyr(Martyr martyr) {
		Stack temp = new Stack(); // Temporary stack to store popped items.
		Martyr removedMartyr = null;
		while (!stack.isEmpty()) {
			if (stack.peek() == martyr) {
				removedMartyr = (Martyr) stack.pop();
				break;
			}
			temp.push(stack.pop());
		}

		while (!temp.isEmpty())
			stack.push(temp.pop()); // Restores the items back to the original stack.

		return removedMartyr; // Returns the removed Martyr object.
	}

	public void printTA(TextArea ta) {

		String[] tkz = date.getTime().toGMTString().split(" "); // Splits the GMT representation of the date.
		String dateString = (Integer.parseInt(tkz[0]) + 1) + " " + tkz[1] + " " + tkz[2]; // Constructs the formatted
																							// date string.

		ta.appendText(dateString + " :\n");

		if (stack.isEmpty()) {
			ta.appendText("No Data\n");
			return;
		}

		Stack temp = new Stack();
		while (!stack.isEmpty()) {
			ta.appendText("\t" + ((Martyr) stack.peek()).toString() + "\n");
			temp.push(stack.pop());
		}

		ta.appendText("\n");

		while (!temp.isEmpty())
			stack.push(temp.pop()); // Restores the items back to the original stack.

	}
}
