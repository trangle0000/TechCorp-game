package ui;

import domain.Company;
import domain.Employee;
import domain.Project;
import engine.GameResult;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {

    public static void showWelcome(int maxTurns) {
        System.out.println("========================================");
        System.out.println("       WELCOME TO TECHCORP DUEL         ");
        System.out.println("========================================");
        System.out.println("  Finish your Strategic Project before");
        System.out.println("  the AI does — or outvalue them in");
        System.out.println("  " + maxTurns + " turns!");
        System.out.println("========================================");
    }

    public static void showTurnHeader(int turn, int maxTurns) {
        System.out.println();
        System.out.println("========================================");
        System.out.printf("           TURN  %d / %d%n", turn, maxTurns);
        System.out.println("========================================");
    }

    public static void showCompany(Company company) {
        System.out.println();
        System.out.println("  >> " + company.getName());
        System.out.printf("     Cash       : $%,.0f%n", company.getCash());
        System.out.printf("     Reputation : %d%n",     company.getReputation());
        System.out.printf("     Value      : $%,.0f%n", company.calculateCompanyValue());
        System.out.println("     Employees:");
        for (Employee e : company.getEmployees()) {
            System.out.println("       - " + e);
        }
        System.out.println("     Projects:");
        for (Project p : company.getProjects()) {
            String bar  = buildProgressBar(p.getProgress(), p.getRequiredWork(), 20);
            String flag = p.isStrategic() ? " [STRATEGIC]" : "";
            System.out.printf("       - %-28s %s %d/%d  [%s]%s%n",
                p.getName(), bar,
                p.getProgress(), p.getRequiredWork(),
                p.getStatus(), flag);
        }
    }

    private static String buildProgressBar(int progress, int total, int width) {
        int filled = (total == 0) ? 0 : (progress * width / total);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < width; i++) {
            sb.append(i < filled ? "#" : "-");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("--- YOUR DECISION ---");
        System.out.println("1. Assign all employees to Strategic Project");
        System.out.println("2. Pause Strategic Project (ON_HOLD)");
        System.out.println("3. Resume paused Strategic Project");
        System.out.println("4. Save cash this turn (80% salary, no work)");
        System.out.println("5. Skip turn");
        System.out.print("Your choice (1-5): ");
    }

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

    public static void showEvent(String message) {
        System.out.println();
        System.out.println("  *** RANDOM EVENT: " + message + " ***");
    }

    public static void showMessage(String message) {
        System.out.println("  > " + message);
    }

    public static void showFinalResult(GameResult result, Company player, Company ai) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("             GAME OVER                  ");
        System.out.println("========================================");
        System.out.printf("  Your company value : $%,.0f%n", player.calculateCompanyValue());
        System.out.printf("  AI company value   : $%,.0f%n", ai.calculateCompanyValue());
        System.out.println();
        switch (result) {
            case PLAYER_WINS -> System.out.println("  *** YOU WIN! Congratulations! ***");
            case AI_WINS     -> System.out.println("  *** AI WINS. Better luck next time. ***");
            case DRAW        -> System.out.println("  *** DRAW! It's a tie! ***");
            default          -> System.out.println("  *** GAME ENDED ***");
        }
        System.out.println("========================================");
    }
}
