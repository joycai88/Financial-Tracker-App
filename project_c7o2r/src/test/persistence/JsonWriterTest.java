package persistence;

import model.Account;
import model.Budget;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account("Student account");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account acc = new Account("Student account");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals("Student account", acc.getAccountName());
            assertEquals(0, acc.getBudgets().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account acc = new Account("Student account");
            acc.addBudget("entertainment",200);
            acc.addBudget("food",50.50);
            List<Budget> budgets = acc.getBudgets();
            budgets.get(0).addExpense("ent1",17.0,"01-02-2023");
            budgets.get(0).addExpense("ent2",30.75,"01-03-2023");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals("Student account", acc.getAccountName());
            assertEquals(2, budgets.size());
            checkBudget("entertainment", 200, 2, budgets.get(0));
            checkBudget("food", 50.50, 0, budgets.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
