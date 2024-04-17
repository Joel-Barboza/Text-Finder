package Interface;

import Logic.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class App {
    public static void main(String[] args) {
        App app = new App();

    }

    public App() {
        JFrame mainFrame = new JFrame("Reproductor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);



        JButton openFileManager = new JButton("Open File Manager");
        openFileManager.setBounds(100, 100, 200,50);
        openFileManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileManager fileManager = new FileManager();
            }

        });
        mainFrame.add(openFileManager);

    }
}
