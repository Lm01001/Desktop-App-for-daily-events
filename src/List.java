import java.util.Scanner;

public class List implements Event {

    Scanner choice = new Scanner(System.in);
    public static String name = "Default name";
    static int priority = 1;

    public List(int priority, String name) {     //List's constructor
        this.priority = priority;
        this.name = name;
    }

    @Override
    public void setPriority(int priority) {     //Setting default priority

        this.priority = choice.nextInt();;
    }

    @Override
    public void setName(String name) {      //Setting action's name
        System.out.println("Please choose name for your task: ");
        this.name = choice.nextLine();;
    }

}
