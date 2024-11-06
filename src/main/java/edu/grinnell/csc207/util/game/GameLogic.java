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
  private final Words targetWord;

  // +-------------+---------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a new game.
   */
  public GameLogic() {
    this.board = new MatrixV0<>(GameLogic.DEFAULT_WIDTH, GameLogic.DEFAULT_HEIGHT, " ");

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
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String horizontalLine = "+----".repeat(GameLogic.DEFAULT_WIDTH - 1) + "+\n";

    for (int row = 0; row < GameLogic.DEFAULT_HEIGHT; row++) {
      sb.append(horizontalLine);
      for (int col = 0; col < GameLogic.DEFAULT_WIDTH; col++) {
        sb.append("| ").append(board.get(row, col)).append(" ");
      }
      sb.append("|\n");
    }
    sb.append(horizontalLine);

    return sb.toString();
  } // toString()

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
      int row = 5 - this.getGuessLeft();
      int col = i;
      if (targetWord.getTarget().charAt(i) == c) {
        this.board.set(row, col, ANSI_GREEN + c + ANSI_RESET);
      } else if (targetWord.getTarget().contains(String.valueOf(c))) {
        this.board.set(row, col, ANSI_YELLOW + c + ANSI_RESET);
      } else {
        this.board.set(row, col, c + "");
      }
    } // for

    return true;
  } // registerGuess(String)

  /**
   * Check if the game is over.
   * 
   * @return
   */
  public boolean isGameOver() {
    return this.getGuessLeft() == 0;
  } // isGameOver()

} // class GameLogic
