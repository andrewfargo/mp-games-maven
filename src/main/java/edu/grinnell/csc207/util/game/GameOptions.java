package edu.grinnell.csc207.util.game;

import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.io.IOException;

/**
 * Represents user configuration of the game.
 * @author Andrew Fargo
 */
public class GameOptions {
  /** Path to the word bank. */
  private Path wordfile;
  /** Path to the list of valid words. */
  private Path checkfile;
  /** Path to the save file. */
  private Path savefile;
  /** Seed to use for randomization. */
  private long seed;
  /** Number of guesses allowed before game over. */
  private int guesses;
  /** True if random seed is used, false if fixed seed. */
  private boolean shouldScore;
  /**
   * Constructor for default values.
   * Should use the setters for other values.
   * @throws IOException If files aren't valid.
   */
  public GameOptions() throws IOException {
    this.setWordlist("wordlist.txt");
    this.setChecklist("checklist.txt");
    this.setSavefile("savefile.txt");
    this.setGuesses(6);
    this.setSeed(Optional.empty());
  } // GameOptions()

  /**
   * Ensures the path is readable, throws an error if not.
   *
   * @param path The path.
   * @throws IOException if the file is not readable.
   */
  private static void validatePath(Path path) throws IOException {
    if (!Files.isReadable(path)) {
      throw new IOException("File unreadable or does not exist: " + path);
    } // if
  } // validatePath(Path)

  /**
   * Mutator for the word list.
   * @param path Path to the word list.
   * @throws IOException if the path is invalid.
   */
  public void setWordlist(String path) throws IOException {
    if (path.isEmpty()) {
      return;
    } // if
    this.wordfile = Path.of(path);
    GameOptions.validatePath(this.wordfile);
  } // setWordlist(String)

  /**
   * Mutator for the checklist.
   * @param path Path to the checklist.
   * @throws IOException if the path is invalid.
   */
  public void setChecklist(String path) throws IOException {
    if (path.isEmpty()) {
      return;
    } // if
    this.checkfile = Path.of(path);
    GameOptions.validatePath(this.checkfile);
  } // setChecklist(String)

  /**
   * Sets the save file.
   * @param path Path to the save file.
   */
  public void setSavefile(String path) {
    if (path.isEmpty()) {
      return;
    } // if
    this.savefile = Path.of(path);
  } // setSavefile(String)

  /**
   * Sets the number of guesses allowed.
   * @param guessesProvided The number specified.
   */
  public void setGuesses(int guessesProvided) {
    this.guesses = guessesProvided;
  } // setGuesses(int)

  /**
   * Sets the seed, or generates a random seed if empty.
   * Sets whether or not the game should be scored,
   * accordingly.
   * @param seedOrNone Optional representing the seed or nothing.
   */
  public void setSeed(Optional<Long> seedOrNone) {
    this.shouldScore = seedOrNone.isEmpty();
    Random rng = new Random();
    this.seed = seedOrNone.isPresent() ? seedOrNone.get() : rng.nextLong();
  } // setSeed(Optional<Long>)

  /**
   * Accessor for word list.
   * @return The word list path.
   */
  public Path getWordlist() {
    return this.wordfile;
  } // getWordlist()

  /**
   * Accessor for check list.
   * @return The check list path.
   */
  public Path getChecklist() {
    return this.checkfile;
  } // getChecklist()

  /**
   * Accessor for save file.
   * @return The save file path.
   */
  public Path getSavefile() {
    return this.savefile;
  } // getSavefile()

  /**
   * Accessor for the seed.
   * @return The seed used.
   */
  public long getSeed() {
    return this.seed;
  } // getSeed()

  /**
   * Accessor for the guess count.
   * @return The number of guesses allowed.
   */
  public int getGuesses() {
    return this.guesses;
  } // getGuesses

  /**
   * Accessor for score validity.
   * @return true if the score should be counted, false otherwise.
   */
  public boolean getValid() {
    return this.shouldScore;
  } // getValid()
} // class GameOptions
