package edu.grinnell.csc207.util.game;

import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;
import java.io.IOException;

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
   * Possible returns on word entry.
   */
  public enum GameState {
    /** Continue accepting input. */
    CONTINUE,
    /** Word invalid, try again. */
    REDO,
    /** Word is target. */
    WIN,
    /** Ran out of guesses. */
    LOSE
  } // enum GameState
  
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
   * The current state of the board.
   */
  private Matrix<String> board;

  /**
   * Handles generation and validation of words.
   */
  private Words words;

  /**
   * The number of guesses the player is allowed.
   */
  private int guessesAllowed;
  
  /**
   * The number of guesses the player has made.
   */
  private int guessesMade;

  /**
   * Current target word.
   */
  private String target;

  /**
   * Keeps track of current wins.
   */
  public Scores scores;


  // +-------------+---------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a new game.
   */
  public GameLogic(String wordlist, String checklist, String savefile, long seed, int guesses)
  throws IOException {
    this.words = new Words(wordlist, checklist, seed);
    this.guessesAllowed = guesses;
    reset();
  } // GameLogic()

  // +---------+---------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Reset and select a new word.
   */
  public final void reset() {
    this.guessesMade = 0;
    this.target = words.next();
    this.board = new MatrixV0<String>(this.target.length(), this.guessesAllowed, " ");
  } // reset()

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String horizontalLine = "+----".repeat(this.target.length() - 1) + "+\n";

    for (int row = 0; row < this.guessesAllowed; row++) {
      sb.append(horizontalLine);
      for (int col = 0; col < this.target.length(); col++) {
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
   * @return Current game state.
   * @see edu.grinnell.csc207.util.game.GameState
   */
  public GameState registerGuess(String guess) {
    guess = guess.toLowerCase();

    if (!words.test(guess) || guess.length() != target.length()) {
      return GameState.REDO;
    } // if
    
    this.guessesMade++;
    for (int i = 0; i < guess.length(); i++) {
      char c = guess.charAt(i);

      int row = guessesMade;
      int col = i;
      if (target.charAt(i) == c) {
        this.board.set(row, col, ANSI_GREEN + c + ANSI_RESET);
      } else if (target.indexOf(c) != -1) {
        this.board.set(row, col, ANSI_YELLOW + c + ANSI_RESET);
      } else {
        this.board.set(row, col, c + "");
      } // if/else
    } // for

    if (guess.equals(target)) {
      scores.add(guessesMade);
      return GameState.WIN;
    } else if (this.isGameOver()) {
      return GameState.LOSE;
    } else {
      return GameState.CONTINUE;
    } // if/else
  } // registerGuess(String)

  /**
   * Check if the game is over.
   * 
   * @return
   */
  public boolean isGameOver() {
    return this.guessesMade == this.guessesAllowed;
  } // isGameOver()

  public int getGuessesLeft() {
    return this.guessesAllowed - this.guessesMade;
  } // getGuessesLeft()
  
} // class GameLogic


