package fr.cyu.depinfo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    public GUI() {
        super("teest");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JButton b = new JButton("fdadas");
        b.addActionListener(new JFC());
        Container contentPane = this.getContentPane();
        contentPane.add(b);

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private class JFC implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.showOpenDialog(GUI.this);
        }
    }
    public static void main(String[] args) {
        new GUI();
    }
}
