/*
 * Scrabble.java
 *   - Main class of the Scrabble project.
 *
 * CSC444H1F Assignment 1 - Scrabble Solver
 *
 * Date Created : 2011/10/02
 * Created By   : Jeff Lee
 *
 * Code Info:
 *   ~100 lines of code
 *   ~60 minutes to think about the design and research online
 *   ~100 minutes to code + test + debug
 *   4 defects:
 *     Does not check the word appended or prefix with the letters already existed.
 *     Time increases in factorial speed.  Takes too long for too many letters.
 *     Doesn't take letter occurrence into account.
 *     BONUS & STAR cases are not handled yet
 *
 */
package com.leejefon.uoft.csc444.scrabble;

import com.leejefon.uoft.csc444.dictionary.Dictionary;
import java.util.Collection;

/**
 * Last Modified: 2011/10/17
 *
 * @author Jeff Lee
 */
public class Scrabble {

	/**
	 * Result class is used to store all the information of a result
	 */
	public class Result {

		// Whether a best move has found
		public boolean FOUND = false;

		// The result word with the best score
		public String WORD = null;

		// The best score found
		public int SCORE = 0;

		// The X position from top left to right, start from 0
		public int X_POS = -1;

		// The Y position from top left to bottom, start from 0
		public int Y_POS = -1;

		public boolean isVertical;

		// Prints the result
		@Override
		public String toString() {
			String result;

			if (FOUND == true) {
				result = "The word that generates the best score (" + SCORE + ") is '" + WORD + "'\n";
				result += "The word is " + (isVertical == true ? "Vertical" : "Horizontal");
				result += " starting at (" + X_POS + "," + Y_POS + ").";
			} else {
				result = "Pass";
			}

			return result;
		}
	}

	// The max number of letters allowed in one player's rack
	public static final int MAX_RACK_SIZE = 7;

	// The board including the letters currently on it
	public ScrabbleBoard BOARD;

	// Contains the list of words in Trie data structure
	public Dictionary DICTIONARY = new Dictionary("resources/dictionary.list");

	/**
	 * Constructor.  Loads the two default map files if no arguments are passed in
	 */
	public Scrabble() {
		BOARD = new ScrabbleBoard("resources/board.map", "resources/play.map");
	}

	/**
	 * Constructor.
	 *
	 * @param boardFile
	 * @param playFile
	 */
	public Scrabble(String boardFile, String playFile) {
		BOARD = new ScrabbleBoard(boardFile, playFile);
	}

	/**
	 * Finds the best result with inputs of the current rack letters, current board letters, and the dictionary
	 *
	 * @return best result found
	 */
	public Result getBestMove(String rack){
		Result result = new Result();

		Collection<String> possibleWords = null;
		int[] rowRange = BOARD.getRowRange();
		int[] colRange = BOARD.getColRange();

		// Find all possible horizontal words and try every placement on the board
		for (int j = rowRange[0]; j <= rowRange[1]; j++) {
			possibleWords = DICTIONARY.find(merge(rack, BOARD.getRowLetters(j)));
			for (String str : possibleWords) {
				for (int i = 0; i <= BOARD.getX() - str.length(); i++) {
					int temp = BOARD.calcPoints(i, j, false, str);

					// Store the better result found in the result variable
					if (temp > result.SCORE) {
						result.SCORE = temp;
						result.FOUND = true;
						result.WORD = str;
						result.X_POS = i;
						result.Y_POS = j;
						result.isVertical = false;
					}
				}
			}
		}

		// Find all possible vertical words and try every placement on the board
		for (int i = colRange[0]; i <= colRange[1]; i++) {
			possibleWords = DICTIONARY.find(merge(rack, BOARD.getColLetters(i)));
			for (String str : possibleWords) {
				for (int j = 0; j <= BOARD.getY() - str.length(); j++) {
					int temp = BOARD.calcPoints(i, j, true, str);

					// Store the better result found in the result variable
					if (temp > result.SCORE) {
						result.SCORE = temp;
						result.FOUND = true;
						result.WORD = str;
						result.X_POS = i;
						result.Y_POS = j;
						result.isVertical = true;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Merge the letters in the rack and also the letters on the board
	 *
	 * @param str - letters in the rack
	 * @param s - letters in the board
	 * @return String[] - merged array
	 */
	private String[] merge(String str, String[] s) {
		String[] result = new String[str.length() + s.length];

		int i = 0;

		for (i = 0; i < str.length(); i++) {
			result[i] = str.substring(i, i + 1);
		}

		for (; i < str.length() + s.length; i++) {
			result[i] = s[i - str.length()];
		}

		return result;
	}

	/**
	 * Main entry point of the program
	 *
	 * @param args - not used
	 */
	public static void main(String[] args) {

		Scrabble scrabble = new Scrabble("resources/board.map", "resources/play.map");

		// check how many chars on the board to match the occurrance
		String rack = "NIMAAS";

		Result bestResult = scrabble.getBestMove(rack);

		System.out.println(bestResult);
	}
}
