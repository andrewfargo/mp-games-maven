# Abstract games

A mini-project for Grinnell's CSC-207.

## Authors:

- Khanh Do
- Andrew Fargo
- Samuel A. Rebelsky (Starter code)

## Acknowledgements:

Implementation of Matrices comes from Khanh's Mini Project 6 submission, you may view the code at <https://github.com/khanhdo05/mp-matrices-maven>.

## Source:

This code may be found at <https://github.com/andrewfargo/mp-games-maven>. It is based on code found at <https://github.com/Grinnell-CSC207/mp-games-maven>.

# Wordle

Welcome to the Wordle! This is a command-line implementation of the popular word-guessing game.

## Introduction

Wordle is a word-guessing game where the player has to guess a 5-letter word within 6 attempts. After each guess, the player receives feedback in the form of colored tiles indicating whether the letters are in the correct position, present in the word but in the wrong position, or not present in the word at all.

## Features

- Command-line interface
- Random word selection from a predefined list
- Feedback on guesses with colored tiles
- Option to view game instructions and statistics

## Installation

### Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) version 11 or later.
- You have installed [Apache Maven](https://maven.apache.org/install.html).
- You have a terminal or command prompt to run the game.

### To install and run the Wordle game, follow these steps:

1. Clone the repository:

   ```sh
   git clone https://github.com/andrewfargo/mp-games-maven
   cd mp-games-maven
   ```

2. Compile the Java files:

   ```sh
   mvn clean compile package -q
   ```

3. Run the game:
   ```sh
   mvn exec:java
   ```

### Usage

When you run the game, you will be presented with the following options:

1. Play
2. See Stats
3. Instructions
4. Quit

To select an option, type the corresponding number and press Enter.

## Playing the Game

- You have 6 attempts to guess the 5-letter target word.
- After each guess, you will receive feedback:

  - **GREEN**: The letter is in the correct position.
  - **YELLOW**: The letter is in the word but in the wrong position.
  - **WHITE**: The letter is not in the word.

    ![example](image.png)
