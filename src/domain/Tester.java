package domain;

public class Tester extends Employee {

    public Tester(String name, int skill, double salary) {
        super(name, skill, salary);
    }

    // Testers produce half the work but cost less — quality-focused role
    @Override
    public int work() {
        return Math.max(1, getSkill() / 2);
    }
}
