package model;

import org.json.JSONObject;
import persistence.Writable;

public class Expense implements Writable {
    private final String expenseName;
    private final double price;
    private final String date;

    //REQUIRES: Name has a non-zero length, price is >= 0, date is in the format mm-dd-yyyy
    //EFFECT: Creates an expense with user provided name, amount, and date
    public Expense(String expenseName, Double price, String date) {
        this.expenseName = expenseName;
        this.price = price;
        this.date = date;
    }

    //EFFECT: returns the name of the expense
    public String getExpenseName() {
        return expenseName;
    }

    //EFFECT: returns the price of the expense
    public double getPrice() {
        return price;
    }

    //EFFECT: returns the date of the expense
    public String getDate() {
        return date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenseName", expenseName);
        json.put("expenseAmount", price);
        json.put("date", date);
        return json;
    }
}
