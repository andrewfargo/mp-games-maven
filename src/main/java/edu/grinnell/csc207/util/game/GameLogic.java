package edu.grinnell.csc207.util.game;

import java.io.IOException;

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
  private final Words words;

  /**
   * Handles options passed to the game logic.
   */
  private final GameOptions opts;

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
  private final Scores scores;

  // +-------------+---------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a new game.
   *
   * @param options The options used.
   */
  public GameLogic(GameOptions options) throws IOException {
    this.words = new Words(options);
    this.opts = options;
    this.scores = new Scores(this.opts.getSavefile());
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
    this.target = (words.next()).toUpperCase();
    this.board = new MatrixV0<>(this.target.length(), this.opts.getGuesses(), " ");
  } // reset()

  /**
   * Represents the game board as a string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String horizontalLine = "+---".repeat(this.target.length()) + "+\n";

    for (int row = 0; row < this.opts.getGuesses(); row++) {
      sb.append(horizontalLine);
      for (int col = 0; col < this.target.length(); col++) {
        sb.append("| ").append(board.get(row, col)).append(" ");
      } // for
      sb.append("|\n");
    } // for
    sb.append(horizontalLine);

    return sb.toString();
  } // toString()

  /**
   * Formats a single guess, coloring the letters appropriately.
   *
   * @param guess The guessed word
   * @return A string of the guessed words characters outfixed with ANSI codes
   */
  private String[] formatGuess(String guess) {
    char[] guessArray = guess.toUpperCase().toCharArray();
    char[] targetArray = this.target.toCharArray();
    String[] formatArray = new String[target.length()];
    // Perform three passes, first checking for exact matches,
    // then checking for slight matches, then filling the rest.
    // Blocks out each letter as it is consumed, avoiding duplicate matches.
    //
    // E.g. if the target is `plead` and the guess is `apple`
    // Only one `p` lights up.

    for (int i = 0; i < targetArray.length; i++) {
      if (targetArray[i] == guessArray[i]) {
        formatArray[i] = ANSI_GREEN + guessArray[i] + ANSI_RESET;
        guessArray[i] = '#';
      } // if
    } // for
    for (int i = 0; i < targetArray.length; i++) {
      if (target.indexOf(guessArray[i]) != -1) {
        formatArray[i] = ANSI_YELLOW + guessArray[i] + ANSI_RESET;
        guessArray[i] = '#';
      } // if
    } // for
    for (int i = 0; i < targetArray.length; i++) {
      if (guessArray[i] != '#') {
        formatArray[i] = String.valueOf(guessArray[i]);
      } // if/else
    } // for
    return formatArray;
  } // formatGuess(String)

  /**
   * Register a guess into the board.
   *
   * @param guess User's input
   * @return Current game state.
   * @see edu.grinnell.csc207.util.game.GameState
   */
  public GameState registerGuess(String guess) {
    if (!words.test(guess) || guess.length() != target.length()) {
      return GameState.REDO;
    } // if

    String[] formatArray = formatGuess(guess);
    for (int i = 0; i < guess.length(); i++) {
      board.set(this.guessesMade, i, formatArray[i]);
    } // for

    this.guessesMade++;

    if (guess.toUpperCase().equals(target)) {
      if (this.opts.getValid()) {
        scores.add(this.guessesMade);
      } // if
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
   * @return Whether or not the game should finish.
   */
  public boolean isGameOver() {
    return this.guessesMade == this.opts.getGuesses();
  } // isGameOver()

  /**
   * Calculates and returns the guesses left before game over.
   *
   * @return The guesses left.
   */
  public int getGuessesLeft() {
    return this.opts.getGuesses() - this.guessesMade;
  } // getGuessesLeft()

  /**
   * Scores getter.
   *
   * @return The scores object
   */
  public Scores getScores() {
    return this.scores;
  } // getScores()
} // class GameLogic


