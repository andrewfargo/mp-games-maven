package edu.grinnell.csc207.util.game;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Random;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Manages operations related to producing and validating words.
 * An unordered list of words available as answers (wordlist),
 * and valid guesses (checklist) are provided.
 * @author Andrew Fargo
 */
public class Words implements Iterator<String>, Predicate<String> {
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

  /** How many words exist in the wordlist. */
  private int length;

  /** The random number generator. */
  private Random rng;

  /**
   * Initializes Words with just a wordlist. Assumes checklist is wordlist.
   * @param wordlistPath The path string to the wordlist.
   * @param seed The random seed.
   * @throws IOException if wordlistPath is not a readable file.
   */
  public Words(String wordlistPath, long seed) throws IOException {
    this(wordlistPath, wordlistPath, seed);
  } // Words(String)

  /**
   * Initializes Words with a wordlist and a checklist.
   * @param wordlistPath The path string to the wordlist file.
   * @param checklistPath The path string to the checklist file.
   * @param seed The random seed.
   * @throws IOException if wordlistPath or checklistPath are not readable files.
   */
  public Words(String wordlistPath, String checklistPath, long seed) throws IOException {
    // Initialize the paths
    // ====================
    this.wordlist = Path.of(wordlistPath);
    this.checklist = Path.of(checklistPath);

    if (!Files.isReadable(this.wordlist)) {
      throw new IOException("Wordlist not readable.");
    } // if
    if (!Files.isReadable(this.wordlist)) {
      throw new IOException("Checklist not readable.");
    } // if

    this.length = (int) Files.lines(this.wordlist)
      .limit(Integer.MAX_VALUE).parallel().count();

    this.rng = new Random(seed);
    
  } // Words(String, String)

  /**
   * Validates that the file is still readable.
   */
  public boolean hasNext() {
    return Files.isReadable(this.wordlist);
  } // hasNext()
  
  /**
   * Gets the next word.
   * @return A pseudorandom word from the wordlist, exactly in the
   *    form specified by the list.
   * @throws RuntimeException if the file becomes invalid.
   */
  public String next() {
    int index = rng.nextInt() % this.length;
    try {
      return Files.lines(this.wordlist).skip(index).findFirst().get();
    } catch (Exception e) {
      throw new RuntimeException("IO ERROR: " + this.wordlist + " File no longer valid.");
    } // try/catch
  } // next()

  /**
   * Checks if a word is present in the checklist; conducts a parallelized linear search.
   * @param word The word to check.
   * @return true if it is present, false otherwise.
   * @throws RuntimeException if the file becomes invalid.
   */
  @Override
  public boolean test(String word) {
    try {
      return Files.lines(this.checklist).parallel().anyMatch((e) -> word.equals(e));
    } catch (Exception e) {
      throw new RuntimeException("IO ERROR: " + this.checklist + " File no longer valid.");
    } // try/catch
  } // pred(String)
} // class Words
