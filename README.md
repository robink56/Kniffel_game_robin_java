# Kniffel_game_robin_java


A desktop implementation of the classic Kniffel/Yahtzee dice game written in Java.

## Features

- Java Swing-based graphical user interface
- Multiple player support
- Dice rolling with hold/select functionality
- Score calculation for all major Kniffel categories
- Player score table
- Bonus and total score calculation
- Debug mode for testing dice combinations

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- MVP / Presenter-based structure

## Project Structure

```text
src/
├── main/
│   └── Main.java
├── kniffelLogik/
│   ├── Kombi.java
│   ├── PunkteRechner.java
│   ├── Spieler.java
│   └── WuerfelRechner.java
├── kniffelPresenter/
│   └── SpielPresenter.java
└── kniffelGUI/
    └── GUI classes
