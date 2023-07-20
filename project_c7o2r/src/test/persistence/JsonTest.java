package persistence;

import model.Budget;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBudget(String name, double amount, int expenses, Budget budget) {
        assertEquals(name, budget.getName());
        assertEquals(amount, budget.getAmount());
        assertEquals(expenses, budget.getExpenses().size());
    }
}
