package Logic;

import Interface.App;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Indexer {

    public void indexFiles(ArrayList<File> fileList) {
        for (File file : fileList) {
            String fileExtension = App.library.getFileExtension(file);
            if (Objects.equals(fileExtension, "txt")) {
                indexTXT(file);
            } else if (Objects.equals(fileExtension, "docx")) {
                indexDOCX(file);
            } else if (Objects.equals(fileExtension, "pdf")) {
                indexPDF(file);
            }

        }

    }
    private void indexTXT(File txtFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile.getAbsoluteFile()))) {

            ArrayList<String> wordList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[ .,;:]");
                wordList.addAll(List.of(parts));
            }

            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

            for (String word : wordList) {
                String cleanWord = removeUnwantedCharacters(word);
                App.avlTree.insert(cleanWord, txtFile);
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

            ArrayList<String> wordList = new ArrayList<>();

            String[] parts = textFromDoc.split("[ .,;:]");
            wordList.addAll(List.of(parts));

            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

            for (String word : wordList) {
                String cleanWord = removeUnwantedCharacters(word);
                App.avlTree.insert(cleanWord, docxFile);
            }

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    private void indexPDF(File pdfFile) {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println(text);

            ArrayList<String> wordList = new ArrayList<>();

            String[] parts = text.split("[ .,;:]");
            wordList.addAll(List.of(parts));

            wordList.removeAll(Collections.singleton(" "));
            wordList.removeAll(Collections.singleton(""));

            for (String word : wordList) {
                String cleanWord = removeUnwantedCharacters(word);
                App.avlTree.insert(cleanWord, pdfFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String removeUnwantedCharacters(String word) {
        String[] unwantedChar = {"\n","\t", "\r", "\\(", "\\)"};
        String resultWord = word;
        for (String character : unwantedChar) {
            resultWord = resultWord.replaceAll(character, "");
        }
        return resultWord;
    }
}
