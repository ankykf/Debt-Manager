package ui;

import info.*;

import java.io.IOException;
import java.util.Scanner;

public class Input {
    private DebtsList normalUrgentDebtsList;
    private DebtsList recurringDebtsList;
    private Debt debt;
    private int amount;
    private String who;
    private String oweOrOwed;
    private String dueDate;
    private boolean recurring;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Input();


    }

    public Input() throws IOException, ClassNotFoundException {
        normalUrgentDebtsList = new NormalUrgentDebtsList();
        recurringDebtsList = new RecurringDebtsList();
        dueDate = "";
        run();
    }

    //EFFECTS: runs a loop that gets user input for data
    public void run() throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        askLoadOrNew();
        while (true) {
            askViewOrInput();
            System.out.println("Would you like to continue? (Type Yes or No)");
            String done = input.next();
            if (done.equalsIgnoreCase("No")) {
                break; // referenced from B04 simple calculator lecture lab
            }
        }
        normalUrgentDebtsList.save();
        recurringDebtsList.saveRec();
    }

   /* // EFFECTS: collects more user input and checks that it makes sense, if not throw exception
    public void userInput() {
                    askDebtType();
            logRegularResult();
        } else if (answer.equalsIgnoreCase("Recurring")) {
            askDebtType();
            logRecurrentResult();
        } else {
            System.out.println("You didn't type a recognized answer");
        }
    }*/

    private void logRecurrentResult() {
        try {
            recurringDebtsList.logResult(debt, amount, oweOrOwed, who, dueDate);
        } catch (IntException e) {
            System.out.println("You entered a negative or zero amount!\nPlease enter your entry again.");
            askDebtType();
        } catch (OweException o) {
            System.out.println("You did not state whether this person owes or is owed by you!\n"
                    + "Please enter you entry again.");
            askDebtType();
        }
    }

    private void logRegularResult() {
        try {
            normalUrgentDebtsList.logResult(debt, amount, oweOrOwed, who, dueDate);
        } catch (IntException e) {
            System.out.println("You entered a negative or zero amount!\nPlease enter your entry again.");
            /*userInput();*/
            askDebtType();
        } catch (OweException o) {
            System.out.println("You did not state whether this person owes or is owed by you!\n"
                    + "Please enter you entry again.");
            /*userInput();*/
            askDebtType();
        }
    }

    private void askViewOrInput() {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to view your Debts, create, or delete an entry? (Type 'View' or 'Entry')");
        String answer = input.next();
        if (answer.equalsIgnoreCase("view")) {
            System.out.println("Would you like to see your recurring or regular debts? (Type 'Recur' or 'Regular')");
            String answertwo = input.next();
            if (answertwo.equalsIgnoreCase("regular") && !normalUrgentDebtsList.getListOfDebt().isEmpty()) {
                printRegularList();
            } else if (answertwo.equalsIgnoreCase("recur") && !recurringDebtsList.getListOfDebtRe().isEmpty()) {
                printRecurringList();
            } else if (!answertwo.equalsIgnoreCase("regular") && !answertwo.equalsIgnoreCase("recur")) {
                System.out.println("You didn't type one of the two options.");
            } else {
                System.out.println("You have no debts in that category!");
            }
        } else {
            askDebtType();
        }
    }


    private void askDebtType() {
        Scanner input = new Scanner(System.in);
        System.out.println("What would you like to do:\n"
                + "Create an urgent debt (Type 'Urgent')\n"
                + "Create a regular debt (Type 'Regular')\n"
                + "Create a recurring debt (Type 'Recurring')\n");
        String answer = input.next();
        if (answer.equalsIgnoreCase("Regular")) {
            normalDebt();
            logRegularResult();
        } else if (answer.equalsIgnoreCase("Urgent")) {
            urgentDebt();
            logRegularResult();
        } else if (answer.equalsIgnoreCase("Recurring")) {
            recurringDebt();
            logRecurrentResult();
        } else {
            System.out.println("You didn't enter a recognized answer!");
        }
    }

    private void recurringDebt() {
        Scanner input = new Scanner(System.in);
        debt = new RecurringDebt();
        System.out.println("How often is this debt due? (every '...')");
        dueDate = input.next();
        debtInput(debt);
    }

    public void normalDebt() {
        debt = new NormalDebt();
        debtInput(debt);


    }

    public void urgentDebt() {
        Scanner input = new Scanner(System.in);
        debt = new UrgentDebt();
        System.out.println("What is the date this debt is due?");
        dueDate = input.next();
        debtInput(debt);

    }

    public void askLoadOrNew() throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to load a previous list or create a new list of debts? "
                + "(Type 'Load' or 'New')");
        String loadOrNew = input.next();
        if (loadOrNew.equalsIgnoreCase("Load")) {
            normalUrgentDebtsList.load();
            recurringDebtsList.loadRec();
        }
    }

    // REQUIRES: Person
    // EFFECTS: passes user input into logResult to be logged
    public void debtInput(Debt person) {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you owe money or are you owed money? (Type Owe or Owed)");
        oweOrOwed = input.next();
        if (oweOrOwed.equalsIgnoreCase("Owed")) {
            System.out.println("Please enter the amount owed to you (No dollar signs please)");
            amount = input.nextInt();
            System.out.println("Who owes you this money?");
            who = input.next();


        } else {
            System.out.println("Please enter the amount you owe");
            amount = input.nextInt();
            System.out.println("Who do you owe this money to?");
            who = input.next();


        }

    }

    public void printRegularList() {
        for (Debt debt : normalUrgentDebtsList.getListOfDebt()) {
            System.out.println(debt.reminder());
        }
    }

    public void printRecurringList() {
        for (Debt debt : recurringDebtsList.getListOfDebtRe()) {
            System.out.println(debt.reminder());
        }
    }
}