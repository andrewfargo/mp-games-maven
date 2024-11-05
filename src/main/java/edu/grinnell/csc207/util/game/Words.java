package edu.grinnell.csc207.util.game;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
import java.io.IOException;
import java.util.Random;
import java.io.BufferedReader;

/**
 * Manages operations related to producing and validating words.
 * An unordered list of words available as answers (wordlist),
 * and valid guesses (checklist) are provided.
 * As of Nov 5, 2024, wordlist is bounded by INT_MAX words,
 * but checklist may be arbitrarily large according to system limits.
 * @author Andrew Fargo
 */
public class Words {
  private static final int WORDLEN_LIMIT = 64;
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

  /** Word index permutation. */
  private int[] indices;

  /** Current index. */
  private int index;

  /**
   * Ensures every line in file specified is of length this.length.
   * @param path The file.
   * @throws IOException If this is not the case.
   */
  private void validateLengths(Path path) throws IOException {
    if (Files.lines(path).parallel().anyMatch((line) -> line.length() != this.length)) {
      throw new IOException("File " + path + " has inconsistent sizes.");
    } // if
  } // validateLengths(BufferedReader)

  /**
   * Initializes this.indices to an array of wordlistLen values.
   * This array is then shuffled to provide a permutation of words
   * delivered to the player.
   * @param seed The random seed from the save file.
   */
  private void generateIndices(long seed) {
    Random rng = new Random(seed);
    this.indices = IntStream.range(0, wordlistLen).toArray();
    // Fisher-Yates from Knuth TAOCP v.2 Algorithm P (Shuffling)
    for (int i = wordlistLen - 1; i > 0; i--) {
      int j = rng.nextInt(i);
      int temp = this.indices[i];
      indices[i] = indices[j];
      indices[j] = temp;
    } // for
  } // generateIndices(int)

  
  /**
   * Initializes Words with just a wordlist. Assumes checklist is wordlist.
   * @param wordlistPath The path string to the wordlist.
   * @param seed The random seed according to the save file.
   * @param numGames The number of games played by the save file.
   */
  public Words(String wordlistPath, long seed, int numGames) throws IOException {
    this(wordlistPath, wordlistPath, seed, numGames);
  } // Words(String)

  /**
   * Initializes Words with a wordlist and a checklist.
   * @param wordlistPath The path string to the wordlist file.
   * @param checklistPath The path string to the checklist file.
   * @param seed The random seed according to the savefile.
   * @param numGames The number of games played by the save file.
   */
  public Words(String wordlistPath, String checklistPath, long seed, int numGames) throws IOException {
    // Initialize the paths
    // ====================
    this.wordlist = Path.of(wordlistPath);
    this.checklist = Path.of(checklistPath);

    // Validate the files
    // ==================
    
    BufferedReader wordReader = Files.newBufferedReader(this.wordlist);
    wordReader.mark(Words.WORDLEN_LIMIT);
    this.length = wordReader.readLine().length();
    wordReader.reset();
    wordReader.close();

    this.validateLengths(this.wordlist);
    this.validateLengths(this.checklist);

    this.wordlistLen = (int) Files.lines(this.wordlist)
      .limit(Integer.MAX_VALUE).parallel().count();
    
    // Generate an index permutation
    // =============================

    this.generateIndices(seed);
    this.index = numGames;
  } // Words(String, String)
  
  /**
   * Gets the next word.
   * @return A pseudorandom word from the wordlist, exactly in the
   *    form specified by the list.
   */
  public String getNextWord() throws IOException {
    BufferedReader wlReader = Files.newBufferedReader(wordlist);
    int wordno = this.indices[this.index++];
    wlReader.skip((this.length + 1) * wordno);
    String ret = wlReader.readLine();
    wlReader.close();
    return ret;
  } // getRandomWord()

  /**
   * Checks if a word is present in the checklist; conducts a parallelized linear search.
   * @param word The word to check.
   * @return true if it is present, false otherwise.
   */
  public boolean isValidWord(String word) throws IOException {
    return Files.lines(this.checklist).parallel().findAny().isPresent();
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
