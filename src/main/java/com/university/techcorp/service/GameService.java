package com.university.techcorp.service;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Developer;
import com.university.techcorp.domain.Manager;
import com.university.techcorp.domain.Project;
import com.university.techcorp.domain.Tester;
import com.university.techcorp.engine.AIPlayer;
import com.university.techcorp.engine.GameEngine;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameEngine engine;

    public GameService() {
        engine = createNewEngine();
    }

    public String getState() {
        return engine.getState();
    }

    public String performAction(int choice) {
        return engine.performAction(choice);
    }

    public String resetGame() {
        engine = createNewEngine();
        return "New game started. " + engine.getState();
    }

    private GameEngine createNewEngine() {
        Company player = new Company("Player TechCorp", 100000);
        player.hire(new Developer("Anna",  8, 7000));
        player.hire(new Tester   ("Piotr", 6, 6000));
        player.hire(new Manager  ("Eva",   5, 8000));
        player.addProject(new Project("Strategic AI Platform", 60, 30000, true));
        player.addProject(new Project("Mobile App",            30, 15000, false));

        Company ai = new Company("AI Systems Ltd", 100000);
        ai.hire(new Developer("Bot Dev",    7, 6500));
        ai.hire(new Tester   ("Bot Tester", 5, 5500));
        ai.addProject(new Project("Competing AI Platform", 60, 30000, true));

        return new GameEngine(player, new AIPlayer(ai), 12);
    }
}
