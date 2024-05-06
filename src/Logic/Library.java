package Logic;

import Interface.App;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Library {
    private final String FILE_PATH = "./src/library.txt";
    public ArrayList<File> fileList;
    private final ArrayList<File> acceptedFiles = new ArrayList<>();
    private final Indexer indexer = new Indexer();

    public Library(){
        loadFromLibraryFile();

    }

    public String getFileExtension(File file) {
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        return extension;
    }
    private void chosenFileExtensionOnly(ArrayList<File> files){

        for (File file : files){
            String fileExtension = getFileExtension(file);
            if (file.isDirectory()) {
                File[] filesInDir = file.listFiles();
                assert filesInDir != null;
                chosenFileExtensionOnly(new ArrayList<>(List.of(filesInDir)));
            } else if (file.isFile() &&
                    (
                            Objects.equals(fileExtension, "txt") ||
                            Objects.equals(fileExtension, "pdf") ||
                            Objects.equals(fileExtension, "docx")
                    )
            ) {
                acceptedFiles.add(file);
            }

        }
    }

    // to extract try/catch block
    public void addToLibrary(ArrayList<File> files) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            add(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void add(ArrayList<File> files) throws IOException {
        chosenFileExtensionOnly(files);
        for (File f : acceptedFiles) {
            if (!isInList(f)) {
                fileList.addFirst(f);
            }
        }
        saveListOnFile();
        indexer.indexFiles(fileList);
    }

    // to extract try/catch block
    private void saveListOnFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            save(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void save(FileWriter writer) throws IOException {
        String data = "";
        for (File file : fileList) {
            data = data + file.getName() + (char) 31 +
                    file.getAbsolutePath() + (char) 31 +
                    getFileDate(file) + (char) 31 +
                    getFileSize(file) +
                    System.lineSeparator();
        }
        writer.write(data);
    }
    public String getFileSize(File file) throws IOException {
        return String.format("%,d", Files.size(Path.of(file.getAbsolutePath())) / 1024).replaceAll("\\u00A0", "");
    }

    public String getFileDate(File file) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String formattedDate = sdf.format(new Date(file.lastModified()));
        return formattedDate.replaceAll("\\u00A0", "");
    }

    private boolean isInList(File f){
        ArrayList<String> fileNames = new ArrayList<>();
        for (File file : fileList) {
            fileNames.add(file.getAbsolutePath());
        }

        return fileNames.contains(f.getAbsolutePath());
    }

    public void loadFromLibraryFile() {
        fileList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Character.toString((char) 31));
                File fileToAdd = new File(parts[1]);
                fileList.add(fileToAdd);

            }
            indexer.indexFiles(fileList);
        AVLTree yes = App.avlTree;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromLibrary(ArrayList<File> fileListToDelete) {
        for (File file : fileListToDelete) {
            if (isInList(file)) {
                fileList.remove(file);
                //System.out.println(fileList);
            }

        }
        saveListOnFile();
        indexer.indexFiles(fileList);
    }

}
