package softwaredesign.projectManager;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Project {
    //Use "this" for creating new instance of class in the parameters

    private String name;
    private UUID uuid;
    private Status status;
    private double timeSpent;
    private double availableFunds;

    private List<TaskList> taskLists;
    private List<Employee> allEmployees;

    public Project(String name, List<TaskList> taskLists, double availableFunds) {
        updateEmployees();
        updateTimeSpent();
        this.name = name;
        this.taskLists = taskLists;
        this.uuid = UUID.randomUUID();
        this.status = decideStatus();
        this.availableFunds = availableFunds;
    }

    public Project(String name, List<TaskList> taskLists, List<Employee> employees, Status status, double availableFunds) {
        this.name = name;
        this.taskLists = taskLists;
        this.allEmployees = employees;
        this.uuid = UUID.randomUUID();
        this.status = status;
        this.availableFunds = availableFunds;
    }

    public Project (Project project) {
        this.name = project.name;
        this.taskLists = project.taskLists;
        this.allEmployees = project.allEmployees;
        this.uuid = project.uuid;
        this.status = project.status;
        this.timeSpent = project.timeSpent;
        this.availableFunds = project.availableFunds;
    }

    public void setAvailableFunds(double availableFunds) {
        this.availableFunds = new Double(availableFunds);
    }

    public void addFunds (double moreFunds) {
        this.availableFunds += moreFunds;
    }

    public double getAvailableFunds() {
        return new Double(availableFunds);
    }

    public String getName() {
        return String.copyValueOf(this.name.toCharArray());
    }

    public void setName(String name) {
        this.name = String.copyValueOf(name.toCharArray());
    }

    public Status getStatus() {return new Status(this.status);}

    public void setStatus(Status status) {
        if (status.getProgress() != decideStatus().getProgress()) {
            //Introduce a switch statement here to see what the status is and why it might have failed for error handling.
            System.err.println("Status not changed, minimum requirements for the desired status not met.");
            return;
        }
        this.status = new Status(status);
    }

    public List<TaskList> getTaskLists() {
        return Collections.unmodifiableList(this.taskLists);
    }

    public void addTaskList(TaskList taskList) {
        taskList = new TaskList(taskList);
        taskLists.add(taskList);
    }

    private void addEmployee(Employee employee) {
        for(Employee currentEmployee: this.allEmployees) {
            if (currentEmployee.is(employee.getUuid())) {
                System.err.println("Employee already assigned.");
                return;
            }
        }
        allEmployees.add(new Employee(employee));
    }

    private void addTime (Double timeSpent) {
        this.timeSpent += timeSpent;
    }

    public void updateEmployees () {
        this.taskLists.forEach(x-> x.getTaskList().forEach(y-> addEmployees(y.getAssignedEmployees())));
    }

    public void updateTimeSpent() {
        this.taskLists.forEach(x->x.getTaskList().forEach(y->addTime(y.getTimeSpent())));
    }

    private void addEmployees (List<Employee> employees) {
        if (!this.allEmployees.containsAll(employees)) {
            for (Employee currentEmployee : employees) {
                addEmployee(currentEmployee);
            }
        }
    }

    public void replaceTaskList(TaskList oldTaskList, TaskList newTaskList) {
        int index = 0;
        for (TaskList currentTL : this.taskLists) {
            if (currentTL == oldTaskList) {
                newTaskList = new TaskList(newTaskList);
                this.taskLists.remove(oldTaskList);
                this.taskLists.add(index, newTaskList);
            }
            //Use try catch here
            else {
                System.err.println("Task list not found. Task list not replaced.");
                return;
            }
        }
    }


    public void moveTask(Task task, TaskList previousTaskList, TaskList currentTaskList) {
        task = new Task(task);
        previousTaskList.removeTask(task.getUuid());
        currentTaskList.addTask(task);
        replaceTaskList(previousTaskList, previousTaskList);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(this.allEmployees);
    }

    public UUID getUUID () {
        return this.uuid;
    }

    private Status decideStatus () {
        if (!tasksAssigned() && isStarted() && allTasksFinished()) {
            return new Status(Status.Progress.FINISHED);
        }
        else if (!tasksAssigned() && isStarted() && !fundsAvailable()){
            return new Status(Status.Progress.ONHOLD);
        }
        else if (!tasksAssigned() && isStarted() && fundsAvailable()){
            return new Status(Status.Progress.EXECUTING);
        }
        else if (!tasksAssigned() && !isStarted()) {
            return new Status(Status.Progress.READY);
        }
        else return new Status(Status.Progress.CREATED);
        //Think of how to do on hold
    }

    private boolean tasksAssigned () {return taskLists.isEmpty();}

    private boolean isStarted () {
        return timeSpent > 0;
    }

    private boolean fundsAvailable () {
        return this.availableFunds > 0;
    }

    private boolean allTasksFinished () {
        for (TaskList currentTaskList : taskLists) {
            for (Task task : currentTaskList.getTaskList()) {
                if (task.getStatus().getProgress() != Status.Progress.FINISHED) return false;
            }
        }
        return true;
    }
}
