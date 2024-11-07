package edu.grinnell.csc207.util.game;

import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

public class Scores {
  /** Default save path. */
  private static final Path SAVE_DEFAULT = Path.of(".", "scores.dat");
  
  /** The storage data. Score to frequency. */
  private Map<Integer, Integer> histogram;

  /** Path of the save file. */
  private Path path;

  /** Create an empty save file with default path. */
  public Scores() {
    this(SAVE_DEFAULT);
  } // Scores()

  /**
   * Create or read from a save file.
   * @param saveFile The save file to be created or read from.
   */
  public Scores(Path saveFile) {
    this.histogram = new HashMap<Integer, Integer>();
    this.path = saveFile;
    this.load();
  } // Scores(Path)

  /**
   * Save the current values to disk.
   */
  public void save() {

  }

  /**
   * Load path into current values.
   */
  private void load() {

  }

  /**
   * From the string formatted by toString, generate a map.
   * @param String A string of format specified by toString()
   * @return The map.
   */
  private static Map<Integer, Integer> fromString(String data) {
    return null;
  }

  /**
   * Represents the map as a human-readable and computer-readable string.
   * @return The String representation.
   */
  public String toString() {
    Function<Map.Entry<Integer, Integer>, String> toFormat = (e) ->
      "" + e.getKey() + ": " + e.getValue() + "\n";
    
    return this.histogram.entrySet().stream()
      .sorted(Map.Entry.comparingByKey())
      .parallel().map(toFormat).reduce((s1, s2) -> s1.concat(s2)).get();
  } // toString()

  public void add(int score) {
    
  } // add(int)
} // class Scores
