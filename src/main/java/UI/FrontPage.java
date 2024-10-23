package UI;

import Classes.ButtonControl;

import javax.swing.*;
import java.awt.*;

public class FrontPage {
    private JPanel mainPanel;
    private JLabel recentItems;
    private JLabel option;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JList list1;

    public FrontPage(){
        mainPanel = new JPanel (new BorderLayout());

        // Padding labels
        recentItems.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 275));
        option.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Grootte van de buttons
        Dimension buttonSize = new Dimension(150, 30);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        // Panel voor de buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new GridLayout(3,1));
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttonPanel.add(option, BorderLayout.NORTH);
        buttonPanel.add(buttons, BorderLayout.CENTER);

        // Panel voor de recent geopende items
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(recentItems, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(list1), BorderLayout.CENTER);

        // Onderdelen voor de 'mainPanel'
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Initialize ButtonController
        new ButtonControl(button1, button2, button3);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Martijn's Comic Book Reader");
        frame.setContentPane(new FrontPage().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 480));
        frame.pack();
        frame.setVisible(true);
    }
}