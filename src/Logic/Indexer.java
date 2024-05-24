package Logic;

import java.io.*;
import java.util.*;

import static Interface.App.*;

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
         ArrayList<String> wordList =  fileManager.getWordsFromTXT(txtFile);

        for (String word : wordList) {
            avlTree.insert(word, txtFile);

        }

    }

    private void indexDOCX(File docxFile) {
        ArrayList<String> wordList = fileManager.getWordsFromDOCX(docxFile);

        for (String word : wordList) {
            avlTree.insert(word, docxFile);

        }
    }

    private void indexPDF(File pdfFile) {
        ArrayList<String> wordList = fileManager.getWordsFromPDF(pdfFile);

        for (String word : wordList) {
            avlTree.insert(word, pdfFile);

        }
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
