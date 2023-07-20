package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Account;

import model.Budget;
import model.Expense;
import org.json.*;

//Modelled after JsonSerializationDemo
// Represents a reader that reads account from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Account acc = new Account(name);
        addBudgets(acc, jsonObject);
        return acc;
    }

    // MODIFIES: acc
    // EFFECTS: parses budgets from JSON object and adds them to the account
    private void addBudgets(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("budgets");
        for (Object json : jsonArray) {
            JSONObject nextBudget = (JSONObject) json;
            addBudget(acc, nextBudget);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses budget from JSON object and adds it to account
    private void addBudget(Account acc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("amount");
        acc.getBudgets().add(parseBudget(jsonObject));
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("amount");
        Budget budget = new Budget(name, amount);
        addExpenses(budget, jsonObject);
        return budget;
    }

    // MODIFIES: budget
    // EFFECTS: parses expenses from JSON object and adds them to the account
    private void addExpenses(Budget budget, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(budget, nextExpense);
        }
    }

    // MODIFIES: budget
    // EFFECTS: parses expense from JSON object and adds it to budget
    private void addExpense(Budget budget, JSONObject jsonObject) {
        String name = jsonObject.getString("expenseName");
        double amount = jsonObject.getDouble("expenseAmount");
        String date = jsonObject.getString("date");
        budget.getExpenses().add(parseExpense(jsonObject));
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Expense parseExpense(JSONObject jsonObject) {
        String name = jsonObject.getString("expenseName");
        double amount = jsonObject.getDouble("expenseAmount");
        String date = jsonObject.getString("date");
        Expense expense = new Expense(name, amount, date);
        return expense;
    }
}
