package engine;

import domain.Company;
import domain.Employee;
import domain.Project;

public class AIPlayer {

    private Company company;

    public AIPlayer(Company company) {
        if (company == null)
            throw new IllegalArgumentException("Company cannot be null.");
        this.company = company;
    }

    public void makeDecision() {
        if (company.getProjects().isEmpty()) return;

        Project target = company.getProjects().get(0);

        if (company.getCash() > 15000) {
            // AI is aggressive when it has enough cash
            target.start();
            for (Employee employee : company.getEmployees()) {
                target.addEmployee(employee);
            }
        }
        // When cash is low, AI skips work to survive bankruptcy
    }

    public Company getCompany() { return company; }
}
