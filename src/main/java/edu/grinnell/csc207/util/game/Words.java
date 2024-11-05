package edu.grinnell.csc207.util.game;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Describes the wordlist used by the game logic.
 * @author Andrew Fargo
 */
public class Words {
  /**
   * The list of words the game will choose from.
   * Must be a subset of wordlist.
   */
  private Path wordlist;

  /**
   * The list of words the game considers valid.
   * Must be a superset of wordlist.
   */
  private Path checklist;

  /* I'm choosing to store the following redundantly,
     this makes file calculations much easier, but requires
     initialization at runtime. */
  
  /** How long each word is in characters. */
  private int length;

  /** How many words exist in the wordlist. */
  private int wordlistLen;
  
  /** How many words exist in the checklist. */
  private int checklistLen;
  
  public Words(String wordlistPath) {
    this(wordlistPath, wordlistPath);
  } // Words(String)

  public Words(String wordlistPath, String checklistPath) {
    throw new NotImplementedException();
  } // Words(String, String)
  
  /**
   * Gets a random word.
   * @return A random word from the wordlist, exactly in the
   *    form specified by the list.
   */
  public String getRandomWord() {
    throw new NotImplementedException();
  } // getRandomWord()

  /**
   * Checks if a word is present in the checklist.
   * @param word The word to check.
   * @return true if it is present, false otherwise.
   */
  public boolean isValidWord(String word) {
    int lineUB = 0;
    int lineLB = checklistLen;
    int lineMB; 
    BufferedReader clReader = Files.newBufferedReader(checklist);
    clReader.mark();
    while (lineUB > lineLB) {
      clReader.reset();
      lineMB = lineLB + (lineUB - lineLB) / 2;
      clReader.skip(lineMB * (this.length + 1)); // add for newline
      int cmp = word.compareTo(clReader.readline());
      if (cmp > 0) {
	lineLB = lineMB + 1;
      } else if (cmp < 0) {
	lineUB = lineLB;
      } else {
	return true;
      } // if/else
    } // while
    return false;
  } // isValidWord(String)

  /**
   * Gets the word length of the current files.
   * @return The necessary length of each word
   *   in wordlist and checklist.
   */
  public int getWordLength() {
    return this.length;
  } // getWordLength()
} // class Words
