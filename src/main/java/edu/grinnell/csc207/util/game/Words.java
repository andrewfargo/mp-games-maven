package edu.grinnell.csc207.util.game;

/**
 * Describes the wordlist used by the game logic.
 * @author Andrew Fargo
 */
public class Words {
  /**
   * The list of words the game will choose from.
   * Must be a subset of wordlist.
   */
  private File wordlist;

  /**
   * The list of words the game considers valid.
   * Must be a superset of wordlist.
   */
  private File checklist;
  
  /** How long each word is. */
  private int length;

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
    throw new NotImplementedException();
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
