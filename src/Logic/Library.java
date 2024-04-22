package Logic;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Library {
    private final String FILE_PATH = "./src/library.txt";
    private final ArrayList<File> fileList = new ArrayList<>();

    public Library(){
        //ArrayList<File> savedFilesInLibrary = loadFromLibraryFile();
    }

    public void addToLibrary(ArrayList<File> files) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {

            for (File f : files) {
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
//            for (File file : fileList) {
//                writer.write(file.getName() + "," +
//                        file.getAbsolutePath() + "," +
//                        sdf.format(file.lastModified()) + "," +
//                        String.format("%,d", Files.size(Path.of(file.getAbsolutePath())) / 1024) +
//                        System.lineSeparator());
//                //System.out.println(sdf.format("%,d", Files.size(Path.of(file.getAbsolutePath())) / 1024));
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isInList(File f){
        ArrayList<String> fileNames = new ArrayList<>();
        for (File file : fileList) {
            fileNames.add(file.getAbsolutePath());
        }
        return fileNames.contains(f.getAbsolutePath());
    }

    public ArrayList<File> loadFromLibraryFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            if (reader.readLine() != null) {

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    fileList.add(new File(parts[1]));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }
}
