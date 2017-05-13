/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

/* Instance Variables */
	private GRect window;
	private GLine line;
	private GLabel decades;
	private ArrayList<NameSurferEntry> entriesDisplayed;
	
	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		entriesDisplayed = new ArrayList<NameSurferEntry>();
		//window = new GRect(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entriesDisplayed.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		entriesDisplayed.add(entry);
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		drawGridLines(getWidth(), getHeight());
		writeDecades(getWidth(), getHeight());
		
	}
	
	private void writeDecades(int windowWidth, int windowHeight) {
		int fontHeight = 0;
		for(int i = 0; i < NDECADES; i++) {
			decades = new GLabel(Integer.toString(START_DECADE + (10 * i)), (i * (windowWidth / NDECADES) + DECADE_LABEL_MARGIN), windowHeight - GRAPH_MARGIN_SIZE + DECADE_LABEL_MARGIN);
			fontHeight = (int) decades.getAscent();
			decades.move(0, fontHeight);
			add(decades);
		}
	}

	private void drawGridLines(int windowWidth, int windowHeight) {
		//Drawing vertical grid lines
		for(int v = 1; v < 11; v++) {
			line = new GLine(v * (windowWidth / NDECADES), 0, v * (windowWidth / NDECADES), windowHeight);
			add(line);
		}
		//Drawing horizontal grid lines
		line = new GLine(0, GRAPH_MARGIN_SIZE, windowWidth, GRAPH_MARGIN_SIZE);
		add(line);
		line = new GLine(0, windowHeight - GRAPH_MARGIN_SIZE, windowWidth, windowHeight - GRAPH_MARGIN_SIZE);
		add(line);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
