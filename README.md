# TechCorp Duel — Java Final Project

A turn-based business strategy game built in plain Java (no frameworks).

## How to Run

**Requirements:** Java 17+

```
run.bat
```

## Game Overview

You manage **Player TechCorp** competing against an AI opponent (**AI Systems Ltd**) over 12 turns.  
The goal is to finish your **Strategic AI Platform** project before the AI finishes theirs — or build a higher company value by the end.

## Win Conditions

| Condition | Result |
|-----------|--------|
| Your company goes bankrupt (cash < 0) | AI Wins |
| AI company goes bankrupt | You Win |
| You finish Strategic Project first | You Win |
| AI finishes Strategic Project first | AI Wins |
| Reputation reaches 50 | You Win |
| End of 12 turns — higher company value wins | Winner / Draw |

## Player Actions (each turn)

1. Assign all employees to Strategic Project
2. Pause Strategic Project
3. Resume paused Strategic Project
4. Save cash this turn (80% salary, no project work)
5. Skip turn

## Project Structure

```
src/
├── Main.java                  # Entry point
├── domain/
│   ├── Employee.java          # Abstract base class
│   ├── Developer.java         # work() = skill
│   ├── Tester.java            # work() = skill / 2
│   ├── Manager.java           # work() = skill * 1.2
│   ├── Project.java           # Project lifecycle & progress
│   ├── ProjectStatus.java     # Enum: PLANNED, IN_PROGRESS, ON_HOLD, FINISHED, CANCELLED
│   └── Company.java           # Company state & operations
└── engine/
│   ├── GameEngine.java        # Main game loop (12 turns)
│   ├── AIPlayer.java          # AI decision logic
│   └── GameResult.java        # Enum: PLAYER_WINS, AI_WINS, DRAW, IN_PROGRESS
└── ui/
    └── ConsoleUI.java         # Console display & input validation
```

## OOP Concepts Used

- **Inheritance & Polymorphism** — `Employee` → `Developer`, `Tester`, `Manager`
- **Abstract class** — `Employee.work()` overridden differently in each subclass
- **Encapsulation** — all fields private with getters
- **Enum** — `ProjectStatus`, `GameResult`
- **Input validation** — `InputMismatchException` handling in `ConsoleUI`
