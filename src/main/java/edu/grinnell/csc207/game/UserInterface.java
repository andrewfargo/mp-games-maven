package edu.grinnell.csc207.game;

import java.io.PrintWriter;

public class UserInterface {
  /**
   * The main method for the Wordle game.
   */
  public static void main(String[] args) {
    printInstructions(new PrintWriter(System.out, true));
  } // main

  /**
   * Print the welcome message for the Wordle game.
   */
  private static void printInstructions(PrintWriter pen) {
    pen.println("""
                Welcome to WORDLE!

                Inspired by the famous NYT game, Wordle, this is a game
                where you have to guess a five-letter word. You have six
                attempts with feedback given for each guess in the form
                of coloured tiles indicating when letters match or occupy
                the correct position.

                Type the number of the option you want to choose.:

                [1] Play!
                [2] See Stats (distribution of your score)
                [3] Instructions (this screen)
                [4] Quit
                
                Your game board is a 5x6 grid.

                You have total of 6 guesses to guess the target word.

                When prompted, enter an English 5-letter word.

                Upon entering a guess, you will receive feedback in the form
                of colored tiles. The colors are as follows:

                * If the word guessed has a letter in the correct place, the 
                letter will be presented as green
                * If the word guessed has a letter that is contained in the 
                answer but the wrong place, the letter will be presented as yellow
                * If the letter is not contained in the answer, it will be 
                presented as white
                """);
  } // printInstructions()
}