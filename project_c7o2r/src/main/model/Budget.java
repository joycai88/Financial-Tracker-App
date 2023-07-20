package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents a budget with a name and monthly spending limit, containing a list of expenses
public class Budget implements Writable {

    private final String name;
    private final double monthlyAmount;
    private double remainingAmount;
    private final ArrayList<Expense> expenses;

    //REQUIRES: Name of budget has a non-zero length, monthly budget is >=0
    //EFFECTS: Assigns a user given name and dollar amount to the budget,
    //         creates an empty list of expenses
    public Budget(String budgetName, double amount) {
        this.name = budgetName;
        this.monthlyAmount = amount;
        remainingAmount = monthlyAmount;
        this.expenses = new ArrayList<>();
    }

    //REQUIRES: Name has a non-zero length, price is >= 0, date is in the format mm-dd-yyyy
    //MODIFIES: this
    //EFFECTS: Adds expense to list of expenses in budget, reduces budget by the amount
    //         returns true if under budget, otherwise false
    public boolean addExpense(String expenseName, Double price, String date) {
        Expense expense = new Expense(expenseName, price, date);
        this.expenses.add(expense);
        remainingAmount -= expense.getPrice();
        EventLog.getInstance().logEvent(new Event(expenseName + " added to " + name));
        return remainingAmount >= 0;
    }

    //EFFECTS: returns true if total spending is under budget, otherwise false
    public boolean withinBudget() {
        return remainingAmount >= 0;
    }
    
    //EFFECTS: returns the remaining budget, if over budget return 0
    public double getBudgetRemaining() {
        if (remainingAmount >= 0) {
            return remainingAmount;
        } else {
            return 0;
        }
    }
    
    //EFFECTS: Returns the name of the budget
    public String getName() {
        return name;
    }

    //EFFECTS: Returns the monthly amount of the budget
    public double getAmount() {
        return monthlyAmount;
    }
    
    //EFFECTS: Returns a list of all the expenses in the budget
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", monthlyAmount);
        json.put("expenses", expensesToJson());
        return json;
    }

    // EFFECTS: returns expenses in this budget as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : expenses) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
