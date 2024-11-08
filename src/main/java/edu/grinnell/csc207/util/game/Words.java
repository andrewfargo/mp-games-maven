package edu.grinnell.csc207.util.game;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Manages operations related to producing and validating words. An unordered list of words
 * available as answers (wordlist), and valid guesses (checklist) are provided.
 *
 * @author Andrew Fargo
 */
public class Words implements Iterator<String>, Predicate<String> {
  /** How many words exist in the wordlist. */
  private int length;

  /** The random number generator. */
  private Random rng;

  /** The game options. */
  private GameOptions opts;

  /**
   * Initializes Words with a wordlist and a checklist.
   *
   * @param options Game options.
   * @throws IOException if wordlistPath or checklistPath are not readable files.
   */
  public Words(GameOptions options) throws IOException {
    // Initialize the paths
    // ====================
    this.opts = options;
    this.length = (int) Files.lines(this.opts.getWordlist())
      .limit(Integer.MAX_VALUE).parallel().count();

    this.rng = new Random(this.opts.getSeed());
  } // Words(String, String)

  /**
   * Validates that the file is still readable.
   * @return true if the file is still readable.
   */
  public boolean hasNext() {
    return Files.isReadable(this.opts.getWordlist());
  } // hasNext()

  /**
   * Gets the next word.
   *
   * @return A pseudorandom word from the wordlist, exactly in the form specified by the list.
   * @throws RuntimeException if the file becomes invalid.
   */
  @Override
  public String next() {
    int index = rng.nextInt(this.length);
    try {
      return Files.lines(this.opts.getWordlist()).skip(index)
      .findFirst().get().toUpperCase();
    } catch (IOException e) {
      throw new RuntimeException("Unrecoverable IO ERROR: "
                                 + this.opts.getWordlist()
                                 + " File no longer valid: "
                                 + e.getMessage());
    } // try/catch
  } // next()

  /**
   * Checks if a word is present in the checklist; conducts a parallelized linear search.
   *
   * @param word The word to check.
   * @return true if it is present, false otherwise.
   * @throws RuntimeException if the file becomes invalid.
   */
  @Override
  public boolean test(String word) {
    try {
      String lower = word.toLowerCase();
      return Files.lines(this.opts.getChecklist()).parallel()
            .anyMatch((e) -> lower.equals(e));
    } catch (Exception e) {
      throw new RuntimeException("Unrecoverable IO ERROR: "
                                 + this.opts.getChecklist()
                                 + " File no longer valid: "
                                 + e.getMessage());
    } // try/catch
  } // pred(String)
} // class Words
