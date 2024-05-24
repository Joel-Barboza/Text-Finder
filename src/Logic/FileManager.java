package Logic;

import Interface.App;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager {
    private ArrayList<File> files;


    public void getFiles(Library library) throws IOException {
        JFileChooser jFileChooser = openFileChooser();


        // do something on open button on file chooser window click
        int showOpenDialog = jFileChooser.showOpenDialog(null);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {

            File[] selectedFiles = jFileChooser.getSelectedFiles();
            files = new ArrayList<>(List.of(selectedFiles));
            library.addToLibrary(files);
            App.app.listFilesOnScreen();

        }
    }

    private JFileChooser openFileChooser() {
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
        return jFileChooser;
    }


    public String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        return s;
    }
    public String getTextFromTXT(File file){
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {


            String line;
            while ((line = reader.readLine()) != null) {
                if (text.isEmpty()) {
                    text.append(line);
                } else {
                    text.append("\n").append(line);

                }
            }
//

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
    public ArrayList<String> getWordsFromTXT(File file){

        String cleanWord = stripAccents(getTextFromTXT(file));
        String[] parts = cleanWord.split("\\s+|(?<=\\W)|(?=\\W)");
        ArrayList<String> wordList = new ArrayList<>(List.of(parts));

        wordList.removeAll(Collections.singleton(""));

        return wordList;
    }


    public String getTextFromDOCX(File file){
        String text = null;
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
            text = wordExtractor.getText();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
    public ArrayList<String> getWordsFromDOCX(File file){

        String cleanWord = stripAccents(getTextFromDOCX(file));

        String[] parts = cleanWord.split("\\s+|(?<=\\W)|(?=\\W)");
        ArrayList<String> wordList = new ArrayList<>(List.of(parts));

        wordList.removeAll(Collections.singleton(""));


        return wordList;
    }

    public String getTextFromPDF(File file) {
        String text = null;
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();

            text = pdfStripper.getText(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
    public ArrayList<String> getWordsFromPDF(File file){

        String cleanWord = stripAccents(getTextFromPDF(file));

        String[] parts = cleanWord.split("\\s+|(?<=\\W)|(?=\\W)");
        ArrayList<String> wordList = new ArrayList<>(List.of(parts));

        wordList.removeAll(Collections.singleton(""));


        return wordList;

    }
}
