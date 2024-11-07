package edu.grinnell.csc207.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import edu.grinnell.csc207.util.game.GameLogic;

public class UserInterface {
  static GameLogic game;

  /**
   * The main method for the Wordle game.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    printInstructions(pen);
    String choice;
    boolean breakOutOfLoop = false;
    game = new GameLogic("wordlist.txt", "checklist.txt", "savefile.txt", 0, 6);

    while (!breakOutOfLoop) {
      pen.print(" Type the number 1, 2, or 3 to select an option: ");
      pen.flush();
      choice = eyes.readLine();

      switch (choice) {
        case "1":
          // Play the game
          playGame(pen, eyes);
          break;
        case "2":
          // See stats
          pen.printf(game.scores.toString());
          break;
        case "3":
          // Quit
          pen.println("Thanks for playing WORDLE!");
          breakOutOfLoop = true;
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
   * Print the welcome message for the Wordle game.
   */
  private static void printInstructions(PrintWriter pen) {
    pen.println("""
        +--------------------+
        | Welcome to WORDLE! |
        +--------------------+

        Inspired by the famous NYT game, Wordle, this is a game
        where you have to guess a five-letter word.

        Your game board is a 5x6 grid.

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

        +----+----+----+----+
        | H | \u001B[33mE\u001B[0m | \u001B[33mL\u001B[0m | \u001B[32mL\u001B[0m | O |
        +----+----+----+----+
        \u001B[33mE\u001B[0m and \u001B[33mL\u001B[0m are in the word but in the wrong place.
        The second \u001B[32mL\u001B[0m is in the word and in the right place.
        The other letters H and O are not in the word.

        +-----------------------------------------------+
        | These are the options to go from here:        |
        |                                               |
        |  [1] Play!                                    |
        |  [2] See Stats (distribution of your score)   |
        |  [3] Instructions                             |
        |  [4] Quit                                     |
        +-----------------------------------------------+
        """);
  } // printInstructions()

  private static void playGame(PrintWriter pen, BufferedReader eye) throws IOException {
    try (pen) {
      game.reset();
      pen.println("The target word has 5 letters.");

      boolean shouldRun = true;
      while (shouldRun) {
        pen.print(game.toString());
        pen.printf("\nYou have %d guesses left.", game.getGuessesLeft());
        pen.print('\n');
        pen.print("Enter your guess: ");
        pen.flush();

        switch (game.registerGuess(eye.readLine())) {
          case CONTINUE:
            break;
          case REDO:
            pen.printf("Invalid input.\n");
            break;
          case WIN:
            pen.print(game.toString());
            pen.printf("Congrats!\n");
            // pen.printf(game.scores.toString());
            shouldRun = false;
            break;
          case LOSE:
            pen.print(game.toString());
            pen.printf("You lose!\n");
            shouldRun = false;
            break;
        } // switch/case
      } // while
      pen.close();
      eye.close();
    } // try
  } // playGame()
}
