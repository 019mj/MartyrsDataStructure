package Proj3;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Martyr implements Comparable<Martyr> {
	private String name; // Represents the name of the martyr.
	private String age; // Represents the age of the martyr.
	private GregorianCalendar date; // Represents the date associated with the martyr.
	private char gender = 'M'; // Represents the gender of the martyr.

	public Martyr() {
		// Default constructor.
	}

	public Martyr(String name, String age, int day, int month, int year, char gender) {
		if (name.trim().isEmpty())
			this.name = "Unknown";
		else
			this.name = name;

		try {
			Integer.parseInt(age.trim());
			this.age = age;
		} catch (Exception e) {
			this.age = "Unknown"; // Sets the age to "Unknown" if it cannot be parsed as an integer.
		}
		setDate(day, month, year); // Sets the date using the provided day, month, and year.

		if (gender == 'M' || gender == 'F')
			this.gender = gender;
		else
			this.gender = '?';
	}

	public String getName() {
		return name; // Returns the name of the martyr.
	}

	public void setName(String name) {
		this.name = name; // Sets the name of the martyr.
	}

	public String getAge() {
		return age; // Returns the age of the martyr.
	}

	public void setAge(String age) {
		this.age = age; // Sets the age of the martyr.
	}

	public GregorianCalendar getDate() {
		return date; // Returns the date associated with the martyr.
	}

	public void setDate(int day, int month, int year) {
		date = new GregorianCalendar(year, month - 1, day); // Sets the date using the provided day, month, and year.
	}

	public char getGender() {
		return gender; // Returns the gender of the martyr.
	}

	public void setGender(char gender) {
		this.gender = gender; // Sets the gender of the martyr.
	}

	@Override
	public int compareTo(Martyr o) {
		return this.date.compareTo(o.date); // Compares the martyr's date with another martyr's date.
	}

	public String toString() {
		int month = date.get(Calendar.MONTH) + 1, day = date.get(Calendar.DATE), year = date.get(Calendar.YEAR);
		return name + "," + age + "," + month + "/" + day + "/" + year + "," + gender; 
		// Returns a string representation of the martyr in the format "name,age,month/day/year,gender".
	}
	
	public String fileInfo(String location) {
		int month = date.get(Calendar.MONTH) + 1, day = date.get(Calendar.DATE), year = date.get(Calendar.YEAR);
		return name + "," + age + "," + location + "," + month + "/" + day + "/" + year + "," + gender; 
		// Returns a string representation of the martyr in the format "name,age,location,month/day/year,gender".
	}


	public boolean equals(Martyr martyr) {
		return getName().equals(martyr.getName()); // Checks if the martyr's name is equal to another martyr's name.
	}

}
