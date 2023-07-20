package ui;

import ui.listeners.AccountListener;

import javax.swing.*;
import java.awt.*;

//Represents the starting screen when the application is first launched
public class StartScreenGUI extends Functionality {

    private AccountListener accountListener = new AccountListener();
    private JPanel startScreen;

    //Components
    private JLabel backgroundLabel;
    private JButton newAccountBtn;
    private JButton loadAccountBtn;
    private JLabel title;
    private JPanel buttonPanel;


    //MODIFIES: this
    //EFFECTS: Creates panel that has components of the start screen
    public Component makeStartScreen() {
        startScreen = new JPanel();
        panelInit(startScreen);
        startScreen.setLayout(new OverlayLayout(startScreen));

        newAccountBtn = new JButton("Create new account");
        loadAccountBtn = new JButton("Load account from file");
        title = new JLabel("Student Expenses Tracker");
        newAccountBtn.setActionCommand("newAccount");
        newAccountBtn.addActionListener(accountListener);
        loadAccountBtn.setActionCommand("loadAccount");
        loadAccountBtn.addActionListener(accountListener);

        customizeScreen();

        startComponents();

        startScreen.add(buttonPanel);
        startScreen.add(backgroundLabel);
        return startScreen;
    }

    //EFFECTS: stores components for start screen customization
    private void customizeScreen() {
        String url = "./src/main/ui/images/notebook_cover.png";
        ImageIcon image = new ImageIcon(url);
        backgroundLabel = new JLabel(image);

        backgroundLabel.setAlignmentX(0.5f);
        backgroundLabel.setAlignmentY(0.5f);
    }

    //MODIFIES: this
    //EFFECTS: sets placements for start menu components
    private void startComponents() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(null);
        buttonPanel.add(newAccountBtn);
        newAccountBtn.setBounds(50,300,400,50);
        buttonPanel.add(loadAccountBtn);
        loadAccountBtn.setBounds(50,350,400,50);
        buttonPanel.add(title);
        title.setFont(titleFont);
        title.setBounds(100, 60, 500,200);
    }
}
