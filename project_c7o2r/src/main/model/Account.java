package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents a user's account that stores all the budgets
public class Account implements Writable {

    private final ArrayList<Budget> budgets;
    private final String name;

    //REQUIRES: Name of account has a non-zero length
    //EFFECTS: Make an empty account with given name and no budgets
    public Account(String accountName) {
        this.name = accountName;
        this.budgets = new ArrayList<>();
    }

    //REQUIRES: budgetName is a non-zero length and amount is >= 0
    //MODIFIES: this
    //EFFECTS: adds new budget to the account
    public void addBudget(String budgetName, double amount) {
        Budget budget = new Budget(budgetName, amount);
        this.budgets.add(budget);
        EventLog.getInstance().logEvent(new Event(budgetName + " created with a monthly amount of $"
                + amount));
    }

    //REQUIRES: budgetName is the name of a budget existing in the account
    //MODIFIES: this
    //EFFECTS: removes budget from account
    public void removeBudget(String budgetName) {
        for (Budget b : this.budgets) {
            if (b.getName().equals(budgetName)) {
                this.budgets.remove(b);
                EventLog.getInstance().logEvent(new Event(budgetName + " was removed from account"));
                return;
            }
        }
    }

    //EFFECTS: returns the name of the account
    public String getAccountName() {
        return name;
    }

    //EFFECTS: returns a list of the all budgets in the account
    public ArrayList<Budget> getBudgets() {
        return budgets;
    }

    //EFFECTS: returns the index of the budget in the list given the budget name
    //         if not found return -1.
    public int getBudgetIndex(String budgetName) {
        int i = 0;
        for (Budget b : budgets) {
            if (b.getName().equals(budgetName)) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("budgets", budgetsToJson());
        return json;
    }

    // EFFECTS: returns budgets in this account as a JSON array
    private JSONArray budgetsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Budget b : budgets) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

    public void printLog() {
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}
