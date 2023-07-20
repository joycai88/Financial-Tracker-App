package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static  org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    Expense expense;

    @BeforeEach
    public void setUp() {
        expense = new Expense("Concert ticket", 100.50, "2023-01-01");
    }

    @Test
    public void testConstructor() {
        assertEquals("Concert ticket", expense.getExpenseName());
        assertEquals(100.50, expense.getPrice());
        assertEquals("2023-01-01", expense.getDate());
    }
}
