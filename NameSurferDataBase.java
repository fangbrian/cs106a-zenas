/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import acm.util.*;
import java.util.*;
import java.util.HashMap.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {

/* Instance Variables */
	private Map<String,String> directory = new HashMap<String,String>();
	private NameSurferEntry entry;
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		try {
			// Read in file, line by line
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				// Create new instance of NameSurferEntry based on line read
				entry = new NameSurferEntry(line);
				// Enter entry into database via Hashmap, with name being the key and list of ranks being the value
				directory.put(entry.getName(), entry.getAllRanks());
			}
			rd.close();
		}
		catch(IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		if(directory.containsKey(name)) {
			entry = new NameSurferEntry(name + " " + directory.get(name));
			return entry;
		}
		else
			return null;
	}
}

