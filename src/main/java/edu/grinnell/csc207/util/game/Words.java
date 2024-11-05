package edu.grinnell.csc207.util.game;

public class Words {
  String target;

  public Words() {
    target = getRandomWord();
  } // Words()

  // getRandomWord()
  private String getRandomWord() {
    return "HELLO";
  } // getRandomWord()

  public String getTarget() {
    return target;
  } // getTarget()

  // checkValidEnglishWord()
  public boolean checkValidEnglishWord(String word) {
    return (word.length() == 5); // TODO: other logic
  } // checkValidEnglishWord()
} // class Words
