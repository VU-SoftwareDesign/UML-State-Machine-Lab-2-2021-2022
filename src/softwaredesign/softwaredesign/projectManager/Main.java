package softwaredesign.projectManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ProjectManager tester = new ProjectManager();
        Printer printer = new Printer();
        Scanner scanner = new Scanner(System.in);
        int userResponseInt = 0;
        String userResponseStr = "";

        tester.instantiateVariables();

        System.out.println("Welcome! You have " + tester.projects.size() + " projects. Please type in the corresponding numbers when" +
                "presented with an option");

        printer.printProjectDetails(tester.projects.get(0));
        System.out.println(printer.presentOptions);
        userResponseInt = scanner.nextInt();

        //Manage Task
        if (userResponseInt == 1) {
            System.out.println(printer.presentTaskLists(tester.projects.get(0).getTaskLists()));
            userResponseInt = scanner.nextInt();
            tester.currentTaskList = tester.projects.get(0).getTaskLists().get(userResponseInt);
            System.out.println(printer.presentTasks(tester.currentTaskList));
            userResponseInt = scanner.nextInt();
            System.out.println(printer.taskOptions);
            tester.handleAddEmployee(printer, tester.currentTaskList.getTaskList().get(userResponseInt));
        }
    }
}