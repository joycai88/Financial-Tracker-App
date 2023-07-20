package ui;

import model.Account;
import model.Budget;
import model.Expense;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.listeners.AccountListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//Represents the graphical user interface (GUI) of the application
public class TrackerAppGUI extends JFrame implements ActionListener, WindowListener {

    //Account related fields
    private Account account;
    private JTextField accountName;
    private JPanel accountPanel;

    //Budget related fields
    private JPanel budgetPanel;
    private Budget selectedBudget;
    private DefaultListModel budgets;
    private JList budgetList;
    private JScrollPane listScrollPane;
    private JButton selectBtn;
    private JFrame newBudget;
    private JTextField budgetName;
    private JTextField amountValue;
    private JButton remove;

    //Expense related fields
    private DefaultListModel expenses;
    private JList expensesList;
    private JFrame newExpense;
    private JTextField expenseName;
    private JTextField expenseValue;
    private JTextField expenseDate;

    //Universal buttons
    private JButton saveBtn;

    //Listeners
    private AccountListener accountListener = new AccountListener();

    //GUIs
    private StartScreenGUI startScreenGUI = new StartScreenGUI();

    //JSON related fields
    private static final String JSON_STORE = "./data/account.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: Initializes the application and shows start screen
    public TrackerAppGUI() {
        super("Student Expenses Tracker");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(startScreenGUI.makeStartScreen());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: initializes panel to default settings
    public void panelInit(JPanel panel) {
        panel.setPreferredSize(new Dimension(400, 200));
        panel.setBorder(new EmptyBorder(13, 13, 13, 13));
        panel.setLayout(new FlowLayout());
    }

    //MODIFIES: this
    //EFFECTS: sets content of the window to specified panel
    public void setPanel(JPanel panel) {
        setContentPane(panel);
        invalidate();
        validate();
    }

    //MODIFIES: this
    //EFFECTS: loads account from file
    public void loadAccount() {
        try {
            account = jsonReader.read();
            accountPanel(account);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    //EFFECTS: saves the account to file
    public void saveFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }

        JFrame saveAlert = new JFrame();
        JLabel alert = new JLabel("File saved!");
        saveAlert.add(alert);
        saveAlert.pack();
        saveAlert.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Screen to create new account
    public void newAccount() {
        JPanel newAccountMaker = new JPanel();
        panelInit(newAccountMaker);
        newAccountMaker.setLayout(new BoxLayout(newAccountMaker, BoxLayout.Y_AXIS));

        JLabel promptName = new JLabel("Enter the name of your new account");
        accountName = new JTextField(5);
        accountName.setPreferredSize(new Dimension(50,10));
        accountName.setAlignmentY(Component.CENTER_ALIGNMENT);
        JButton createAccountBtn = new JButton("Create Account");
        createAccountBtn.setActionCommand("makeAccount");
        createAccountBtn.addActionListener(this);

        newAccountMaker.add(promptName);
        newAccountMaker.add(accountName);
        newAccountMaker.add(createAccountBtn);

        setPanel(newAccountMaker);
    }

    //EFFECTS: processes commands for when buttons are clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("makeAccount")) {
            account = new Account(accountName.getText());
            accountPanel(account);
        } else if (e.getActionCommand().equals("addBudget")) {
            addBudgetWindow();
        } else if (e.getActionCommand().equals("selectBudget")) {
            budgetSelector();
        } else if (e.getActionCommand().equals("addExpense")) {
            addExpenseWindow();
        } else if (e.getActionCommand().equals("createBudget")) {
            account.addBudget(budgetName.getText(), Double.parseDouble(amountValue.getText()));
            addBudgets(budgets, account.getBudgets());
            newBudget.dispose();
        } else if (e.getActionCommand().equals("createExpense")) {
            createExpense();
        } else if (e.getActionCommand().equals("saveFile")) {
            saveFile();
        } else if (e.getActionCommand().equals("toAccount")) {
            accountPanel(account);
        } else if (e.getActionCommand().equals("removeBudget")) {
            budgetRemover();
        }
    }

    private void createExpense() {
        selectedBudget.addExpense(expenseName.getText(), Double.valueOf(expenseValue.getText()),
                expenseDate.getText());
        addExpenses(expenses);
        newExpense.dispose();
    }

    //MODIFIES: this
    //EFFECTS: creates the account panel with related components
    public void accountPanel(Account account) {
        accountPanel = new JPanel();
        panelInit(accountPanel);
        accountPanel.setLayout(new BorderLayout());

        String name = account.getAccountName();
        budgets = new DefaultListModel();
        budgets = addBudgets(budgets, account.getBudgets());

        JLabel header = new JLabel(name + " Account");

        accountPanel.add(header, BorderLayout.NORTH);
        saveButton();
        budgetList(budgets);

        addWindowListener(this);

        setPanel(accountPanel);
    }

    //MODIFIES: this
    //EFFECTS: Creates visual list of budgets
    public void budgetList(DefaultListModel budgets) {
        budgetList = new JList(budgets);
        budgetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        budgetList.setSelectedIndex(0);
        budgetList.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(budgetList);

        budgetOptions();
    }

    //MODIFIES: this
    //EFFECTS: creates buttons related to adding Xs to Y
    private void budgetOptions() {
        JButton addBudgetBtn = new JButton("New Budget");
        addBudgetBtn.setActionCommand("addBudget");
        addBudgetBtn.addActionListener(this);

        selectBtn = new JButton("Select");
        selectBtn.setActionCommand("selectBudget");
        selectBtn.addActionListener(this);

        remove = new JButton("Remove");
        remove.setActionCommand("removeBudget");
        remove.addActionListener(this);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(addBudgetBtn);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(remove);
        buttonPane.add(selectBtn);
        buttonPane.add(saveBtn);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        accountPanel.add(listScrollPane, BorderLayout.CENTER);
        accountPanel.add(buttonPane, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: shows budgets in account on the visual list
    private DefaultListModel addBudgets(DefaultListModel budgets, List<Budget> accountBudgets) {
        for (Budget b : accountBudgets) {
            if (!budgets.contains(b.getName())) {
                budgets.addElement(b.getName());
            }
        }

        return budgets;
    }

    //MODIFIES: this
    //EFFECTS: removes selected budget from account
    public void budgetRemover() {
        int index = budgetList.getSelectedIndex();
        if (index >= 0) {
            Budget selectedBudget = this.account.getBudgets().get(index);
            this.account.removeBudget(selectedBudget.getName());
            budgets.removeElement(selectedBudget.getName());
            addBudgets(budgets, account.getBudgets());
            setPanel(accountPanel);
        }
    }

    //REQUIRES: text boxes are not empty
    //MODIFIES: this
    //EFFECTS: opens window that prompts user to create a budget
    public void addBudgetWindow() {
        newBudget = new JFrame("New Budget Creator");
        JPanel newBudgetPanel = new JPanel();
        JLabel promptName = new JLabel("Please enter the budget name");
        budgetName = new JTextField(5);
        JLabel promptAmount = new JLabel("Please enter the monthly amount");
        amountValue = new JTextField(5);
        JButton createBudgetBtn = new JButton("Create");

        createBudgetBtn.setActionCommand("createBudget");
        createBudgetBtn.addActionListener(this);

        newBudgetPanel.add(promptName);
        newBudgetPanel.add(budgetName);
        newBudgetPanel.add(promptAmount);
        newBudgetPanel.add(amountValue);
        newBudgetPanel.add(createBudgetBtn);

        panelInit(newBudgetPanel);

        newBudget.add(newBudgetPanel);
        newBudget.pack();
        newBudget.setVisible(true);
    }

    //EFFECTS: identifies the budget that user wants to select
    public void budgetSelector() {
        int index = budgetList.getSelectedIndex();
        if (index != -1) {
            selectedBudget = account.getBudgets().get(index);
            budgetPanel(selectedBudget);
        }
    }


    //MODIFIES: this
    //EFFECTS: creates components related to the budget panel
    public void budgetPanel(Budget budget) {
        budgetPanel = new JPanel();
        JLabel budgetName = new JLabel(budget.getName());
        JLabel budgetAmount = new JLabel("Total amount remaining: $" + budget.getBudgetRemaining());

        panelInit(budgetPanel);
        JButton backBtn = new JButton("Back");

        backBtn.setActionCommand("toAccount");
        backBtn.addActionListener(this);

        budgetPanel.add(budgetName);
        budgetPanel.add(budgetAmount);
        expensesList();
        saveButton();
        budgetPanel.add(saveBtn);
        budgetPanel.add(backBtn);

        setPanel(budgetPanel);
    }

    //MODIFIES: this
    //EFFECTS: creates visual list of expenses
    public void expensesList() {
        expenses = new DefaultListModel();
        addExpenses(expenses);

        expensesList = new JList(expenses);
        expensesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        expensesList.setSelectedIndex(0);
        budgetList.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(expensesList);

        budgetPanel.add(listScrollPane);

        expenseOptions();
    }

    //MODIFIES: this
    //EFFECTS: adds new expenses to visual list of expenses
    private void addExpenses(DefaultListModel expenses) {
        List<Expense> budgetExpenses = selectedBudget.getExpenses();
        for (Expense e : budgetExpenses) {
            if (!expenses.contains(e.getExpenseName())) {
                this.expenses.addElement(e.getExpenseName() + " (" + e.getDate() + ") " + " - $" + e.getPrice());
            }
        }
    }

    //EFFECTS: specifications for save button
    public void saveButton() {
        saveBtn = new JButton("Save");
        saveBtn.setActionCommand("saveFile");
        saveBtn.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: buttons related to expenses
    private void expenseOptions() {
        JButton addExpenseBtn = new JButton("New Expense");
        addExpenseBtn.setActionCommand("addExpense");
        addExpenseBtn.addActionListener(this);

        budgetPanel.add(addExpenseBtn);

        JPanel expensePane = new JPanel();
        expensePane.setLayout(new BoxLayout(expensePane, BoxLayout.LINE_AXIS));
        expensePane.add(addExpenseBtn);
        expensePane.add(Box.createHorizontalStrut(5));
        expensePane.add(new JSeparator(SwingConstants.VERTICAL));
        expensePane.add(Box.createHorizontalStrut(5));
        expensePane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        budgetPanel.add(listScrollPane, BorderLayout.CENTER);
        budgetPanel.add(expensePane, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: launches new frame that prompts user to create a new expense
    public void addExpenseWindow() {
        newExpense = new JFrame("New Expense Creator");
        JPanel newExpensePanel = new JPanel();
        JLabel promptName = new JLabel("Please enter the name of your expense");
        expenseName = new JTextField(5);
        JLabel promptAmount = new JLabel("Please enter the total cost");
        expenseValue = new JTextField(5);
        JLabel promptDate = new JLabel("Please enter the date of purchase");
        expenseDate = new JTextField(5);
        JButton createExpenseBtn = new JButton("Create");

        createExpenseBtn.setActionCommand("createExpense");
        createExpenseBtn.addActionListener(this);

        newExpensePanel.add(promptName);
        newExpensePanel.add(expenseName);
        newExpensePanel.add(promptAmount);
        newExpensePanel.add(expenseValue);
        newExpensePanel.add(promptDate);
        newExpensePanel.add(expenseDate);
        newExpensePanel.add(createExpenseBtn);

        panelInit(newExpensePanel);

        newExpense.add(newExpensePanel);
        newExpense.pack();
        newExpense.setVisible(true);
        newExpense.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //EFFECTS: prints EventLog to console when application is closed
    @Override
    public void windowClosing(WindowEvent e) {
        account.printLog();
    }

    //Unused window listeners
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
