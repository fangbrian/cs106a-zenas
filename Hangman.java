/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

public void run() {
	setUpGame();
	playGame();
}

//Resets canvas
//Displays welcome message
//Displays number of letters in word
//Displays number of guesses user has
private void setUpGame() {
    canvas.reset();
    //Gets word from Lexicon, masks letters with -'s and puts into hiddenWord
    hiddenWord = showNumberOfLetters();
    //Displays sequence of -'s from above onto canvas
    canvas.displayWord(hiddenWord);
    println("Welcome to Hangman!");
    println("The word now looks like this: " + hiddenWord);
    println("You have " + guessCount + " guesses left.");
}

//Generates a random word selected from the HangmanLexicon
private String pickWord() {
    hangmanWords = new HangmanLexicon();
    //Will choose random index from 0 to number of words - 1 from Lexicon for guess word
    int randomWord = rgen.nextInt(0, (hangmanWords.getWordCount() - 1));
    //Will retrieve word corresponding to index above
    String pickedWord = hangmanWords.getWord(randomWord);
    return pickedWord;
}

//Shows how many letters there are(indicated with -'s) in the word as part of game setup
private String showNumberOfLetters() {
    String result = "";
    //Draws a "-" for each letter of the picked word(lexWord)
    for(int i = 0; i < lexWord.length(); i++) {
        result = result + "-";
    }
    return result;
    }

private void playGame() {
    while(guessCount > 0) {
        String getChar = readLine("Your guess: ");
        //Check to see that the player entered in only one character
        while (true) {
            if(getChar.length() > 1) {
                getChar = readLine("You can only guess one letter at a time. Try again: ");
            }
            if(getChar.length() == 1) break;
        }
        //Takes first character of String getChar and initializes it to Character ch
        ch = getChar.charAt(0);
        //Checks if input from player is lowercase, if it is, then will UpperCase entered character
        if(Character.isLowerCase(ch)) {
            ch = Character.toUpperCase(ch);
        }
        letterCheck();
        if (hiddenWord.equals(lexWord)) {
            println("You guessed the word: " + lexWord);
            println("You win");
            break;
        }
        println("The word now looks like this: " + hiddenWord);
        println("You have " + guessCount + " guesses left.");

    }
    if(guessCount == 0) {
        println("You're completely hung.");
        println("The word was:" + lexWord);
        println("You lose.");
    }
}

//Updates the hiddenWord if the character entered is correct
private void letterCheck() {
    //Checks to see if the guessed letter is in the word. If it gets to -1,
	//that means we searched the entire string and found that the guessed
	//character does not exist in string.
    if(lexWord.indexOf(ch) == -1) {
        println("There are no " + ch + "'s in the word");
        //Decrements number of guesses player has left
        guessCount--;
        //Keeping track of number of incorrect letters player has guessed.
        incorrectLetters = incorrectLetters + ch;
        //Displays incorrect guesses for player to see
        canvas.noteIncorrectGuess(incorrectLetters);
    }
    //Player has guessed a correct character in the string
    if(lexWord.indexOf(ch) != -1) {
        println("The guess is correct.");
    }
    //Goes through each of the letters in the string and checks if it matches the guessed letter,
    //if it matches, it updates the hidden word to reveal the position of the guessed letter
    for(int i = 0; i < lexWord.length(); i++) {
        if(ch == lexWord.charAt(i)) {
            if(i > 0) {
                hiddenWord = hiddenWord.substring(0, i) + ch + hiddenWord.substring(i + 1);
            }
            if(i == 0) {
                hiddenWord = ch + hiddenWord.substring(1);
            }
            canvas.displayWord(hiddenWord);
        }
    }
}

//Adds canvas for side by side display of hangman and prompts
public void init() {
    canvas = new HangmanCanvas();
    add(canvas);
    }

//Declaring the canvas object for drawing
private HangmanCanvas canvas;
//Declaring the Hangman Lexicon variable to use for pulling words
private HangmanLexicon hangmanWords;
//Variable that will keep track of number of guesses player has left
private int guessCount = 8;
//Declaring an instance of the random generator
private RandomGenerator rgen = RandomGenerator.getInstance();
//This is the word being guessed
private String hiddenWord;
//This is the secret word pulled from the lexicon
private String lexWord = pickWord();
//This is the latest character entered by the user
private char ch;
//This string keeps track of all the incorrect guessed letters
private String incorrectLetters = "";
}
