package edu.grinnell.csc207.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import edu.grinnell.csc207.util.game.GameLogic;

public class UserInterface {
  /**
   * The main method for the Wordle game.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    printInstructions(pen);
    String choice;
    boolean breakOutOfLoop = false;

    while (!breakOutOfLoop) {
      pen.print(" Type the number 1, 2, 3, or 4 to select an option: ");
      pen.flush();
      choice = eyes.readLine();

      switch (choice) {
        case "1":
          // Play the game
          playGame(pen, eyes);
          breakOutOfLoop = true;
          break;
        case "2":
          // See stats
          break;
        case "3":
          // Print instructions
          printInstructions(pen);
          break;
        case "4":
          // Print instructions
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
      GameLogic game = new GameLogic();
      String guess;
      pen.println("The target word has 5 letters.");
      pen.print(game.toString());
      while (!game.isGameOver()) {
        pen.printf("You have %d guesses left.", game.getGuessLeft());
        pen.print('\n');
        pen.print("Enter your guess: ");
        pen.flush();
        if (game.registerGuess(guess = eye.readLine())) {
          pen.print(game.toString());
        } // if
      } // while

    } // try
  } // playGame()
}
