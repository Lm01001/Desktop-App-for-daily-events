public class Main {
    public static void main(String[] args) {
        //List list = new List(List.priority, List.name);
        Task task = new Task(List.priority, List.name);
        task.showExistingTasks();
        System.out.println();
        System.out.println(task);
        System.out.println();
        task.deleteTask();
        System.out.println();
        task.showExistingTasks();
    }
}