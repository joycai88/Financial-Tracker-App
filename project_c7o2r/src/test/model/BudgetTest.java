package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class BudgetTest {
    private Budget budget;

    @BeforeEach
    public void setUp() {
        budget = new Budget("Budget1", 100.0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Budget1", budget.getName());
        assertEquals(100, budget.getBudgetRemaining());
        assertEquals(100, budget.getAmount());
        ArrayList<Expense> expenses = budget.getExpenses();
        assertEquals(0,expenses.size());
    }

    @Test
    public void testAddExpenseTrue() {
        assertTrue(budget.addExpense("Expense1", 50.0, "2023-01-01"));
        ArrayList<Expense> expenses = budget.getExpenses();
        assertEquals(1, expenses.size());
        assertEquals(50.0, budget.getBudgetRemaining());
    }

    @Test
    public void testAddExpenseFalse() {
        budget.addExpense("Expense1", 50.0, "2023-01-01");
        assertFalse(budget.addExpense("Expense2", 150.0, "2023-02-01"));
        ArrayList<Expense> expenses = budget.getExpenses();
        assertEquals(2, expenses.size());
        assertEquals(0, budget.getBudgetRemaining());
    }

    @Test
    public void testAddExpenseZero() {
        assertTrue(budget.addExpense("Expense1", 100.0, "2023-01-01"));
        ArrayList<Expense> expenses = budget.getExpenses();
        assertEquals(1, expenses.size());
        assertEquals(0, budget.getBudgetRemaining());
    }

    @Test
    public void testWithinBudgetTrue() {
        budget.addExpense("Expense1", 50.0, "2023-01-01");
        assertTrue(budget.withinBudget());
    }

    @Test
    public void testWithinBudgetFalse() {
        budget.addExpense("Expense1", 150.0, "2023-01-01");
        assertFalse(budget.withinBudget());
    }

    @Test
    public void testBudgetRemainingUnder() {
        budget.addExpense("Expense1", 50.0, "2023-01-01");
        assertEquals(50.0, budget.getBudgetRemaining());
    }

    @Test
    public void testBudgetRemainingOver() {
        budget.addExpense("Expense1", 150.0, "2023-01-01");
        assertEquals(0, budget.getBudgetRemaining());
    }
}