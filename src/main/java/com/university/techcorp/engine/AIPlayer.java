package com.university.techcorp.engine;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Employee;
import com.university.techcorp.domain.Project;

public class AIPlayer {

    private Company company;

    public AIPlayer(Company company) {
        if (company == null) throw new IllegalArgumentException("Company cannot be null.");
        this.company = company;
    }

    public void makeDecision() {
        if (company.getProjects().isEmpty()) return;
        Project target = company.getProjects().get(0);
        if (company.getCash() > 15000) {
            target.start();
            for (Employee e : company.getEmployees()) target.addEmployee(e);
        }
    }

    public Company getCompany() { return company; }
}
