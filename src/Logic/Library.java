package Logic;

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

    public Library(){
        //ArrayList<File> savedFilesInLibrary =
        loadFromLibraryFile();
    }

    // to extract try/catch block
    public void addToLibrary(ArrayList<File> files) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            add(files, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(File file) {
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

    private void add(ArrayList<File> files, FileWriter writer) throws IOException {
        chosenFileExtensionOnly(files);
        for (File f : acceptedFiles) {
            if (!isInList(f)) {
                fileList.add(f);
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                String formattedDate = sdf.format(new Date(f.lastModified()));
                String removedString = formattedDate.replaceAll("\\u00A0", ""); // to remove   from text
                writer.write(f.getName() + "," +
                        f.getAbsolutePath() + "," +
                        removedString + "," +
                        String.format("%,d", Files.size(Path.of(f.getAbsolutePath())) / 1024) +
                        System.lineSeparator());
            }
        }
    }

    private boolean isInList(File f){
        ArrayList<String> fileNames = new ArrayList<>();
        for (File file : fileList) {
            fileNames.add(file.getAbsolutePath());
        }
        if (fileNames.contains(f.getAbsolutePath())){
            System.out.println(f.getAbsolutePath() + "in list");
        }
        return fileNames.contains(f.getAbsolutePath());
    }

    public void loadFromLibraryFile() {
        fileList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                fileList.add(new File(parts[1]));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
