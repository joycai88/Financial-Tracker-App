package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setup() {
        account = new Account("Primary");
    }

    @Test
    public void testConstructor() {
        assertEquals("Primary", account.getAccountName());
        ArrayList<Budget> budgets = account.getBudgets();
        assertEquals(0, budgets.size());
    }

    @Test
    public void testAddBudget() {
        account.addBudget("Budget1", 100.00);
        account.addBudget("Budget2", 20.50);

        ArrayList<Budget> budgets = account.getBudgets();
        assertEquals(2, budgets.size());
        assertEquals(0, account.getBudgetIndex("Budget1"));
        assertEquals(1, account.getBudgetIndex("Budget2"));
    }

    @Test
    public void testBudgetIndexNA() {
        assertEquals(-1, account.getBudgetIndex("BudgetNA"));
    }

    @Test
    public void testRemoveBudget() {
        account.addBudget("Budget1" , 100.00);
        account.addBudget("Budget2", 20.50);
        account.removeBudget("Budget1");
        assertEquals(1, account.getBudgets().size());
    }

    @Test
    public void testRemoveNone() {
        account.addBudget("Budget1" , 100.00);
        account.addBudget("Budget2", 20.50);
        account.removeBudget("Budget3");
        assertEquals(2, account.getBudgets().size());
    }
}
