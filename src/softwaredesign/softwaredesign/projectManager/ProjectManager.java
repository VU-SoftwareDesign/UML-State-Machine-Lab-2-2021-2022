package softwaredesign.projectManager;

import java.util.*;

public class ProjectManager {
    String[] skillNames = {"Programming", "Driving", "Cooking", "Music"};
    String[] skillsTestSet = {"Blockchain, Cloud computing, Analytical Reasoning"};
    String[] employeeNames = {"Bobby", "Sammy", "Pink man"};
    String[] tasks = {"Wash car", "Cook food", "Eat", "Sleep", "Build a rocket", "Get a million dollars", "Do a moon dance"};

    List<Skill> skills = new ArrayList<>();
    Map<Employee, QualStatus> employees = new HashMap<>();
    List<Employee> employeesList = new ArrayList<>();
    Map<UUID, Task> tasksMap = new HashMap<>();
    List<Skill> testSkillSet = new ArrayList<>();
    List<Project> projects = new ArrayList<>();
    List<TaskList> tasksLists = new ArrayList<>();
    TaskList currentTaskList = new TaskList("new");

    //Use string array to create and add skills to an arrayList.
    public List<Skill> createSkillList(String[] skillNames) {
        System.out.println("Creating skills");
        List<Skill> skills = new ArrayList<>();
        for (String skill : skillNames) {
            skills.add(new Skill(skill));
        }
        return skills;
    }

    public Map<Employee, QualStatus> createEmployeeMap(List<Skill> skills, String[] employeeNames) {
        System.out.println("Creating Employee list");
        Map<Employee, QualStatus> employeesMap = new HashMap<>();
        for (String currentName : employeeNames) {
            employeesMap.put(new Employee(currentName, 0d, skills), QualStatus.QUALIFIED);
        }
        return employeesMap;
    }

    public Map<UUID, Task> createTasks(Map<Employee, QualStatus> employees, String[] tasksNames, List<Skill> skills) {
        System.out.println("Creating tasks");
        Map<UUID, Task> tasksMap = new HashMap<>();

        TaskList dependentTasks = new TaskList("Dependent Tasks");

        for (String currentTaskName : tasksNames) {
            tasksMap.put(UUID.randomUUID(), new Task(currentTaskName, employees, new Status(Status.Progress.CREATED), skills, 0d, dependentTasks));
        }
        return tasksMap;
    }

    public void addProject(String name, List<TaskList> taskLists, List<Employee> employees, Status status, double availableFunds) {
        this.projects.add(new Project(name, taskLists, employees, status, availableFunds));
    }

    public void addTaskList(Map<UUID, Task> tasksMap, String name) {
        this.tasksLists.add(new TaskList(name, tasksMap));
    }

    public void instantiateVariables() {
        this.skills = createSkillList(skillNames);
        this.employees = createEmployeeMap(skills, employeeNames);
        this.employeesList = new ArrayList<>(this.employees.keySet());
        this.tasksMap = createTasks(employees, tasks, skills);
        this.testSkillSet = createSkillList(skillsTestSet);
        addTaskList(this.tasksMap, "alpha");
        addProject("Alpha", tasksLists, this.employeesList, new Status(Status.Progress.CREATED), 1000d);
    }

    public void handleAddEmployee (Printer printer, Task task) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> userInput = new ArrayList<>();
        System.out.println("Type in employee details:\n\n1. Name?");
        String name = scanner.nextLine();
        System.out.println("Hours worked?");
        int hoursWorked = scanner.nextInt();
        printer.printSkills(this.skills);
        System.out.println("Choose the skills to assign the employee. Press q to exit.");
        while (scanner.hasNextInt()) {
            userInput.add(scanner.nextInt());
        }
        Employee newEmployee = new Employee(name, hoursWorked, parseToSkills(userInput));
        task.assignEmployeeToTask(newEmployee);
        System.out.println(newEmployee.getName() + " added to " + task.getTaskName());
    }

    public List<Skill> parseToSkills(List<Integer> nums) {
        List<Skill> skillSet = new ArrayList<>();
        nums.forEach(x -> skillSet.add(this.skills.get(x)));
        return skillSet;
    }
}