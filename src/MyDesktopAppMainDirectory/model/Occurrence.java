package MyDesktopAppMainDirectory.model;
import javax.swing.*;
import java.util.Scanner;

public class Occurrence implements Event {
    Scanner choice = new Scanner(System.in);

    private int priority = 1;
    public int getPriority() {
        return priority;
    }

    String name = "Default name";
    public String getName() {
        return name;
    }

    private String decision = "no";
    public String getDecision() {
        return decision;
    }
    public void setDecision(String decision) {
        this.decision = decision;
    }

    private String answer = "";
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Occurrence(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    //No-argument constructor to use in Db class to initialize default class object
    public Occurrence() {
        this.priority = 0;
        this.name = "Default name";
    }

    @Override
    public void setPriority(int priority) {
        Object[] choice = {"Optional", "Low priority", "High priority"};
        this.priority = JOptionPane.showOptionDialog(null,
                "Please choose priority for your event: ",
                "Choose task's priority", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, choice, choice[1]);
        /*while(true){
            System.out.print("Please choose priority from range 1-3, ");
            System.out.print("where 1 means lower priority, 2 means normal - " +
                    "not something important, but to do ");
            System.out.println("and 3 means very high priority. ");
            try{
                this.priority = Integer.parseInt(choice.nextLine());
                if(this.priority >= 1 && this.priority <= 3){
                    break;
                }else{
                    System.out.print("Number out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }*/
    }

    @Override
    public void setName(int c) {
        //System.out.println("Please choose name for your event: ");
        //this.name = choice.nextLine();
        boolean check = true;
        while(check){
            this.name = JOptionPane.
                    showInputDialog("Please choose name of the new event: ");
            if(!name.equals("Default name") && name.length() < 75){
                check = false;
            } if(name.length() > 75) {
                JOptionPane.showMessageDialog(null, "Name is too long!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            break;
        }
    }

    @Override
    public String ifStillInProgress() {
        System.out.println("Do You want to add another event? Otherwise the document will be closed");
        setAnswer(choice.nextLine().trim().toLowerCase());
        setDecision(getAnswer());
        if(getDecision().equals("yes") || getDecision().startsWith("y")){
            return "yes";
        } else {
            return "no";
        }
    }

    @Override
    public int getIndex() {
        return 1;
    }
}
