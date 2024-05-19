package Logic;

import Interface.App;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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

import static Interface.App.avlTree;

public class FileManager {
    private ArrayList<File> files;


    public void getFiles(Library library) throws IOException {
        JFileChooser jFileChooser = openFileChooser();


        // do something on open button on file chooser window click
        int showOpenDialog = jFileChooser.showOpenDialog(null);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {

            File[] selectedFiles = jFileChooser.getSelectedFiles();
            files = new ArrayList<>(List.of(selectedFiles));
            App.createActionInfoPanel("In progress -- Indexing Files");
            library.addToLibrary(files);
            App.createActionInfoPanel("Done -- Files indexed");
            //ArrayList<File> listOfFiles = library.loadFromLibraryFile();
            App.app.listFilesOnScreen();
            AVLTree si = App.avlTree;

//            for (File f : listOfFiles) {
//                System.out.println(f.getAbsolutePath());
//
//            }
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

    public ArrayList<File> getFilePath(){
        return this.files;
    }

    private String removeUnwantedCharacters(String word) {
        String[] unwantedChar = {"\n","\t", "\r", "\\(", "\\)"};
        String resultWord = word;
        word = Normalizer.normalize(word, Normalizer.Form.NFD);
        word = word.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        for (String character : unwantedChar) {
            resultWord = resultWord.replaceAll(character, "");
        }
        return resultWord;
    }

    public String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        return s;
    }
    public ArrayList<String> openTXT(File file){
        ArrayList<String> wordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {


            String line;
            while ((line = reader.readLine()) != null) {
//                String cleanWord = removeUnwantedCharacters(line);
                String cleanWord = stripAccents(line);
//                String[] parts = cleanWord.split("[ .,;:]");
                String[] parts = cleanWord.split("\\s+|(?<=\\W)|(?=\\W)");
//                String[] parts = cleanWord.split("\\s+|(?=\\b)|(?<=\\b)|(?<=\\W)|(?=\\W)");
//                String[] parts = cleanWord.split("[^\\p{L}0-9']+");
//                String[] parts = cleanWord.split("[^\\p{IsLatin}0-9']+&&[^\\p{Punct}]");
                wordList.addAll(List.of(parts));
            }
//            for (int i = 0; i < wordList.size(); i++) {
//                wordList.set(i, stripAccents(wordList.get(i)));
//            }

//            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(wordList);
        return wordList;
    }

    public ArrayList<String> openDOCX(File file){
        ArrayList<String> wordList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
            String textFromDoc = wordExtractor.getText();
//            String cleanWord = removeUnwantedCharacters(textFromDoc);
            String cleanWord = stripAccents(textFromDoc);

            String[] parts = cleanWord.split("[^\\p{L}0-9']+");
            wordList.addAll(List.of(parts));

//            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordList;
    }

    public ArrayList<String> openPDF(File file){
        ArrayList<String> wordList = new ArrayList<>();
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();

            String text = pdfStripper.getText(document);
//            String cleanWord = removeUnwantedCharacters(text);
            String cleanWord = stripAccents(text);

            String[] parts = cleanWord.split("[^\\p{L}0-9']+");
            wordList.addAll(List.of(parts));

//            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordList;
    }
}
