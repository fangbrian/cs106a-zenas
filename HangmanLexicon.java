/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.util.ArrayList;
import java.io.*;

public class HangmanLexicon {

private ArrayList wordList = new ArrayList();

public HangmanLexicon() {
//Adds individual words in the file to the array list
        try {
            BufferedReader hangmanWords = new BufferedReader(new FileReader("HangmanLexicon.txt"));
            while(true) {
                String line = hangmanWords.readLine();
                if(line == null) break;
                wordList.add(line);
            }
            hangmanWords.close();
        } catch (IOException ex) {
        	throw new ErrorException(ex);
        }
    }
 
//Returns word at specified index
    public String getWord(int index) {
        return(String) wordList.get(index);
    }
 
//Returns number of words in lexicon
    public int getWordCount() {
        return wordList.size();
    }
}
