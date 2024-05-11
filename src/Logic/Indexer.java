package Logic;

import Interface.App;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.*;

import static Interface.App.avlTree;
import static Interface.App.library;

public class Indexer {

    public void indexFiles(File file) {

        String fileExtension = getFileExtension(file);
        if (Objects.equals(fileExtension, "txt")) {
            indexTXT(file);
        } else if (Objects.equals(fileExtension, "docx")) {
            indexDOCX(file);
        } else if (Objects.equals(fileExtension, "pdf")) {
            indexPDF(file);
        }



    }
    public String getFileExtension(File file) {
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        return extension;
    }
    private void indexTXT(File txtFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile.getAbsoluteFile()))) {

            ArrayList<String> wordList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String cleanWord = removeUnwantedCharacters(line);
                String[] parts = cleanWord.split("[ .,;:]");
                wordList.addAll(List.of(parts));
            }

            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

            for (String word : wordList) {
                avlTree.insert(word, txtFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void indexDOCX(File docxFile) {
        try (FileInputStream fis = new FileInputStream(docxFile.getAbsolutePath())) {
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
            String textFromDoc = wordExtractor.getText();
            String cleanWord = removeUnwantedCharacters(textFromDoc);

            ArrayList<String> wordList = new ArrayList<>();

            String[] parts = cleanWord.split("[ .,;:]");
            wordList.addAll(List.of(parts));
            System.out.println(wordList.size());

            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

            for (String word : wordList) {
                avlTree.insert(word, docxFile);
            }

            System.out.println("cleanWord");

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    private void indexPDF(File pdfFile) {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            String cleanWord = removeUnwantedCharacters(text);
            System.out.println(text);

            ArrayList<String> wordList = new ArrayList<>();

            String[] parts = cleanWord.split("[ .,;:]");
            wordList.addAll(List.of(parts));

            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

            for (String word : wordList) {
                avlTree.insert(word, pdfFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private String removeUnwantedCharacters(String word) {
        String[] unwantedChar = {"\n","\t", "\r", "\\(", "\\)"};
        String resultWord = word;
        for (String character : unwantedChar) {
            resultWord = resultWord.replaceAll(character, "");
        }
        return resultWord;
    }



    public void deindexFile(File file, AVLNode root) {
        ArrayList fileOfOccurrence = root.occurrencesList.getFirst();
        ArrayList occurrencePosition =  root.occurrencesList.get(1);
        System.out.println(root.key);
        System.out.println(fileOfOccurrence.getClass().getName());
        System.out.println(occurrencePosition.getClass().getName());


        while (fileOfOccurrence.contains(file)) {

            int index = fileOfOccurrence.indexOf(file);
            System.out.println(index);
            fileOfOccurrence.remove(index);
            occurrencePosition.remove(index);


        }
        if (fileOfOccurrence.isEmpty()) {
            avlTree.delete(root.key);
        }
        library.fileList.remove(file);
    }

}
