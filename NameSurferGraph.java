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
	private GLine line;
	private GLabel decades;
	private ArrayList<NameSurferEntry> entriesDisplayed = new ArrayList<NameSurferEntry>();
	
	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
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
		for(int i = 0; i < entriesDisplayed.size(); i++) {
			drawGraphLines(entriesDisplayed.get(i), i);
			writeNameLabels(entriesDisplayed.get(i), i);
		}			
	}
     
	/** 
	 * Draws graph lines based on rankings of names
	 */
	private void drawGraphLines(NameSurferEntry entry, int entryNumber) {
		for(int i = 0; i < NDECADES - 1; i++) {
            int ranking1 = entry.getRank(i);
            int ranking2 = entry.getRank(i + 1);
            double x1 = i * (getWidth() / NDECADES);
            double x2 = (i + 1) * (getWidth() / NDECADES);
            double y1 = 0;
            double y2 = 0;
            if(ranking1 != 0 && ranking2 != 0) {
                y1 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * ranking1 / MAX_RANK;
                y2 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * ranking2 / MAX_RANK;
            }
            else if(ranking1 == 0 && ranking2 == 0) {
                y1 = getHeight() - GRAPH_MARGIN_SIZE;
                y2 = getHeight() - GRAPH_MARGIN_SIZE;
            }
            else if (ranking1 == 0) {
                y1 = getHeight() - GRAPH_MARGIN_SIZE;
                y2 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * ranking2 / MAX_RANK;
            }
            else if(ranking2 == 0) {
                y1 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * ranking1 / MAX_RANK;
                y2 = getHeight() - GRAPH_MARGIN_SIZE;
            }
            GLine line = new GLine(x1, y1, x2, y2);
            // Color of first line drawn will be black
            // 2nd = red, 3rd = blue, 4th = magenta
            // then program cycles through those colors
            if(entryNumber % 4 == 1) {
                line.setColor(Color.RED);
            }
            else if(entryNumber % 4 == 2) {
                line.setColor(Color.BLUE);
            }
            else if(entryNumber % 4 == 3) {
                line.setColor(Color.MAGENTA);
            }
            add(line);
        }
	}

	/**
	 * Writes name labels and ranks in the upper right hand corner of
	 * corresponding points on graph
	 */
	private void writeNameLabels(NameSurferEntry entry, int entryNumber) {
        for(int i = 0; i < NDECADES; i++) {
            String name = entry.getName();
            int rank = entry.getRank(i);
            String rankString = Integer.toString(rank);
            String label = name + " " + rankString;
            double x = i * (getWidth() / NDECADES) + LABEL_MARGIN;
            double y = 0;
            if(rank != 0) {
                y = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * rank / MAX_RANK - LABEL_MARGIN;
            }
            else {
                label = name + " *";
                y = getHeight() - GRAPH_MARGIN_SIZE - LABEL_MARGIN;
            }
            GLabel nameLabel = new GLabel(label, x, y);
            // Color of first label + rank drawn will be black
            // 2nd = red, 3rd = blue, 4th = magenta
            // then program cycles through those colors
            if(entryNumber % 4 == 1) {
                nameLabel.setColor(Color.RED);
            }
            else if(entryNumber % 4 == 2) {
                nameLabel.setColor(Color.BLUE);
            }
            else if(entryNumber % 4 == 3) {
                nameLabel.setColor(Color.MAGENTA);
            }
            add(nameLabel);
        }
    }

	 /**
	  * Draws the decades at the bottom of the display
	  */
	private void writeDecades(int windowWidth, int windowHeight) {
		int fontHeight = 0;
		for(int i = 0; i < NDECADES; i++) {
			decades = new GLabel(Integer.toString(START_DECADE + (10 * i)), (i * (windowWidth / NDECADES) + LABEL_MARGIN), windowHeight - GRAPH_MARGIN_SIZE + LABEL_MARGIN);
			fontHeight = (int) decades.getAscent();
			decades.move(0, fontHeight);
			add(decades);
		}
	}

	/**
	 * Draws the vertical and horizontal gridlines on the display
	 */
	private void drawGridLines(int windowWidth, int windowHeight) {
		//Drawing vertical grid lines
		for(int v = 1; v < NDECADES; v++) {
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
