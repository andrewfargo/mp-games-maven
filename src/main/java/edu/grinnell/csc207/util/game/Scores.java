package edu.grinnell.csc207.util.game;

import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.function.Function;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a score-frequency tracking system.
 * Handles saving and loading of these scores.
 * @author Andrew Fargo
 */
public class Scores {
  /** The storage data. Score to frequency. */
  private Map<Integer, Integer> histogram;

  /** Path of the save file. */
  private Path path;

  /**
   * Create or read from a save file.
   * @param saveFile The save file to be created or read from.
   */
  public Scores(Path saveFile) throws IOException {
    this.histogram = new HashMap<Integer, Integer>();
    this.path = saveFile;
    if (Files.isReadable(saveFile)) {
      this.load();
    } else {
      this.save(); // Necessarily empty, creates
    } // if/else
  } // Scores(Path)

  /**
   * Save the current values to disk.
   */
  public void save() throws IOException {
    Files.writeString(this.path, this.toString(), StandardOpenOption.WRITE,
                      StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  } // save()

  /**
   * Load path into current values.
   */
  private void load() throws IOException {
    this.histogram.clear();
    Scanner sc = new Scanner(this.path);
    while (sc.hasNextInt()) {
      try {
        int guesses = sc.nextInt();
        int freq = sc.nextInt();
        this.histogram.put(guesses, freq);
      } catch (Exception e) {
        sc.close();
        throw new IOException("Save file corrupt when loading: " + e.getMessage());
      } // try/catch
    } // while
    sc.close();
  } // load()

  /**
   * Represents the map as a human-readable and computer-readable string.
   * @return The String representation.
   */
  @Override
  public String toString() {
    Function<Map.Entry<Integer, Integer>, String> toFormat = (e) ->
        "" + e.getKey() + "\t" + e.getValue() + "\n";
    // This series of stream operations makes the formatting significantly
    // easier compared to imperative methods.
    return this.histogram.entrySet().stream()
      .sorted(Map.Entry.comparingByKey())
      .parallel().map(toFormat).reduce("", (s1, s2) -> s1.concat(s2));
  } // toString()

  /**
   * Adds a score to the histogram.
   * @param score The amount of guesses it took to win.
   */
  public void add(int score) {
    this.histogram.compute(score, (k, v) -> (v == null) ? 1 : v + 1);
  } // add(int)
} // class Scores
