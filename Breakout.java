/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/** Ball Animation Delay Time */
	private static final int DELAY = 8;
	
/** End Game Message Delay Time */
	private static final int MESSAGE_DELAY = 200;

/* Method: run() */
/** Runs the Breakout program. */
public void run() {
	setupBorders();
	setupBricks((WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - ((NBRICKS_PER_ROW - 1) * BRICK_SEP))/2, BRICK_Y_OFFSET);
	setupPaddle();
	playGame();
}

//Setup the borders
private void setupBorders(){
	top = new GLine(0, 0, WIDTH, 0);
	leftSide = new GLine(0, 0, 0, HEIGHT);
	rightSide = new GLine(WIDTH, 0, WIDTH, HEIGHT);
	bottom = new GLine(0, HEIGHT, WIDTH, HEIGHT);
	add(top);
	add(leftSide);
	add(rightSide);
	add(bottom);
}

private void setupBricks(int x, int y){
	//Setting up each row
	for(int i = 0; i < NBRICK_ROWS; i++){
		int temp = x;
		//Setting up each brick in a row
		for(int j = 0; j < NBRICKS_PER_ROW; j++){
			GRect rect = new GRect(temp, y, BRICK_WIDTH, BRICK_HEIGHT);
			rect.setFilled(true);
				if(i < 2){
					rect.setColor(Color.RED);
					rect.setFillColor(Color.RED);
				}
				else if(i >= 2 && i < 4){
					rect.setColor(Color.ORANGE);
					rect.setFillColor(Color.ORANGE);
				}
				else if(i >= 4 && i < 6){
					rect.setColor(Color.YELLOW);
					rect.setFillColor(Color.YELLOW);
				}
				else if(i >= 6 && i < 8){
					rect.setColor(Color.GREEN);
					rect.setFillColor(Color.GREEN);
				}
				else if(i >= 8 && i < 10){
					rect.setColor(Color.CYAN);
					rect.setFillColor(Color.CYAN);
				}
				add(rect);
				temp += BRICK_WIDTH + BRICK_SEP;
		}
		y += BRICK_HEIGHT + BRICK_SEP;
	}
}

//Create the paddle
private void setupPaddle(){
	paddle = new GRect(WIDTH/2 - PADDLE_WIDTH/2, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
	paddle.setColor(Color.BLACK);
	paddle.setFillColor(Color.BLACK);
	paddle.setFilled(true);
	add(paddle);
	addMouseListeners();
}

//Have the paddle track the mouse
public void mouseMoved(MouseEvent e){
	if ((e.getX() < WIDTH - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
		paddle.setLocation(e.getX() - PADDLE_WIDTH/2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}
}

private void playGame(){
	//create the ball
	ball = new GOval(WIDTH/2, HEIGHT/2, BALL_RADIUS*2, BALL_RADIUS*2);
	ball.setColor(Color.BLACK);
	ball.setFilled(true);
	add(ball);
	//click on the screen to start the game.
	waitForClick();
	//set initial ball velocities
	setBallVelocity();
	while(true){
		//get the ball moving
		moveBall();
		//Once you run out of bricks, execute the You Win message
		if(brickCounter == 0)
			youWin();
	}
}

private void setBallVelocity(){
	//set the initial x velocity(1 to 3) and constant y ball velocity, with the x direction being chosen at random with probability 0.5.
	vx = rgen.nextDouble(1.0, 3.0);
	if(rgen.nextBoolean(0.5)) vx = -vx;
	vy = 1;
}

private void moveBall(){
	//move the ball by displacement vx and vy
	ball.move(vx, vy);
	//retrieve a colliding object
	GObject collider = getCollidingObject();
	//if the ball hits the paddle, change the direction of the ball
	if (collider == paddle) {
		if(ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2)
			vy = -vy;	
	}
	//if the ball hits the top or side walls, change the direction. If it hits the bottom, execute the you lose message.
	else if(collider == top || collider == leftSide || collider == rightSide || collider == bottom){
		if(ball.getY() <= top.getY())
			vy = -vy;
		else if((ball.getY() + BALL_RADIUS * 2) == bottom.getY())
			youLose();
		else if(ball.getX() <= leftSide.getX() || (ball.getX() + BALL_RADIUS * 2) >= rightSide.getX())
				vx = -vx;
		}
	//if the ball hits a brick, remove the brick, decrement the brick counter, and change the direction of the ball.
	else if(collider != null) {
		remove(collider);
		brickCounter--;
		vy = -vy;
	}
	//adding in a pause for smooth animation	
	pause (DELAY);
	}

//retrieve a colliding object
private GObject getCollidingObject() {
		//returning a colliding object on the top left corner of the ball
		if((getElementAt(ball.getX(), ball.getY())) != null)
	         return getElementAt(ball.getX(), ball.getY());
		//returning a colliding object on the top right corner of the ball
		else if (getElementAt( (ball.getX() + BALL_RADIUS * 2), ball.getY()) != null )
	         return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
		//returning a colliding object on the bottom left corner of the ball
		else if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS * 2)) != null )
	         return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
		//returning a colliding object on the bottom right corner of the ball
		else if(getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != null )
	         return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
		//returning null if there are no objects present at any of the corners
		else
	         return null;
	}

private void youLose(){
	//Creating the You Lose message - Will flash 5 times and then show Game Over
	GLabel youLose = new GLabel("YOU LOSE");
	youLose.setFont(new Font("Serif", Font.BOLD, 50));
	youLose.setColor(Color.RED);
	youLose.setLocation(0.5 * WIDTH - 0.5 * youLose.getWidth(), 0.5 * HEIGHT + 0.5 * youLose.getAscent());
	for(int i = 0; i < 5; i++){
		add(youLose);
		pause(MESSAGE_DELAY);
		remove(youLose);
		pause(MESSAGE_DELAY);
	}
	gameOver();
}

private void youWin(){
	//Creating the You Win message - Will flash 5 times and then show Game Over
	GLabel youWin = new GLabel("YOU WIN");
	youWin.setFont(new Font("Serif", Font.BOLD, 50));
	youWin.setColor(Color.BLUE);
	youWin.setLocation(0.5 * WIDTH - 0.5 * youWin.getWidth(), 0.5 * HEIGHT + 0.5 * youWin.getAscent());
	for(int i = 0; i < 5; i++){
		add(youWin);
		pause(MESSAGE_DELAY);
		remove(youWin);
		pause(MESSAGE_DELAY);
	}
	gameOver();
}

private void gameOver(){
	//Creating the Game Over message
	GLabel gameOver = new GLabel("GAME OVER");
	gameOver.setFont(new Font("Serif", Font.BOLD, 50));
	gameOver.setColor(Color.CYAN);
	gameOver.setLocation(0.5 * WIDTH - 0.5 * gameOver.getWidth(), 0.5 * HEIGHT + 0.5 * gameOver.getAscent());
	add(gameOver);
}

/**Declaring instance variables*/
private GRect paddle;	/*This is the paddle moving with the unclicked mouse*/
private GOval ball;		/*This is the moving ball*/
private GLine top, leftSide, rightSide, bottom;		/*These are the borders of the game*/
private RandomGenerator rgen = RandomGenerator.getInstance();	/*Creating an instance of the random generator*/
private double vx, vy;		/*These are the ball velocities in the x and y directions, respectively*/
private int brickCounter = NBRICKS_PER_ROW * NBRICK_ROWS;	/*This is the brick counter. Once it equals 0, game is over*/
}
