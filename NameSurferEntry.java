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
private String name;
private String data;
private ArrayList<String> dataSeparatedByDecade = new ArrayList<String>();

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
public NameSurferEntry(String line) {
	// Splits read line into 2 parts, name and data.
	// Puts name found into name variable and puts the String 
	// data into data variable.
	int firstSpace = line.indexOf(" ");
	name = line.substring(0, firstSpace);
	data = line.substring(firstSpace);
	// Splits data from String data and puts each piece into
	// different index of arraylist dataSeparatedByDecade
	for(int i = 0; i < NDECADES; i++) {
		String[] data2 = data.split(" ");
		dataSeparatedByDecade.add(data2[i]);
	}
}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
public String getName() {
	return name;
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
	return Integer.parseInt(dataSeparatedByDecade.get(decade));
}
	
/* Method: getAllRanks() */
/**
 * Returns all of the ranks associated with a name, in chronological order.
 */
public String getAllRanks() {
	return data;
}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
public String toString() {
	return (name + " " + data);
}
}

