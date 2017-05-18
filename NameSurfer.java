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
private JTextField textField;
private JButton graphButton;
private JButton clearButton;
private NameSurferDataBase nameSurferDataBase;
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
    textField = new JTextField(TEXT_FIELD_SIZE);
    textField.addActionListener(this);
    add(textField, SOUTH);
	add(graphButton = new JButton("Graph"), SOUTH);
	add(clearButton = new JButton("Clear"), SOUTH);
    addActionListeners();
    // Creating new instance of NameSurferDatabase with file read-in
    nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
public void actionPerformed(ActionEvent e) {
	if(e.getSource() == textField) {
		drawGraph();
	}
	else if(e.getSource() == graphButton) {
		drawGraph();
	}
	else if(e.getSource() == clearButton)	{
		// Clear arraylist of entries and update display to show nothing
		guiGraph.clear();
		guiGraph.update();
	}
}

/**
 * Adds entry from textField into arraylist(entriesDisplayed 
 * in NameSurferGraph class) of user inputted entries.
 * Then draws graph of name entered into textfield.
 */
private void drawGraph() {
	String a = textField.getText();
	NameSurferEntry b = nameSurferDataBase.findEntry(a);
	guiGraph.addEntry(b);
	//guiGraph.addEntry(nameSurferDataBase.findEntry(textField.getText()));
	guiGraph.update();
}
}
