/*
 * ScrabbleBoard.java
 *   - Loads the board and also the letters currently on the board.
 *
 * CSC444H1F Assignment 1 - Scrabble Solver
 *
 * Date Created : 2011/10/02
 * Created By   : Jeff Lee
 *
 * Code Info:
 *   ~300 lines of code
 *   ~30 minutes to think about the design and research online
 *   ~90 minutes to code
 *   ~120 minutes to test & debug
 *   1 defects:
 *     It calls Dictionary when calculating the words of neighbour.  Don't like it very much.
 *
 */
package com.leejefon.uoft.csc444.scrabble;

import com.leejefon.uoft.csc444.dictionary.Dictionary;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Last Modified: 2011/10/17
 *
 * @author Jeff Lee
 */
public class ScrabbleBoard {

	// Stores the Premium Squares location
	private char[][] EMPTY_BOARD;

	// Stores the letters currently on the board
	private char[][] PLAY_BOARD;

	// The size of the Height(Y) of the board
	private int boardY;

	// The size of the Width(X) of the board
	private int boardX;

	// For loading board files, used to determine which types of board is inputed
	public enum BoardType {
		EMPTY, PLAY
	}

	/**
	 * ScrabbleBoard Constructor
	 *
	 * @param boardMap - file name of the empty board map
	 * @param playMap - file name of the play map
	 */
	public ScrabbleBoard(String boardMap, String playMap) {
		loadBoard(boardMap, BoardType.EMPTY);
		loadBoard(playMap, BoardType.PLAY);
	}

	/**
	 * Load the board from file.  Can load either empty board or play board.
	 *
	 * @param file - File name in string
	 * @param type - Enum of the type of the board to load
	 */
	private void loadBoard(String file, BoardType type) {
		try {
			BufferedReader map = new BufferedReader(new FileReader(file));
			String row;

			String[] info = map.readLine().split(" ");

			boardY = Integer.parseInt(info[0]);
			boardX = Integer.parseInt(info[1]);

			char[][] temp = new char[boardY][boardX];

			int i = 0;
			while ((row = map.readLine()) != null) {
				if (row.trim().length() == 0 || row.startsWith("#")) {
					continue;
				}
				int j = 0;
				for (String c : row.split(" ")) {
					temp[i][j++] = c.charAt(0);
				}
				i++;
			}

			if (type == BoardType.EMPTY) {
				EMPTY_BOARD = new char[boardY][boardX];
			} else if (type == BoardType.PLAY) {
				PLAY_BOARD = new char[boardY][boardX];
			}

			for (int j = 0; j < temp.length; j++) {
				System.arraycopy(temp[j], 0, type == BoardType.EMPTY ? EMPTY_BOARD[j] : PLAY_BOARD[j], 0, temp.length);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	/**
	 * Get the X (width) of the board
	 *
	 * @return int - X of board
	 */
	public int getX() {
		return boardX;
	}

	/**
	 * Get the Y (height) of the board
	 *
	 * @return int - Y of board
	 */
	public int getY() {
		return boardY;
	}

	/**
	 * Get the letters or letter groups in a column.
	 *
	 * Example: Row(_ E _ _ A B C _ _) will return String["E", "ABC"]
	 *
	 * @param Y - Row number
	 * @return String[] - array of letters or letter groups in string
	 */
	public String[] getRowLetters(int Y) {
		String letters = "";
		char prev = '_';

		for (char c : PLAY_BOARD[Y]) {
			if (prev == '_' && c == '_') {
				continue;
			}
			letters += Character.toString(c);
			prev = c;
		}

		return letters.split("_");
	}

	/**
	 * Get the letters or letter groups in a column.
	 *
	 * Example: Col(_ E _ _ A B C _ _) will return String["E", "ABC"]
	 *
	 * @param X - Column number
	 * @return String[] - array of letters or letter groups in string
	 */
	public String[] getColLetters(int X) {
		String letters = "";
		char prev = '_';

		for (int j = 0; j < boardY; j++) {
			if (prev == '_' && PLAY_BOARD[j][X] == '_') {
				continue;
			}
			letters += Character.toString(PLAY_BOARD[j][X]);
			prev = PLAY_BOARD[j][X];
		}

		return letters.split("_");
	}

	/**
	 * Get range of rows that are valid to insert words
	 *
	 * @return int[] - range[0,1] = (from, to)
	 */
	public int[] getRowRange() {
		int[] range = new int[2];
		boolean flag = false;

		OUTSIDE:
		for (int i = 0; i < boardY; i++) {
			for (int j = 0; j < boardX; j++) {
				if (flag == false && PLAY_BOARD[i][j] != '_') {
					flag = true;
					range[0] = i == 0 ? i : i - 1;
					break;
				} else if (flag == true && PLAY_BOARD[i][j] != '_') {
					break;
				} else if (flag == true && PLAY_BOARD[i][j] == '_' && j == boardX - 1) {
					range[1] = i;
					break OUTSIDE;
				}
			}
		}

		return range;
	}

	/**
	 * Get range of columns that are valid to insert words
	 *
	 * @return int[] - range[0,1] = (from, to)
	 */
	public int[] getColRange() {
		int[] range = new int[2];
		boolean flag = false;

		OUTSIDE:
		for (int j = 0; j < boardX; j++) {
			for (int i = 0; i < boardY; i++) {
				if (flag == false && PLAY_BOARD[i][j] != '_') {
					flag = true;
					range[0] = j == 0 ? j : j - 1;
					break;
				} else if (flag == true && PLAY_BOARD[i][j] != '_') {
					break;
				} else if (flag == true && PLAY_BOARD[i][j] == '_' && i == boardY - 1) {
					range[1] = j;
					break OUTSIDE;
				}
			}
		}

		return range;
	}

	/**
	 * Calculate the total points gain providing the word and the location to put it
	 *
	 * @param startX - X position of the beginning of word, relative to top left corner, starting from 0
	 * @param startY - Y position of the beginning of word, relative to top left corner, starting from 0
	 * @param isVertical - Whether the word is vertically placed or horizontally
	 * @param word - The word to be calculated
	 * @return int - Calculated score
	 *    score  valid word
	 *    -1     invalid word
	 */
	public int calcPoints(int startX, int startY, boolean isVertical, String word) {
		int score = 0;
		int extraScore = 0;
		int noNeighbour = 0;
		int allExisted = 0;

		int trippleWord = 0;
		int doubleWord = 0;

		if (isVertical == true) {
			word = String.format("%" + (startY + word.length()) + "s", word).toUpperCase();

			for (int j = startY; j < word.length() && j < boardY; j++) {
				if (PLAY_BOARD[j][startX] == '_') {
					switch (EMPTY_BOARD[j][startX]) {
						case 'T':
							score += Letters.POINT.get(word.charAt(j));
							trippleWord++;
							break;
						case 't':
							score += Letters.POINT.get(word.charAt(j)) * 3;
							break;
						case 'D':
							score += Letters.POINT.get(word.charAt(j));
							doubleWord++;
							break;
						case 'd':
							score += Letters.POINT.get(word.charAt(j)) * 2;
							break;
						case '_':
							score += Letters.POINT.get(word.charAt(j));
					}

					int bonus = calcNeighbourPoints(word.charAt(j) + "-" + startX + "-" + j, isVertical);

					switch (bonus) {
						case -1: return -1;            // Invalid word
						case 0:  noNeighbour++; break; // No neighbour for the current letter
						default: extraScore += bonus;
					}
				} else if (PLAY_BOARD[j][startX] == word.charAt(j)) {
					score += Letters.POINT.get(word.charAt(j));
					allExisted++;
				} else if (PLAY_BOARD[j][startX] != word.charAt(j)) {
					return -1;
				}
			}
		} else {
			word = String.format("%" + (startX + word.length()) + "s", word).toUpperCase();

			for (int i = startX; i < word.length() && i < boardY; i++) {
				if (PLAY_BOARD[startY][i] == '_') {
					switch (EMPTY_BOARD[startY][i]) {
						case 'T':
							score += Letters.POINT.get(word.charAt(i));
							trippleWord++;
							break;
						case 't':
							score += Letters.POINT.get(word.charAt(i)) * 3;
							break;
						case 'D':
							score += Letters.POINT.get(word.charAt(i));
							doubleWord++;
							break;
						case 'd':
							score += Letters.POINT.get(word.charAt(i)) * 2;
							break;
						case '_':
							score += Letters.POINT.get(word.charAt(i));
					}

					int bonus = calcNeighbourPoints(word.charAt(i) + "-" + i + "-" + startY, isVertical);

					switch (bonus) {
						case -1: return -1;            // Invalid word
						case 0:  noNeighbour++; break; // No neighbour for the current letter
						default: extraScore += bonus;
					}
				} else if (PLAY_BOARD[startY][i] == '*' || PLAY_BOARD[startY][i] == word.charAt(i)) {
					score += Letters.POINT.get(word.charAt(i));
					allExisted++;
				} else if (PLAY_BOARD[startY][i] != word.charAt(i)) {
					return -1;
				}
			}
		}

		// If there is no neighbour for every letter, the word is not connected to the played letters
		// If all the words are already on the board, no letter from the rack is played
		if (noNeighbour == word.trim().length() || allExisted == word.trim().length()) {
			return -1;
		}

		return score * (int) Math.pow(3, trippleWord) * (int) Math.pow(2, doubleWord) + extraScore;
	}

	/**
	 *
	 * @param newLetterPos
	 * @param wasVertical
	 * @return
	 *    score  valid neighbor
	 *    0      no neighbor
	 *    -1     invalid neighbor
	 */
	private int calcNeighbourPoints(String newLetterPos, boolean wasVertical) {
		String letter = newLetterPos.split("-")[0];
		int xPos = Integer.parseInt(newLetterPos.split("-")[1]);
		int yPos = Integer.parseInt(newLetterPos.split("-")[2]);
		int score = Letters.POINT.get(letter.charAt(0));

		boolean trippleWord = false;
		boolean doubleWord = false;

		if (EMPTY_BOARD[yPos][xPos] == 'T') {
			trippleWord = true;
		} else if (EMPTY_BOARD[yPos][xPos] == 't') {
			score *= 3;
		} else if (EMPTY_BOARD[yPos][xPos] == 'D') {
			doubleWord = true;
		} else if (EMPTY_BOARD[yPos][xPos] == 'd') {
			score *= 2;
		}

		// If it was vertical, find the horizontal neighbours of the letter.  Revers if it was horizontal.
		if (wasVertical == true) {
			for (int i = xPos - 1; i > 0; i--) {
				if (PLAY_BOARD[yPos][i] == '_') {
					break;
				} else {
					letter = PLAY_BOARD[yPos][i] + letter;
					score += Letters.POINT.get(PLAY_BOARD[yPos][i]);
				}
			}
			for (int i = xPos + 1;i < boardX; i++) {
				if (PLAY_BOARD[yPos][i] == '_') {
					break;
				} else {
					letter += PLAY_BOARD[yPos][i];
					score += Letters.POINT.get(PLAY_BOARD[yPos][i]);
				}
			}
		} else {
			for (int i = yPos - 1; i > 0; i--) {
				if (PLAY_BOARD[i][xPos] == '_') {
					break;
				} else {
					letter = PLAY_BOARD[i][xPos] + letter;
					score += Letters.POINT.get(PLAY_BOARD[i][xPos]);
				}
			}
			for (int i = yPos + 1;i < boardY; i++) {
				if (PLAY_BOARD[i][xPos] == '_') {
					break;
				} else {
					letter += PLAY_BOARD[i][xPos];
					score += Letters.POINT.get(PLAY_BOARD[i][xPos]);
				}
			}
		}

		if (trippleWord == true) {
			score *= 3;
		} else if (doubleWord == true) {
			score *= 2;
		}

		// Checking if the letter formed with neighbour is a valid word
		// calling dictionary, not very like it
		if (letter.length() > 1) {
			Dictionary dic = new Dictionary("resources/dictionary.list");
			if (!dic.find(letter, false).isEmpty()) {
				return score;
			} else {
				// can't make a word with neighbours, fail
				return -1;
			}
		}

		// No neighbour
		return 0;
	}
}
