package softwaredesign.projectManager;

import java.util.List;
import java.util.Scanner;

public class Printer {
    public String presentOptions = "\nWhat would you like to manage?\n\n1. Task\n2.Project";
    public String projectOptions = "\n1. Add task\n2. Add funds\n3. Change project name\n";
    public String taskOptions = "\n1. Assign Employee\n2. Add Dependent Tasks\n3. Add Required Skills";
    public void printEmployeeDetails(Employee employee) {
        String messageToBePrinted = "Employee name:" + employee.getName() + "\nHours worked: " + employee.getWorkedHours() + "\nSkills: ";
        System.out.print(messageToBePrinted);

        for (Skill currentSkill : employee.getSkills()) {
            System.out.print(currentSkill.getName() + " ");
        }
        System.out.println("\n");
    }

    public void printSkills(List<Skill> skills) {
        StringBuilder skillPrint = new StringBuilder();
        for (int i = 0; i < skills.size(); i++) {
            skillPrint.append(i+1 + ". " + skills.get(i).getName() + "\n");
        }
        System.out.println(skillPrint);
    }

    public void printProjectDetails(Project project) {
        project.updateEmployees();
        String messageToBePrinted = "\nPrinting project details...\nProject name: " + project.getName() + "\nStatus: " + project.getStatus().getProgress().toString() +
                "\nTasks assigned:\n";
        StringBuilder employeeList = new StringBuilder("\nCurrent Employees:\n");

        project.getEmployees().forEach(x-> employeeList.append(x.getName()).append("\n"));
        System.out.println(messageToBePrinted + printTaskListsAndTasks(project.getTaskLists()) + employeeList);
    }

    private StringBuilder printTaskListsAndTasks (List<TaskList> taskLists) {
        StringBuilder taskListPrint = new StringBuilder("current tasks available: \n");

        for (TaskList currentTaskList : taskLists) {
            taskListPrint.append("TaskList: ").append(currentTaskList.getName()).append("\n");
            for (Task currentTask : currentTaskList.getTaskList()) {
                taskListPrint.append(currentTask.getTaskName()).append("\n");
            }
        }
        return taskListPrint;
    }

    public StringBuilder presentTaskLists(List<TaskList> taskLists) {
        StringBuilder tasksListsPrint = new StringBuilder("\nCurrent Task lists:\n");
        for (TaskList currentTaskList : taskLists) {
            tasksListsPrint.append(currentTaskList.getName());
        }
        return tasksListsPrint;
    }

    public StringBuilder presentTasks (TaskList taskList) {
        StringBuilder tasksPrint = new StringBuilder("Current Tasks:\n");
        taskList.getTaskList().forEach(x->tasksPrint.append(x.getTaskName()));
        return tasksPrint;
    }

    public void presentTaskOptions(List<TaskList> taskLists, Scanner scanner) {
        System.out.println(presentTaskLists(taskLists));
        presentTasks(taskLists.get(scanner.nextInt()));
    }
}
