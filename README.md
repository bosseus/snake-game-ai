# AI Snake Game

A Java implementation of the classic Snake game with an AI player using the A* pathfinding algorithm.

## Features

- Classic Snake gameplay with arrow key controls
- AI-controlled mode (toggle with 'A' key)
- Advanced pathfinding using A* algorithm
- Score tracking
- Clean and modern GUI using Java Swing
- Grid-based movement system
- Survival strategies when no direct path to food exists

## Controls

- Arrow Keys: Control snake direction in manual mode
- A: Toggle AI control
- Space: Restart game after game over

## Technical Details

The AI implementation uses:
- A* pathfinding algorithm for optimal path calculation
- Collision avoidance with walls and snake body
- Dynamic path recalculation
- Survival mode when no path to food exists

## Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, etc.)

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/bosseus/snake-game-ai.git
   ```

2. Compile the Java files:
   ```bash
   javac src/*.java
   ```

3. Run the game:
   ```bash
   java -cp src SnakeGame
   ```

## Project Structure

- `SnakeGame.java`: Main class to start the game
- `GameFrame.java`: Creates the game window using JFrame
- `GamePanel.java`: Handles game logic and rendering
- `SnakeAI.java`: Implements AI control logic
- `PathFinder.java`: Implements A* pathfinding algorithm 