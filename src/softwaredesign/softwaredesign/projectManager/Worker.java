package softwaredesign.projectManager;

import java.util.List;

public class Worker extends Employee {
    softwaredesign.projectManager.Worker smth = new softwaredesign.projectManager.Worker("lala");
    public Worker(String name) {
        super(name);
    }

    public Worker(String name, double maxWorkHours, List<Skill> skills) {
        super(name, maxWorkHours, skills);
    }
    public String getName() {
        return "lala";
    }
}
