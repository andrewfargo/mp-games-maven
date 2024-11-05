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

    // TODO: Use Words class to get word for this game
    this.targetWord = new Words();
  } // GameLogic()

  // +---------+---------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * toString
   */

  /**
   * registerGuess
   */


} // class GameLogic
