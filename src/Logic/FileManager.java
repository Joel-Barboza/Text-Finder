package Logic;

import javax.swing.*;
import java.io.File;

public class FileManager {
    public FileManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));
        //jFileChooser.showSaveDialog(null);
        jFileChooser.setDialogTitle("choosertitle");
        jFileChooser.setMultiSelectionEnabled(true);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        //int text = jFileChooser.showOpenDialog(null);
        int showOpenDialog = jFileChooser.showOpenDialog(null);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File[] uploadDir = jFileChooser.getSelectedFiles();
            for (File f : uploadDir) {
                System.out.println(f.getAbsolutePath());

            }
            //return null;
        }
    }
}
