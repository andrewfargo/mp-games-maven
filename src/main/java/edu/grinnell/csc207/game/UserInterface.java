package edu.grinnell.csc207.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Optional;

import edu.grinnell.csc207.util.game.GameLogic;
import edu.grinnell.csc207.util.game.GameOptions;

/**
 * Handles all input and output from the user.
 * @author Khanh Do
 * @author Andrew Fargo
 */
public class UserInterface {
  /**
   * The game logic reference.
   */
  static GameLogic game;
  /**
   * Reference to the options the game should be using.
   */
  static GameOptions opts;

  /**
   * Instructions for first time players.
   */
  public static final String INSTRUCTIONS = """
        +--------------------+
        | Welcome to WORDLE! |
        +--------------------+

        Inspired by the famous NYT game, Wordle, this is a game
        where you have to guess a five-letter word.

        By default, your game board is a 5x6 grid.

        You have total of 6 guesses to guess the target word.

        When prompted, enter an English 5-letter word.

        Upon entering a guess, you will receive feedback in the form
        of colored tiles. The colors are as follows:

        * If the word guessed has a letter in the correct place, the
        letter will be presented as GREEN
        * If the word guessed has a letter that is contained in the
        answer but the wrong place, the letter will be presented as YELLOW
        * If the letter is not contained in the answer, it will be
        presented as WHITE

        Example:

        +---+---+---+---+---+
        | H | \u001B[33mE\u001B[0m | \u001B[33mL\u001B[0m | \u001B[32mL\u001B[0m | O |
        +---+---+---+---+---+
        \u001B[33mE\u001B[0m and \u001B[33mL\u001B[0m are in the word but in the wrong place.
        The second \u001B[32mL\u001B[0m is in the word and in the right place.
        The other letters H and O are not in the word.
      """;

  /**
   * Options available to players.
   */
  public static final String OPTIONS = """
        +-----------------------------------------------+
        | These are the options to go from here:        |
        |                                               |
        |  [1] Play!                                    |
        |  [2] See Stats (distribution of your score)   |
        |  [3] Instructions                             |
        |  [4] Quit                                     |
        |  [5] Configure...                             |
        +-----------------------------------------------+
      """;

  /**
   * The main method for the Wordle game.
   * @param args Ignored
   * @throws IOException If files used are invalid.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    UserInterface.opts = new GameOptions();

    pen.printf(UserInterface.INSTRUCTIONS);
    pen.println("Using seed: " + UserInterface.opts.getSeed());

    String choice;
    boolean shouldRun = true;
    game = new GameLogic(UserInterface.opts);

    while (shouldRun) {
      pen.printf(UserInterface.OPTIONS);
      pen.print(" Type the number 1-5 to select an option: ");
      pen.flush();
      choice = eyes.readLine();

      switch (choice) {
        case "1":
          // Play the game
          playGame(pen, eyes);
        case "2":
          // See stats
          pen.println("Scores (Guesses, Frequency):");
          pen.printf(game.getScores().toString());
          break;
        case "3":
            // Instructions
          pen.print(UserInterface.INSTRUCTIONS);
          break;
        case "4":
            // Quit
          game.getScores().save();
          pen.println("Thanks for playing WORDLE!");
          shouldRun = false;
          break;
        case "5":
          game.getScores().save();
          try {
            GameOptions builder = new GameOptions();
            pen.printf("Enter wordlist file (or empty if unchanged): ");
            builder.setWordlist(eyes.readLine());
            pen.printf("Enter checklist file (or empty if unchanged): ");
            builder.setChecklist(eyes.readLine());
            pen.printf("Enter save file (or empty if unchanged): ");
            builder.setSavefile(eyes.readLine());
            pen.printf("Enter seed (or empty if random; only scores random): ");
            String seed = eyes.readLine();
            builder.setSeed(seed.isEmpty() ? Optional.empty()
                            : Optional.of(Long.valueOf(seed)));
            pen.printf("Enter guesses allowed (or empty if unchanged): ");
            String guesses = eyes.readLine();
            if (!guesses.isEmpty()) {
              builder.setGuesses(Integer.valueOf(guesses));
            } // if
            UserInterface.opts = builder;
            game = new GameLogic(UserInterface.opts);
          } catch (Exception e) {
            pen.println(e.getMessage());
            break;
          } // try/catch
          break;
        default:
          pen.printf("Unexpected command: '%s'. Please try again.\n\n", choice);
          break;
      } // switch
    } // while

    pen.close();
    eyes.close();
  } // main
  /**
   * One iteration of the game.
   * @param pen Output
   * @param eye Input
   * @throws IOException if configured files are invalid.
   */
  private static void playGame(PrintWriter pen, BufferedReader eye) throws IOException {
    game.reset();

    boolean shouldRun = true;
    while (shouldRun) {
      pen.printf("%s\nYou have %d guesses left.\n Enter your guess: ",
                 game, game.getGuessesLeft());

      switch (game.registerGuess(eye.readLine())) {
        case REDO:
          pen.printf("Invalid input.\n");
          break;
        case WIN:
          pen.print(game.toString());
          pen.printf("Congrats!\n");
          shouldRun = false;
          break;
        case LOSE:
          pen.print(game.toString());
          pen.printf("You lose!\n");
          shouldRun = false;
          break;
        default:
          break;
      } // switch/case
    } // while
  } // playGame()
} // class UserInterface
