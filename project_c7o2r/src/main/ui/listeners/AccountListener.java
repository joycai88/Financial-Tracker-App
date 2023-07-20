package ui.listeners;

import ui.TrackerAppGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//ActionListener for starting screen
public class AccountListener implements ActionListener {

    private TrackerAppGUI trackerAppGUI;

    @Override
    public void actionPerformed(ActionEvent e) {
        trackerAppGUI = new TrackerAppGUI();
        if (e.getActionCommand().equals("newAccount")) {
            trackerAppGUI.newAccount();
        } else if (e.getActionCommand().equals("loadAccount")) {
            trackerAppGUI.loadAccount();
        }
    }
}
