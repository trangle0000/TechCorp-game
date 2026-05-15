package domain;

public class Manager extends Employee {

    public Manager(String name, int skill, double salary) {
        super(name, skill, salary);
    }

    // Managers boost efficiency: produce 1.2x their skill value
    @Override
    public int work() {
        return (int) (getSkill() * 1.2);
    }
}
