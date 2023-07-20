package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//General functionality elements used in GUI
public class Functionality extends JFrame {

    protected Font titleFont = new Font("Baskerville", Font.BOLD, 24);

    //MODIFIES: panel
    //EFFECTS: initializes panel to default settings
    public void panelInit(JPanel panel) {
        panel.setPreferredSize(new Dimension(500, 600));
        panel.setBorder(new EmptyBorder(0,0,0,0));
        panel.setLayout(new BorderLayout());
    }

    //MODIFIES: this
    //EFFECTS: sets content of the window to specified panel
    public void setPanel(JPanel panel) {
        setContentPane(panel);
        invalidate();
        validate();
    }
}
