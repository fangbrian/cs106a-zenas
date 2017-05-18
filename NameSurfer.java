/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Instance Variables */
	private JTextField tf;
	private JButton graph;
	private JButton clear;
	private NameSurferDataBase repo;
	private NameSurferGraph guiGraph;
	
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		// Initializing interactors and creating NameSurferGraph instance
		guiGraph = new NameSurferGraph();
		add(guiGraph);
		add(new JLabel("Name "), SOUTH);
	    tf = new JTextField(TEXT_FIELD_SIZE);
	    tf.addActionListener(this);
	    add(tf, SOUTH);
		add(graph = new JButton("Graph"), SOUTH);
		add(clear = new JButton("Clear"), SOUTH);
	    addActionListeners();
	    // Creating new instance of NameSurferDatabase with file read-in
	    repo = new NameSurferDataBase(NAMES_DATA_FILE);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tf) {
			// Draws graph of name entered into textfield:
			// Add entry to arraylist and then pull data from arraylist to be displayed
			guiGraph.addEntry(repo.findEntry(tf.getText()));
			guiGraph.update();
		}
		else if(e.getSource() == graph) {
			// Draws graph of name entered into textfield and user clicked graph:
			// Add entry to arraylist and then pull data from arraylist to be displayed
			guiGraph.addEntry(repo.findEntry(tf.getText()));
			guiGraph.update();
		}
		else if(e.getSource() == clear)	{
			// Clear arraylist of entries and update display to show nothing
			guiGraph.clear();
			guiGraph.update();
		}
	}
}
