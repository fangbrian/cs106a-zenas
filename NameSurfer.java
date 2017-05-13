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
		guiGraph = new NameSurferGraph();
		add(guiGraph);
		add(new JLabel("Name "), SOUTH);
	    tf = new JTextField(TEXT_FIELD_SIZE);
	    tf.addActionListener(this);
	    add(tf, SOUTH);
		add(graph = new JButton("Graph"), SOUTH);
		add(clear = new JButton("Clear"), SOUTH);
	    addActionListeners();
	    repo = new NameSurferDataBase(NAMES_DATA_FILE);
	    
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tf)
			println("TextFieldEntry: " + repo.findEntry(tf.getText()));
		else if(e.getSource() == graph)
			println("Graph:" + repo.findEntry(tf.getText()));
		else if(e.getSource() == clear)
			removeAll();
	}
}
