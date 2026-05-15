package domain;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String        name;
    private int           requiredWork;
    private int           progress;
    private double        reward;
    private boolean       strategic;
    private boolean       paid;
    private ProjectStatus status;
    private List<Employee> team;

    public Project(String name, int requiredWork, double reward, boolean strategic) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Project name cannot be blank.");
        if (requiredWork <= 0)
            throw new IllegalArgumentException("Required work must be positive.");
        if (reward < 0)
            throw new IllegalArgumentException("Reward cannot be negative.");

        this.name         = name;
        this.requiredWork = requiredWork;
        this.reward       = reward;
        this.strategic    = strategic;
        this.progress     = 0;
        this.paid         = false;
        this.status       = ProjectStatus.PLANNED;
        this.team         = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null.");
        if (!team.contains(employee))
            team.add(employee);
    }

    public void start() {
        if (status == ProjectStatus.PLANNED || status == ProjectStatus.ON_HOLD)
            status = ProjectStatus.IN_PROGRESS;
    }

    public void pause() {
        if (status == ProjectStatus.IN_PROGRESS)
            status = ProjectStatus.ON_HOLD;
    }

    public void cancel() {
        status = ProjectStatus.CANCELLED;
    }

    public void workOneTurn() {
        if (status != ProjectStatus.IN_PROGRESS) return;
        if (isFinished()) return;

        for (Employee employee : team) {
            progress += employee.work();
        }

        if (progress >= requiredWork) {
            progress = requiredWork;
            status   = ProjectStatus.FINISHED;
        }
    }

    public boolean isFinished() {
        return progress >= requiredWork;
    }

    public void    markAsPaid()          { paid = true; }
    public boolean isPaid()              { return paid; }
    public boolean isStrategic()         { return strategic; }

    public String        getName()        { return name; }
    public int           getRequiredWork(){ return requiredWork; }
    public int           getProgress()    { return progress; }
    public double        getReward()      { return reward; }
    public ProjectStatus getStatus()      { return status; }
    public List<Employee> getTeam()       { return team; }
}
