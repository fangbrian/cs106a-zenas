/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}
		
	private void playGame() {
		// Initialize duplicate check array element values to 0
		// *******************************
		// ******************CATEGORY SELECTION IS GOING WRONG
		Arrays.fill(dupCheck, 0);
		Arrays.fill(totalScore, 0);
		for(int i = 0; i < nPlayers; i++){
			// Prompts player to roll w/ a message at the bottom of the display
			display.printMessage(playerNames[i] + "'s turn!, Click the \"Roll Dice\" button to roll the dice.");
			// Declaring new variable to differentiate between index of player vs. player number(1 - nPlayers)
			int playerNumber = i + 1;
			// At beginning of player's turn, will highlight player's name and wait for player to click "Roll Dice"
			display.waitForPlayerToClickRoll(playerNumber);
			// Randomly selects dice values for N_DICE and displays values for those N_DICE
			rollDice();
			for(int j = 0; j < N_REROLLS; j++) {
				display.printMessage("Select the dice you wish to re-roll and click \"Roll again\"");
				// Waits for player to select dice to re-roll and then click "Roll Dice"
				display.waitForPlayerToSelectDice();
				// Changes value of selected dice
				changeSelectedDice();
				// Displays values of dice array
				display.displayDice(dice);
			}
			//cheatMode();
			display.printMessage("Select a category for this roll");
			// Wait for player to select a category and returns index of category
			int category = display.waitForPlayerToSelectCategory();
			// Checks to see if selected category has already been filled/selected before and calculates score based on category
			int score = duplicateCategorySelectionCheck(category, playerNumber);
			// Updates scorecard based on category selected
			totalScore[i] += score;
			display.updateScorecard(category, playerNumber, score);
			// Update Total on Scorecard
			display.updateScorecard(TOTAL, playerNumber, totalScore[i]);
			// Checks to see if end of game has been reached and determines winner
			if(gameEndChecker(i) == 13) {
				int winner = determineWinner();
				endGame(winner);
			}
			// If it's the last player's turn, then takes us back to first player's turn
			if(i == (nPlayers - 1))
				i = -1;
		}
	}

	private int determineWinner() {
		int winningPlayerNumber = 0;
		for(int i = 0; i < nPlayers; i++) {
			if(totalScore[i] > totalScore[i + 1])
				winningPlayerNumber = i;
			else
				winningPlayerNumber = i + 1;
		}
		return winningPlayerNumber;
	}

	private int gameEndChecker(int activePlayer) {
		int gameEndMarker = 0;
		for(int i = 0; i < N_SCORING_CATEGORIES; i++) {
			if(activePlayer == nPlayers - 1) {
				if(dupCheck[i] == 1) {
					gameEndMarker++;
				}
				else
					break;	
			}
			else
				break;
		}
		return gameEndMarker;
	}
	
	private void endGame(int winningPlayerNumber) {
			display.printMessage("Congratulations, " + playerNames[winningPlayerNumber] + "you're the winner with a total score of " + totalScore[winningPlayerNumber] + "!");
	}

	private int duplicateCategorySelectionCheck(int category, int playerNumber) {
		int score = 0;
		while(true) {
			if(dupCheck[category - 1] == 1) {
				display.printMessage("You already picked that category. Please choose another category.");
				int newCategory = display.waitForPlayerToSelectCategory();
				if(dupCheck[newCategory - 1] == 0) {
					dupCheck[newCategory - 1] = 1;
					score = calcCategoryScore(newCategory);
					//categoryScores.set(newCategory - 1, score);
					display.updateScorecard(newCategory, playerNumber, score);
					break;
				}
			}
			else {
				dupCheck[category - 1] = 1;
				score = calcCategoryScore(category);
				//categoryScores.set(category - 1, score);
				break;
			}
		}
		return score;
	}

	private void cheatMode() {
		dice[0] = 5;
		dice[1] = 5;
		dice[2] = 3;
		dice[3] = 3;
		dice[4] = 3;
		display.displayDice(dice);
	}

	private int calcCategoryScore(int category) {
		int categoryScore = 0;
		// Calculating score for categories Ones to Sixes
		if(category >= ONES && category <= SIXES)
			categoryScore = isOnesThroughSixesSum(category);
		// Calculating score for category Three of a Kind
		else if(category == THREE_OF_A_KIND)
			categoryScore = isThreeOfAKind();
		// Calculating score for category Four of a Kind
		else if(category == FOUR_OF_A_KIND)
			categoryScore = isFourOfAKind();
		// Calculating score for category Full House
		else if(category == FULL_HOUSE)
			categoryScore = isFullHouse();
		// Calculating score for category Small Straight
		else if(category == SMALL_STRAIGHT)
			categoryScore = isSmallStraight();
		// Calculating score for category Large Straight
		else if(category == LARGE_STRAIGHT)
			categoryScore = isLargeStraight();
		// Calculating score for category Yahtzee
		else if(category == YAHTZEE)
			categoryScore = isYahtzee();
		// Calculating score for category Chance
		else if(category == CHANCE)
			categoryScore = isChance();
			
		return categoryScore;
	}

	/** Gives sum of all values of the die. */
	private int isChance() {
		int sum = 0;
		for(int i = 0; i < N_DICE; i++)
			sum += dice[i];
		return sum;
	}

	/** Goes through all dice and checks if there are five dice of one kind.
	 * If there are five dice of one kind, then method will return a score of 50. */
	private int isYahtzee() {
		int score = 0;
		int count = countMatches();
		// Five of a Kind
		if(count == 4)
			score = 50;
		return score;
		// How come this won't work?:
		// if(count == 4)
		//	 return 50;
	}

	/** Goes through all dice and checks if there are five consecutive values
	 * from the dice roll. If this holds true, then method will return score
	 * of 40, 0 otherwise. */
	private int isLargeStraight() {
		int score = 0;
		// Sorts dice values from smallest to largest
		selectionSort();
		for(int i = 0; i < N_DICE - 4; i++) {
			if(dice[i] == (dice[i + 1] - 1) && dice[i + 1] == (dice[i + 2] - 1) && dice[i + 2] == (dice[i + 3] - 1) && dice[i + 3] == (dice[i + 4] - 1))
				score = 40;
		}
		return score;
	}

	/** Goes through all dice and checks if there are four consecutive values
	 * from the dice roll. If this holds true, then method will return score
	 * of 30, 0 otherwise. */
	private int isSmallStraight() {
		int score = 0;
		// Sorts dice values from smallest to largest
		selectionSort();
		for(int i = 0; i < N_DICE - 3; i++) {
			if(dice[i] == (dice[i + 1] - 1) && dice[i + 1] == (dice[i + 2] - 1) && dice[i + 2] == (dice[i + 3] - 1))
				score = 30;
		}
		return score;
	}

	/** Goes through all dice and checks if there are three of one kind and
	 * two of another kind. If this holds true, then method will return a
	 * score of 25, 0 otherwise. */
	private int isFullHouse() {
		// Sorts dice values from smallest to largest
		selectionSort();
		int score = 0;
		int count1 = countMatches();
		int count2 = reverseCountMatches();
		// Three of a Kind and Two of a Kind
		if((count1 == 2 && count2 == 1) || (count1 == 1 && count2 == 2))
			score = 25;
		return score;
	}

	/** Goes through all dice and checks if there are four dice of one kind.
	 * If there are four dice of one kind, then method will return sum of
	 * all dice. */
	private int isFourOfAKind() {
		// Sorts dice values from smallest to largest
		selectionSort();
		// Four of a Kind
		int sum = 0;
		int matchCount = 0;
		int count1 = countMatches(), count2 = reverseCountMatches();
		if(count1 > count2)
			matchCount = count1;
		else
			matchCount = count2;
		if(matchCount >= 3) {
			for(int i = 0; i < N_DICE; i++)
				sum += dice[i];
		}
		return sum;
	}
	
	/** Goes through all dice and checks if there are three dice of one kind.
	 * If there are three dice of one kind, then method will return sum of
	 * all dice. */
	private int isThreeOfAKind() {
		// Sorts dice values from smallest to largest
		selectionSort();
		// Three of a Kind
		int sum = 0;
		int matchCount = 0;
		int count1 = countMatches(), count2 = reverseCountMatches();
		if(count1 > count2)
			matchCount = count1;
		else
			matchCount = count2;
		if(matchCount >= 2) {
			for(int i = 0; i < N_DICE; i++)
				sum += dice[i];
		}
		return sum;
	}
	
	/** Goes through all dice and sums up the values of the die based on the
	 *  category chosen. For example, if category 1 is chosen and you have five
	 *  dice of value 1, your sum will be 5. If category 2 is chosen and you have
	 *  5 dice of value 2, your sum will be 10. */
	private int isOnesThroughSixesSum(int category) {
		int sum = 0;
		for(int i = 0; i < N_DICE; i++) {
			if(dice[i] == category)
				sum += category;
		}
		return sum;
	}
	
	/** Counts the number of matches in your collection of die, in reverse. */
	private int reverseCountMatches() {
		int matchCount = 0;
		// Counts the number of matches
		for(int i = N_DICE - 1; i > 0; i--) {
			if(dice[i] == dice [i - 1])
				matchCount++;
			else
				break;
		}
		return matchCount;
	}

	/** Counts the number of matches in your collection of die. */
	private int countMatches() {
		int matchCount = 0;
		// Counts the number of matches
		for(int i = 0; i < N_DICE - 1; i++) {
			if(dice[i] == dice [i + 1])
				matchCount++;
			else
				break;
		}
		return matchCount;
	}

	/** Sorts values from smallest to largest by searching through all 5 dice,
	 *  picking the smallest value, placing it at the beginning of unordered dice,
	 *  and then going through the rest of the values and going through the same process. */
	private void selectionSort() {
		int min_j, temp;
	   	for(int i = 0; i < N_DICE; i++) {
	   		// We are assuming the first dice has the smallest value
	   		min_j = i;
	   	    // Sorting through the rest of the dice to see which has the minimum value
	   		for(int j = i+1; j < N_DICE; j++)
	   	    {
	   	    	if(dice[j] < dice[min_j])
	   	    		min_j = j; 
	   	    }
	   		// Swaps dice value at i with dice with next smallest value
	   		temp = dice[i];
	   	    dice[i] = dice[min_j];
	   	    dice[min_j] = temp;
	   	 }
	}

	/** Re-rolls selected dice and displays new values for selected dice. */
	private void changeSelectedDice() {
		for(int i = 0; i < N_DICE; i++) {
			if(display.isDieSelected(i))
				dice[i] = rgen.nextInt(1,6);
		}
	}

	/** Randomly selects dice values for N_DICE and displays values for those N_DICE */
	private void rollDice() {
		for(int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		display.displayDice(dice);
	}

	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
	private int[] dupCheck = new int[N_CATEGORIES];
	private int[] totalScore = new int[nPlayers];
	// private ArrayList<Integer> categoryScores = new ArrayList<Integer>();
}
