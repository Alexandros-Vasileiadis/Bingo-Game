# Bingo Game

## Overview
This program simulates a bingo game. It takes the number of players from an input file, generates unique bingo cards for each player based on a template, and starts the game. The program randomly selects numbers, and players mark off those numbers on their cards. The winner is the first player to mark off all numbers on one of their cards.

## How to Use
1. Clone or download the repository to your local machine.
2. Make sure you have Java installed on your system.
3. Compile the `BingoGame.java` file.
   ```
   javac BingoGame.java
   ```
4. Create an input file named `BingoGameInput.txt` (a prefered type of file is already created) with the following format:
   ```
   <number of players>
   <template numbers>
   ```
   Replace `<number of players>` with the desired number of players, and `<template numbers>` with the bingo card template. Each number in the template should be separated by whitespace.
5. Run the program.
   ```
   java BingoGame
   ```

## Contributors
- [Alexandros Vasileiadis](https://github.com/yourusername) - Author

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
