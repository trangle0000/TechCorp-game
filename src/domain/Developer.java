package domain;

public class Developer extends Employee {

    public Developer(String name, int skill, double salary) {
        super(name, skill, salary);
    }

    // Developers produce full skill value as work
    @Override
    public int work() {
        return getSkill();
    }
}
