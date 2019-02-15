package com.presentation;

import javax.swing.*;
import java.awt.*;

public class UI {
    private JFrame frame;
    private int width, height;

    public UI() {
        JPanel contentPane = new JPanel(new GridLayout());
        contentPane.add(new Card(Color.BLACK));
        contentPane.add(new Card(Color.red));
        contentPane.add(new Card(Color.BLUE));


        frame = new JFrame("Home Automation Desktop Client");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        //frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        width = frame.getWidth();
        height = frame.getHeight();
    }
}
