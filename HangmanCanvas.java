/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/* Resets the display so that only the scaffold appears */
public void reset() {
    drawScaffold();
}

//Draws the scaffold
private void drawScaffold() {
    double scaffoldTopX = getWidth()/2 - UPPER_ARM_LENGTH;
    double scaffoldTopY = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2 - ROPE_LENGTH;
    double scaffoldBottomY = scaffoldTopY + SCAFFOLD_HEIGHT;
    GLine scaffold= new GLine (scaffoldTopX, scaffoldTopY, scaffoldTopX, scaffoldBottomY);
    add(scaffold);
    double beamRightX = scaffoldTopX + BEAM_LENGTH;
    GLine beam = new GLine(scaffoldTopX, scaffoldTopY, beamRightX, scaffoldTopY);
    add(beam);
    double ropeBottomY = scaffoldTopY + ROPE_LENGTH;
    GLine rope = new GLine (beamRightX, scaffoldTopY, beamRightX, ropeBottomY);
    add(rope);
}
 
//Updates hidden word on screen based on player's progress, in regards to the word.
public void displayWord(String word) {
	double x = getWidth()/4;
	double y = getHeight() - HEAD_RADIUS*2;
	GLabel unGuessedWord = new GLabel(word, x, y);
	unGuessedWord.setFont("Halvetica-24");
	//Checks for existing set of letters, and removes the previous state of hidden word and replaces it newest state(including new, correctly guessed characters)
        if (getElementAt(x,y) != null){
            remove(getElementAt(x,y));
        }
        add(unGuessedWord);
    }

//Updates set of incorrectly guessed letters and draws corresponding
//body part on scaffold depending on number of incorrect guesses.
public void noteIncorrectGuess(String incorrectGuesses) {
    //Adds the label with the incorrect letters
	double x = getWidth()/4;
	double y = getHeight() - HEAD_RADIUS;
	GLabel incorrectLetters = new GLabel(incorrectGuesses, x, y);
	//Checks for existing set of letters, and removes the previous set of characters and replaces it newest set(newer incorrect guesses)
	if(getElementAt(x,y) != null) {
	    remove(getElementAt(x,y));
	}
	add(incorrectLetters);
	//Checks for number of incorrect guesses and draws corresponding body part on scaffold
    if(incorrectGuesses.length() == 1) {
        drawHead();
    }
    else if(incorrectGuesses.length() == 2) {
        drawBody();
    }
    else if(incorrectGuesses.length() == 3) {
        drawLeftArm();
    }
    else if(incorrectGuesses.length() == 4) {
        drawRightArm();
    }
    else if(incorrectGuesses.length() == 5) {
        drawLeftLeg();
    }
    else if(incorrectGuesses.length() == 6) {
        drawRightLeg();
    }
    else if(incorrectGuesses.length() == 7) {
        drawLeftFoot();
    }
    else if(incorrectGuesses.length() == 8) {
        drawRightFoot();
    }
}
 
//Draws the head on scaffold
private void drawHead() {
    double x = getWidth()/2 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS;
    double y = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2;
    GOval head = new GOval (x, y, HEAD_RADIUS*2, HEAD_RADIUS*2);
    add(head);
}

//Draws the body on scaffold
private void drawBody() {
    double x = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
    double topY = getHeight()/2 - BODY_LENGTH;
    double bottomY = topY + BODY_LENGTH;
    GLine body = new GLine (x, topY, x, bottomY);
    add(body);
}

//Draws the left arm on scaffold
private void drawLeftArm() {
    double insideOfArmX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
    double outsideOfArmX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - UPPER_ARM_LENGTH;
    double upperArmHeightY = getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
    GLine upperLeftArm = new GLine (insideOfArmX, upperArmHeightY, outsideOfArmX, upperArmHeightY);
    add(upperLeftArm);
    double lowerArmEndY = upperArmHeightY + LOWER_ARM_LENGTH;
    GLine lowerLeftArm = new GLine (outsideOfArmX, upperArmHeightY, outsideOfArmX, lowerArmEndY);
    add(lowerLeftArm);
}
 
//Draws the right arm on scaffold
private void drawRightArm() {
    double insideOfArmX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
    double outsideOfArmX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + UPPER_ARM_LENGTH;
    double upperArmHeightY = getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
    GLine upperRightArm = new GLine (insideOfArmX, upperArmHeightY, outsideOfArmX, upperArmHeightY);
    add(upperRightArm);
    double lowerArmEndY = upperArmHeightY + LOWER_ARM_LENGTH;
    GLine lowerRightArm = new GLine (outsideOfArmX, upperArmHeightY, outsideOfArmX, lowerArmEndY);
    add(lowerRightArm);
}
 
//Draws the left leg on scaffold
private void drawLeftLeg() {
    double hipStartX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
    double hipEndX = hipStartX - HIP_WIDTH;
    double hipHeightY = getHeight()/2;
    GLine leftHip = new GLine(hipStartX, hipHeightY, hipEndX, hipHeightY);
    add(leftHip);
    double leftLegY = hipHeightY + LEG_LENGTH;
    GLine leftLeg = new GLine(hipEndX, hipHeightY, hipEndX, leftLegY);
    add(leftLeg);
}

//Draws the right leg on scaffold
private void drawRightLeg() {
    double hipStartX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
    double hipEndX = hipStartX + HIP_WIDTH;
    double hipHeightY = getHeight()/2;
    GLine leftHip = new GLine(hipStartX, hipHeightY, hipEndX, hipHeightY);
    add(leftHip);
    double leftLegEndY = hipHeightY + LEG_LENGTH;
    GLine leftLeg = new GLine(hipEndX, hipHeightY, hipEndX, leftLegEndY);
    add(leftLeg);
}
 
//Draws the left foot on scaffold
private void drawLeftFoot() {
    double insideOfFootX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - HIP_WIDTH;
    double outsideOfFootX = insideOfFootX - FOOT_LENGTH;
    double footHeightY = getHeight()/2 + LEG_LENGTH;
    GLine leftFoot = new GLine(insideOfFootX, footHeightY, outsideOfFootX, footHeightY);
    add(leftFoot);
}
 
//Draws the right foot on scaffold
private void drawRightFoot() {
    double insideOfFootX = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + HIP_WIDTH;
    double outsideOfFootX = insideOfFootX + FOOT_LENGTH;
    double footHeightY = getHeight()/2 + LEG_LENGTH;
    GLine rightFoot = new GLine(insideOfFootX, footHeightY, outsideOfFootX, footHeightY);
    add(rightFoot);
}

/* Constants for the simple version of the picture (in pixels) */
private static final int SCAFFOLD_HEIGHT = 360;
private static final int BEAM_LENGTH = 144;
private static final int ROPE_LENGTH = 18;
private static final int HEAD_RADIUS = 36;
private static final int BODY_LENGTH = 144;
private static final int ARM_OFFSET_FROM_HEAD = 28;
private static final int UPPER_ARM_LENGTH = 72;
private static final int LOWER_ARM_LENGTH = 44;
private static final int HIP_WIDTH = 36;
private static final int LEG_LENGTH = 108;
private static final int FOOT_LENGTH = 28;
}
