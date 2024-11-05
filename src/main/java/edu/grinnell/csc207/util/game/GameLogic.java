package edu.grinnell.csc207.util.game;

import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;

/**
 * Core game logic for the game of Wordle.
 *
 * @author Khanh Do
 * @author Andrew Fargo
 */
public class GameLogic {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default width.
   */
  static final int DEFAULT_WIDTH = 5;

  /**
   * The default number of rows.
   */
  static final int DEFAULT_HEIGHT = 6;

  /**
   * The ANSI code for the color white.
   */
  static final String ANSI_RESET = "\u001B[0m";

  /**
   * The ANSI code for the color green.
   */
  static final String ANSI_GREEN = "\u001B[32m";

  /**
   * The ANSI code for the color yellow.
   */
  static final String ANSI_YELLOW = "\u001B[33m";

  // +--------+---------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The number of guesses the player has left.
   */
  private int guessLeft = DEFAULT_HEIGHT;

  /**
   * The current state of the board.
   */
  private Matrix<String> board;

  /**
   * The target word.
   *
   * Should have method like getTargetWord() and checkValidEnglishWord()
   */
  private Words targetWord;

  // +-------------+---------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a new game.
   */
  public GameLogic() {
    this.board = new MatrixV0<>(GameLogic.DEFAULT_WIDTH, GameLogic.DEFAULT_HEIGHT, "");

    this.targetWord = new Words();
  } // GameLogic()

  // +---------+---------------------------------------------------
  // | Methods |
  // +---------+

  public int getGuessLeft() {
    return this.guessLeft;
  } // getGuessLeft()

  private int decrementGuessLeft() {
    return this.guessLeft--;
  } // decrementGuessLeft()

  /**
   * toString prints the board in format.
   */

  /**
   * Register a guess into the board.
   *
   * @param guess User's input
   * @return true if the guess get registered successfully and false otherwise
   */
  public boolean registerGuess(String guess) {
    guess = guess.toUpperCase();

    if (this.getGuessLeft() == 0) {
      return false;
    } // if

    if (!this.targetWord.checkValidEnglishWord(guess)) {
      return false;
    } // if

    this.decrementGuessLeft();

    for (int i = 0; i < guess.length(); i++) {
      char c = guess.charAt(i);
      if (targetWord.getTarget().charAt(i) == c) {
        this.board.set(i, 5 - this.getGuessLeft(), ANSI_GREEN + c + ANSI_RESET);
      } else if (targetWord.getTarget().contains(String.valueOf(c))) {
        this.board.set(i, 5 - this.getGuessLeft(), ANSI_YELLOW + c + ANSI_RESET);
      } else {
        this.board.set(i, 5 - this.getGuessLeft(), c + "");
      } // if/else
    } // for

    return true;
  } // registerGuess(String)

} // class GameLogic
