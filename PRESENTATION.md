# TechCorp Duel — Java Final Project Presentation

## 1. How to Run

Open terminal in this folder and run:

```
run.bat
```

---

## 2. Project Structure

```
FinalProject/
├── src/
│   ├── Main.java                    ← Entry point
│   ├── domain/
│   │   ├── Employee.java            ← Abstract class
│   │   ├── Developer.java           ← extends Employee
│   │   ├── Tester.java              ← extends Employee
│   │   ├── Manager.java             ← extends Employee
│   │   ├── Project.java             ← Project lifecycle
│   │   ├── ProjectStatus.java       ← Enum
│   │   └── Company.java             ← Company operations
│   ├── engine/
│   │   ├── GameEngine.java          ← Main game loop (12 turns)
│   │   ├── AIPlayer.java            ← AI decision logic
│   │   └── GameResult.java          ← Enum
│   └── ui/
│       └── ConsoleUI.java           ← Console display & input
├── run.bat                          ← Compile & run script
└── out/                             ← Compiled .class files
```

---

## 3. OOP Concepts Demonstrated

### Abstract Class & Inheritance

```java
// Employee.java — abstract base class
public abstract class Employee {
    private String name;
    private int skill;
    private double salary;

    public abstract int work();  // each subclass implements differently
}

// Developer.java
public class Developer extends Employee {
    @Override
    public int work() { return getSkill(); }  // full skill value
}

// Tester.java
public class Tester extends Employee {
    @Override
    public int work() { return Math.max(1, getSkill() / 2); }  // half skill
}

// Manager.java
public class Manager extends Employee {
    @Override
    public int work() { return (int)(getSkill() * 1.2); }  // 20% bonus
}
```

### Enum

```java
// ProjectStatus.java
public enum ProjectStatus {
    PLANNED, IN_PROGRESS, ON_HOLD, FINISHED, CANCELLED
}

// GameResult.java
public enum GameResult {
    PLAYER_WINS, AI_WINS, DRAW, IN_PROGRESS
}
```

### Encapsulation

```java
// All fields are private — accessed only through getters
private String name;
private double cash;
private int reputation;

public String getName()      { return name; }
public double getCash()      { return cash; }
public int getReputation()   { return reputation; }
```

### Input Validation

```java
// ConsoleUI.java — handles wrong input gracefully
public static int getValidChoice(Scanner scanner, int min, int max) {
    while (true) {
        try {
            int choice = scanner.nextInt();
            if (choice >= min && choice <= max) return choice;
            System.out.printf("Enter a number between %d and %d: ", min, max);
        } catch (InputMismatchException e) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
    }
}
```

---

## 4. Game Logic Summary

| Turn | Player Action | Result |
|------|--------------|--------|
| Each turn | Choose 1–5 | Employees work, salaries paid, revenue collected |
| Every 3rd turn | Market Crisis | Both companies lose $3,000 |
| End | Compare values | Higher company value wins |

### Win Conditions
- Finish Strategic Project before AI → **You Win**
- AI goes bankrupt → **You Win**
- Reputation reaches 50 → **You Win**
- After 12 turns → highest company value wins

---

## 5. Key Classes Walkthrough

| Class | Role |
|-------|------|
| `Employee` | Abstract base — defines `work()` contract |
| `Developer / Tester / Manager` | Concrete employees, each with different `work()` |
| `Project` | Tracks progress, status, assigned employees |
| `Company` | Manages employees, projects, cash, reputation |
| `GameEngine` | Controls 12-turn loop, player input, events |
| `AIPlayer` | Automated opponent — aggressive when cash > $15,000 |
| `ConsoleUI` | All display and input handling |
