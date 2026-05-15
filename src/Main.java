import domain.Company;
import domain.Developer;
import domain.Tester;
import domain.Manager;
import domain.Project;
import engine.AIPlayer;
import engine.GameEngine;

public class Main {

    public static void main(String[] args) {

        // --- Player Company ---
        Company playerCompany = new Company("Player TechCorp", 100000);

        playerCompany.hire(new Developer("Anna",  8, 7000));
        playerCompany.hire(new Tester   ("Piotr", 6, 6000));
        playerCompany.hire(new Manager  ("Eva",   5, 8000));

        playerCompany.addProject(new Project("Strategic AI Platform", 60, 30000, true));
        playerCompany.addProject(new Project("Mobile App",            30, 15000, false));

        // --- AI Company ---
        Company aiCompany = new Company("AI Systems Ltd", 100000);

        aiCompany.hire(new Developer("Bot Dev",    7, 6500));
        aiCompany.hire(new Tester   ("Bot Tester", 5, 5500));

        aiCompany.addProject(new Project("Competing AI Platform", 60, 30000, true));

        // --- Start Game ---
        AIPlayer   aiPlayer = new AIPlayer(aiCompany);
        GameEngine engine   = new GameEngine(playerCompany, aiPlayer, 12);

        engine.run();
    }
}
