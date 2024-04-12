Sure, here's the updated section on how to include the StdDraw library in your project:

---

## How to Use
1. Clone or download the repository to your local machine.
2. Make sure you have Java installed on your system.
3. Compile the `BingoGame.java` file.
   ```
   javac BingoGame.java
   ```
4. Include the `StdDraw` library in your project:
   - Download the `stdlib.jar` file from [here](https://introcs.cs.princeton.edu/java/stdlib/).
   - In your IntelliJ project, right-click on your project name in the project explorer.
   - Select "Open Module Settings."
   - In the dialog that opens, select "Libraries" from the left menu.
   - Click the "+" icon and choose "Java."
   - Navigate to the location where you downloaded `stdlib.jar` and select it.
   - Click "OK" to add the library to your project.
5. Create an input file named `BingoGameInput.txt` with the following format:
   ```
   <number of players>
   <template numbers>
   ```
   Replace `<number of players>` with the desired number of players, and `<template numbers>` with the bingo card template. Each number in the template should be separated by whitespace.
6. Run the program.
   ```
   java BingoGame
   ```

## Program Output
- The program will display the bingo cards for each player in the console.
- During the game, it will print the number drawn for each turn and indicate the winner(s) when the game ends.

## Contributors
- [Alexandros Vasileiadis](https://github.com/Alexandros-Vasileiadis) - Author

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Let me know if you need further assistance!
