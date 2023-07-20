package ui;

import model.Account;
import model.Budget;
import model.EventLog;
import model.Expense;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Expenses tracker application
public class TrackerApp {

    private static final String JSON_STORE = "./data/account.json";
    private String accountName;
    private Scanner input;
    private Account account;
    private Integer selector = 1;
    private Budget currentBudget;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the expense tracker application
    public TrackerApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command;

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        selector = 3;
        startingMenu();

        while (keepGoing) {
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                if (selector == 0) {
                    processCommandAccount(command);
                } else if (selector == 1) {
                    processCommandBudget(command);
                } else {
                    processStartCommand(command);
                }
            }
        }
        account.printLog();
        System.out.println("\nClosing application...");
    }

    //EFFECTS: displays options to load account from file or start a new
    //         account.
    public void startingMenu() {
        System.out.println("l -> load account from file");
        System.out.println("n -> start a new account");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from starting menu
    private void processStartCommand(String command) {
        if (command.equals("l")) {
            loadAccount();
            selector = 0;
            mainMenu();
        } else if (command.equals("n")) {
            init();
            selector = 0;
            mainMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new account
    private void init() {
        System.out.print("Please enter the name of your account:");

        accountName = input.nextLine();
        account = new Account(accountName);

    }

    // EFFECTS: displays the account of the user with the budgets inside
    //          if there are no budgets added, prompt user to make a new one
    private void mainMenu() {
        System.out.println("\n" + account.getAccountName() + " Account");
        ArrayList<Budget> budgets = account.getBudgets();
        if (budgets.size() == 0) {
            System.out.println("You currently have no budgets");
            addBudget();
        } else {
            System.out.println("Current budgets in account:");
            displayBudgets(account);
            accountMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: displays the budget with the given name and the expenses inside
    //         return to account page if given budget name does not exist
    //         alert user if they are spending over budget
    private void budgetDisplay(String name) {
        ArrayList<Budget> budgets = account.getBudgets();
        for (Budget b: budgets) {
            if (b.getName().equals(name)) {
                System.out.println("\nSelected budget: " + b.getName());
                System.out.println("Monthly budget is $" + b.getAmount());
                System.out.println("You have $" + b.getBudgetRemaining() + " remaining.");
                System.out.println("History of expenses:");
                displayExpenses(b);

                if (!b.withinBudget()) {
                    alert();
                }

                selector = 1;
                currentBudget = b;
                budgetMenu();
                return;
            }
        }
        System.out.println("There is no budget by that name, returning to main page...");
        selector = 0;
        mainMenu();
    }

    //EFFECTS: displays menu of options within an account
    private void accountMenu() {
        System.out.println("\ns -> select a budget");
        System.out.println("a -> add budget");
        System.out.println("r -> remove budget");
        System.out.println("m -> save account to file");
        System.out.println("l -> load account from file");
        System.out.println("q -> quit application");
    }

    //EFFECTS: displays menu of options within a budget
    private void budgetMenu() {
        System.out.println("\nm -> return to main account");
        System.out.println("e -> add expense");
        System.out.println("q -> quit application");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from account
    private void processCommandAccount(String command) {
        if (command.equals("s")) {
            System.out.print("Please enter the name of your budget:");
            String name = input.nextLine();
            budgetDisplay(name);
        } else if (command.equals("a")) {
            addBudget();
        } else if (command.equals("r")) {
            removeBudget();
        } else if (command.equals("m")) {
            saveAccount();
        } else if (command.equals("l")) {
            loadAccount();
        } else {
            System.out.println("Selection not valid, please make another selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command from budget
    private void processCommandBudget(String command) {
        if (command.equals("m")) {
            mainMenu();
            selector = 0;
        } else if (command.equals("e")) {
            addExpense();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: conducts the creation of a new budget
    private void addBudget() {
        System.out.print("Please enter the name of your new budget:");
        String budgetName = input.nextLine();
        System.out.print("Enter the monthly amount:");
        double amount = input.nextDouble();
        input.nextLine();

        account.addBudget(budgetName, amount);

        System.out.println("You have created a budget called " + budgetName
                + " with a monthly amount of " + amount);

        selector = 1;
        budgetDisplay(budgetName);
    }

    //MODIFIES: this
    //EFFECTS: removes selected budget from the account
    private void removeBudget() {
        System.out.print("Please enter the name of the budget you want to remove:");
        String budgetName = input.nextLine();
        account.removeBudget(budgetName);

        selector = 0;
        mainMenu();
    }

    //EFFECTS: Displays names of all the budgets stored in the account
    private void displayBudgets(Account account) {
        ArrayList<Budget> budgets = account.getBudgets();
        for (Budget b : budgets) {
            System.out.println("\t- " + b.getName());
        }
    }

    //EFFECTS: Displays information of all the expenses stored in the budget
    private void displayExpenses(Budget budget) {
        ArrayList<Expense> expenses = budget.getExpenses();
        for (Expense e : expenses) {
            System.out.println("\t- " + e.getExpenseName() + " : $" + e.getPrice()
                    + " (" + e.getDate() + ")");
        }
    }

    //MODIFIES: this
    //EFFECTS: conducts the creation of a new expense
    private void addExpense() {
        System.out.print("Enter a name for the expense:");
        String name = input.nextLine();
        System.out.print("Enter the price:");
        double price = input.nextDouble();
        input.nextLine();
        System.out.print("Enter the date of the purchase(MM-DD-YYYY):");
        String date = input.nextLine();

        currentBudget.addExpense(name, price, date);
        selector = 1;
        budgetDisplay(currentBudget.getName());
    }

    //EFFECTS: prints alert message to console for overspending
    public void alert() {
        System.out.println("You are spending over budget!");
    }

    // EFFECTS: saves the account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            System.out.println("Saved " + account.getAccountName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {
        try {
            account = jsonReader.read();
            System.out.println("Loaded " + account.getAccountName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
