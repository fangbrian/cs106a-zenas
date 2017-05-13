/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Instance Variables */
	private String[] stringParts = new String[NDECADES + 1];
	private Integer[] intParts = new Integer[NDECADES + 1];

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		stringParts = line.split(" ");
		for(int i = 1; i < NDECADES + 1; i++) {
			intParts[i] = Integer.parseInt(stringParts[i]);
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return stringParts[0];
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return intParts[decade + 1];
	}
	
/* Method: getAllRanks() */
/**
 * Returns all of the ranks associated with a name, in chronological order.
 */
	public String getAllRanks() {
		String allRanks = new String();
		for(int i = 1; i < 12; i++) {
			if(i == 11)
				allRanks += Integer.toString(intParts[i]);
			else 
				allRanks += Integer.toString(intParts[i]) + " ";
		}
		return allRanks;
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String nameAndRanks = stringParts[0] + " [";
		for(int i = 1; i < NDECADES + 1; i++){
			if(i == NDECADES)
				nameAndRanks += stringParts[i] + "]";
			else
				nameAndRanks += stringParts[i] + " ";
		}
		return nameAndRanks;
	}
}

