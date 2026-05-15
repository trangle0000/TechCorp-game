package engine;

import domain.Company;
import domain.Employee;
import domain.Project;
import ui.ConsoleUI;

import java.util.Scanner;

public class GameEngine {

    private Company    playerCompany;
    private AIPlayer   aiPlayer;
    private Scanner    scanner;
    private int        turn;
    private int        maxTurns;
    private GameResult result;
    private boolean    saveCashThisTurn;

    public GameEngine(Company playerCompany, AIPlayer aiPlayer, int maxTurns) {
        if (playerCompany == null || aiPlayer == null)
            throw new IllegalArgumentException("Companies cannot be null.");
        if (maxTurns <= 0)
            throw new IllegalArgumentException("Max turns must be positive.");

        this.playerCompany    = playerCompany;
        this.aiPlayer         = aiPlayer;
        this.maxTurns         = maxTurns;
        this.turn             = 1;
        this.result           = GameResult.IN_PROGRESS;
        this.saveCashThisTurn = false;
        this.scanner          = new Scanner(System.in);
    }

    public void run() {
        ConsoleUI.showWelcome(maxTurns);

        while (result == GameResult.IN_PROGRESS) {
            ConsoleUI.showTurnHeader(turn, maxTurns);

            System.out.println("[YOUR COMPANY]");
            ConsoleUI.showCompany(playerCompany);
            System.out.println();
            System.out.println("[AI COMPANY]");
            ConsoleUI.showCompany(aiPlayer.getCompany());

            playerDecision();
            aiPlayer.makeDecision();
            processTurn();
            processRandomEvent();
            evaluateGameResult();

            turn++;
        }

        ConsoleUI.showFinalResult(result, playerCompany, aiPlayer.getCompany());
        scanner.close();
    }

    // -----------------------------------------------------------------------
    // Player decision
    // -----------------------------------------------------------------------

    private void playerDecision() {
        saveCashThisTurn = false;
        ConsoleUI.showMenu();
        int choice = ConsoleUI.getValidChoice(scanner, 1, 5);

        switch (choice) {
            case 1 -> assignAllToStrategic();
            case 2 -> pauseStrategic();
            case 3 -> resumeStrategic();
            case 4 -> {
                saveCashThisTurn = true;
                ConsoleUI.showMessage("Saving cash this turn — 80% salary, no project work.");
            }
            case 5 -> ConsoleUI.showMessage("Turn skipped.");
        }
    }

    private void assignAllToStrategic() {
        Project strategic = playerCompany.findStrategicProject();
        if (strategic == null) {
            ConsoleUI.showMessage("No strategic project found.");
            return;
        }
        strategic.start();
        for (Employee e : playerCompany.getEmployees()) {
            strategic.addEmployee(e);
        }
        ConsoleUI.showMessage("All employees assigned to: " + strategic.getName());
    }

    private void pauseStrategic() {
        Project strategic = playerCompany.findStrategicProject();
        if (strategic == null) {
            ConsoleUI.showMessage("No strategic project found.");
            return;
        }
        strategic.pause();
        ConsoleUI.showMessage("Project paused: " + strategic.getName());
    }

    private void resumeStrategic() {
        Project strategic = playerCompany.findStrategicProject();
        if (strategic == null) {
            ConsoleUI.showMessage("No strategic project found.");
            return;
        }
        strategic.start();
        ConsoleUI.showMessage("Project resumed: " + strategic.getName());
    }

    // -----------------------------------------------------------------------
    // Turn processing
    // -----------------------------------------------------------------------

    private void processTurn() {
        // Player's projects only work if not saving cash
        if (!saveCashThisTurn) {
            playerCompany.workOnProjects();
        }
        aiPlayer.getCompany().workOnProjects();

        // Salary payments
        if (saveCashThisTurn) {
            playerCompany.paySalariesReduced();
        } else {
            playerCompany.paySalaries();
        }
        aiPlayer.getCompany().paySalaries();

        // Collect revenue from finished projects
        playerCompany.collectRevenue();
        aiPlayer.getCompany().collectRevenue();
    }

    private void processRandomEvent() {
        // Market crisis every 3rd turn
        if (turn % 3 == 0) {
            ConsoleUI.showEvent("Market Crisis! Both companies lose $3,000");
            playerCompany.deductCash(3000);
            aiPlayer.getCompany().deductCash(3000);
        }
    }

    // -----------------------------------------------------------------------
    // Victory evaluation
    // -----------------------------------------------------------------------

    private void evaluateGameResult() {
        Company aiCompany = aiPlayer.getCompany();

        // Bankruptcy check
        if (playerCompany.isBankrupt()) {
            result = GameResult.AI_WINS;
            return;
        }
        if (aiCompany.isBankrupt()) {
            result = GameResult.PLAYER_WINS;
            return;
        }

        // Win by reputation (bonus condition)
        if (playerCompany.getReputation() >= 50) {
            result = GameResult.PLAYER_WINS;
            return;
        }

        // Strategic project race
        boolean playerDone = playerCompany.hasFinishedStrategicProject();
        boolean aiDone     = aiCompany.hasFinishedStrategicProject();

        if (playerDone && !aiDone) {
            result = GameResult.PLAYER_WINS;
            return;
        }
        if (aiDone && !playerDone) {
            result = GameResult.AI_WINS;
            return;
        }

        // Final turn — compare company values
        if (turn >= maxTurns) {
            double pv = playerCompany.calculateCompanyValue();
            double av = aiCompany.calculateCompanyValue();

            if      (pv > av) result = GameResult.PLAYER_WINS;
            else if (av > pv) result = GameResult.AI_WINS;
            else              result = GameResult.DRAW;
        }
    }
}
