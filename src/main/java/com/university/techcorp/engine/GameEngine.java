package com.university.techcorp.engine;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Employee;
import com.university.techcorp.domain.Project;

public class GameEngine {

    private Company    playerCompany;
    private AIPlayer   aiPlayer;
    private int        turn;
    private int        maxTurns;
    private GameResult result;

    public GameEngine(Company playerCompany, AIPlayer aiPlayer, int maxTurns) {
        if (playerCompany == null || aiPlayer == null) throw new IllegalArgumentException("Companies cannot be null.");
        if (maxTurns <= 0)                             throw new IllegalArgumentException("Max turns must be positive.");

        this.playerCompany = playerCompany;
        this.aiPlayer      = aiPlayer;
        this.maxTurns      = maxTurns;
        this.turn          = 1;
        this.result        = GameResult.IN_PROGRESS;
    }

    public String performAction(int choice) {
        if (result != GameResult.IN_PROGRESS) return "Game over: " + result;

        boolean saveCash  = false;
        String  actionMsg = "";

        switch (choice) {
            case 1 -> actionMsg = assignAllToStrategic();
            case 2 -> actionMsg = pauseStrategic();
            case 3 -> actionMsg = resumeStrategic();
            case 4 -> { saveCash = true; actionMsg = "Cash saved this turn (80% salaries)."; }
            case 5 -> actionMsg = "Turn skipped.";
            default -> { return "Invalid choice. Use 1-5."; }
        }

        aiPlayer.makeDecision();

        if (!saveCash) playerCompany.workOnProjects();
        aiPlayer.getCompany().workOnProjects();

        if (saveCash) playerCompany.paySalariesReduced();
        else          playerCompany.paySalaries();
        aiPlayer.getCompany().paySalaries();

        playerCompany.collectRevenue();
        aiPlayer.getCompany().collectRevenue();

        String event = "";
        if (turn % 3 == 0) {
            playerCompany.deductCash(3000);
            aiPlayer.getCompany().deductCash(3000);
            event = " | Market Crisis: -$3,000 each";
        }

        evaluateGameResult();
        turn++;

        return actionMsg + event;
    }

    public String getState() {
        return String.format(
            "Turn: %d/%d, Cash: $%,.0f, Reputation: %d, Status: %s | AI Cash: $%,.0f",
            turn, maxTurns,
            playerCompany.getCash(),
            playerCompany.getReputation(),
            result,
            aiPlayer.getCompany().getCash()
        );
    }

    private String assignAllToStrategic() {
        Project strategic = playerCompany.findStrategicProject();
        if (strategic == null) return "No strategic project found.";
        strategic.start();
        for (Employee e : playerCompany.getEmployees()) strategic.addEmployee(e);
        return "All employees assigned to: " + strategic.getName();
    }

    private String pauseStrategic() {
        Project strategic = playerCompany.findStrategicProject();
        if (strategic == null) return "No strategic project found.";
        strategic.pause();
        return "Project paused: " + strategic.getName();
    }

    private String resumeStrategic() {
        Project strategic = playerCompany.findStrategicProject();
        if (strategic == null) return "No strategic project found.";
        strategic.start();
        return "Project resumed: " + strategic.getName();
    }

    private void evaluateGameResult() {
        Company aiCompany = aiPlayer.getCompany();

        if (playerCompany.isBankrupt()) { result = GameResult.AI_WINS;     return; }
        if (aiCompany.isBankrupt())     { result = GameResult.PLAYER_WINS; return; }
        if (playerCompany.getReputation() >= 50) { result = GameResult.PLAYER_WINS; return; }

        boolean playerDone = playerCompany.hasFinishedStrategicProject();
        boolean aiDone     = aiCompany.hasFinishedStrategicProject();

        if (playerDone && !aiDone) { result = GameResult.PLAYER_WINS; return; }
        if (aiDone && !playerDone) { result = GameResult.AI_WINS;     return; }

        if (turn >= maxTurns) {
            double pv = playerCompany.calculateCompanyValue();
            double av = aiCompany.calculateCompanyValue();
            if      (pv > av) result = GameResult.PLAYER_WINS;
            else if (av > pv) result = GameResult.AI_WINS;
            else              result = GameResult.DRAW;
        }
    }

    public int        getTurn()          { return turn; }
    public int        getMaxTurns()      { return maxTurns; }
    public GameResult getResult()        { return result; }
    public Company    getPlayerCompany() { return playerCompany; }
    public AIPlayer   getAiPlayer()      { return aiPlayer; }
}
