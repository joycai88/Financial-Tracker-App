package persistence;

import model.Account;
import model.Budget;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("Student account", acc.getAccountName());
            assertEquals(0, acc.getBudgets().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("Student account", acc.getAccountName());
            List<Budget> budgets = acc.getBudgets();
            assertEquals(2, budgets.size());
            checkBudget("entertainment", 200, 2, budgets.get(0));
            checkBudget("food", 50.50, 0, budgets.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
