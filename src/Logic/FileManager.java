package Logic;

import Interface.App;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private ArrayList<File> files;

    public FileManager(Library library) throws IOException {

        // changes the default swing filechooser appearance to the system one
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        JFileChooser jFileChooser = new JFileChooser(); // create file chooser

        // create and config file filter
        FileNameExtensionFilter textDocFilter = new FileNameExtensionFilter("Todos los documentos de texto (*.txt, *.pdf, *.docx)", "txt", "pdf", "docx");
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Texto sin formato (*.txt)", "txt");
        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF (*.pdf)", "pdf");
        FileNameExtensionFilter docxFilter = new FileNameExtensionFilter("Documento de Word (*.docx)", "docx");

        jFileChooser.addChoosableFileFilter(textDocFilter);
        jFileChooser.addChoosableFileFilter(txtFilter);
        jFileChooser.addChoosableFileFilter(pdfFilter);
        jFileChooser.addChoosableFileFilter(docxFilter);
        jFileChooser.setFileFilter(textDocFilter);


        jFileChooser.setCurrentDirectory(new File(".")); // set current directory

        jFileChooser.setDialogTitle("Seleccionar archivos"); // file chooser window title
        jFileChooser.setMultiSelectionEnabled(true); // enable the selection of multiple files/directories at once
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // allow to select files and directories
        jFileChooser.setAcceptAllFileFilterUsed(false);

        // do something on open button on file chooser window click
        int showOpenDialog = jFileChooser.showOpenDialog(null);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {

            File[] selectedFiles = jFileChooser.getSelectedFiles();
            files = new ArrayList<>(List.of(selectedFiles));
            library.addToLibrary(files);
            //ArrayList<File> listOfFiles = library.loadFromLibraryFile();
            App.app.listFilesOnScreen();
            AVLTree si = App.avlTree;

//            for (File f : listOfFiles) {
//                System.out.println(f.getAbsolutePath());
//
//            }
        }
    }

    public ArrayList<File> getFilePath(){
        return this.files;
    }
}
