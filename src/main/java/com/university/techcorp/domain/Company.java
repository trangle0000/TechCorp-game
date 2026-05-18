package com.university.techcorp.domain;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private String         name;
    private double         cash;
    private int            reputation;
    private List<Employee> employees;
    private List<Project>  projects;

    public Company(String name, double cash) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Company name cannot be blank.");
        if (cash < 0)                       throw new IllegalArgumentException("Cash cannot be negative.");

        this.name       = name;
        this.cash       = cash;
        this.reputation = 0;
        this.employees  = new ArrayList<>();
        this.projects   = new ArrayList<>();
    }

    public void hire(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Employee cannot be null.");
        employees.add(employee);
    }

    public void addProject(Project project) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null.");
        projects.add(project);
    }

    public void workOnProjects()      { for (Project p : projects) p.workOneTurn(); }
    public void paySalaries()         { for (Employee e : employees) cash -= e.getSalary(); }
    public void paySalariesReduced()  { for (Employee e : employees) cash -= e.getSalary() * 0.8; }
    public void deductCash(double amt){ cash -= amt; }

    public void collectRevenue() {
        for (Project p : projects) {
            if (p.isFinished() && !p.isPaid()) { cash += p.getReward(); reputation += 10; p.markAsPaid(); }
        }
    }

    public boolean isBankrupt()             { return cash < 0; }
    public double  calculateCompanyValue()  { return cash + reputation * 1000.0 + countFinishedProjects() * 5000.0; }

    public int countFinishedProjects() {
        int count = 0;
        for (Project p : projects) if (p.isFinished()) count++;
        return count;
    }

    public boolean hasFinishedStrategicProject() {
        for (Project p : projects) if (p.isStrategic() && p.isFinished()) return true;
        return false;
    }

    public Project findStrategicProject() {
        for (Project p : projects) if (p.isStrategic()) return p;
        return null;
    }

    public String         getName()       { return name; }
    public double         getCash()       { return cash; }
    public int            getReputation() { return reputation; }
    public List<Employee> getEmployees()  { return employees; }
    public List<Project>  getProjects()   { return projects; }
}
