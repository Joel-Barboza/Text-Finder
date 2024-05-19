package Interface;

import Logic.AVLTree;
import Logic.FileManager;
import Logic.Library;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class App {
    private static JFrame mainFrame;
    public static Library library;
    public static App app;
    public static JPanel libraryFilesPanel;
    public static JPanel libraryFoundFilesPanel;
    public static JPanel mainContent;
    public static JButton deleteFile;
    public static JLabel message;
    public static AVLTree avlTree;
    public static FileManager fileManager;
    public static void main(String[] args) throws IOException {
        avlTree = new AVLTree();
        library = new Library();
        fileManager = new FileManager();
        app = new App();

        for (File file : library.fileList) {
            createActionInfoPanel("In progress -- Indexing files " + (library.fileList.indexOf(file) + 1)  + "/" + library.fileList.size() );
            library.indexer.indexFiles(file);
        }
        createActionInfoPanel("Done -- Files on library indexed");


        System.out.println("sesese");

//        avlTree.insert("hóla", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Matrices-Sistemas-Determinantes.pdf"));
//        avlTree.insert("Hola", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Folleto_Induccion,_Sucesiones_y_series.pdf"));
//        avlTree.insert("hóla", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Folleto_Induccion,_Sucesiones_y_series.pdf"));
//        avlTree.insert("pedro", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Folleto_Induccion,_Sucesiones_y_series.pdf"));
//        avlTree.insert("dedo", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Folleto_Induccion,_Sucesiones_y_series.pdf"));
//        avlTree.insert("ajo", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Folleto_Induccion,_Sucesiones_y_series.pdf"));
//        avlTree.insert("22", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Sucesiones_y_series_-_Acuña_y_Calderón.pdf"));
//        avlTree.insert("hóla", new File("C:\\Users\\Usuario\\Desktop\\Universidad\\2024_S1\\CAL\\Folleto_Induccion,_Sucesiones_y_series.pdf"));





    }
    public App() throws IOException {
        mainFrame = new JFrame("Text Finder"); // create new frame
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); // set close operation
        mainFrame.setSize(1200,800); // set frame dimensions
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null); // position frame at the center of the screen

        createHeaderPanel();
        createMainContentPanel();
        message = new JLabel("sisisi");
        mainFrame.add(message, BorderLayout.SOUTH);


        mainFrame.setVisible(true); // shows frame




    }

    public void createHeaderPanel() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(1200,100));
        header.setBackground(Color.decode("#bababa"));

        JPanel mainOptions = new JPanel();
        mainOptions.setLayout(new BoxLayout(mainOptions, BoxLayout.X_AXIS));
        mainOptions.setPreferredSize(new Dimension(1200,50));
        mainOptions.setBackground(Color.decode("#aeaeae"));
        header.add(mainOptions, BorderLayout.NORTH);



        // button to open file manager
        JButton biblioOption = new JButton("Biblioteca");
        buttonStyle(biblioOption, "#aeaeae");

        JButton findOption = new JButton("Buscador");
        buttonStyle(findOption, "#aeaeae");
        JButton[] mainButtons = {biblioOption,findOption};

        // action listener for biblioOption button
        biblioOption.addActionListener(e -> {

        });
        mainOptions.add(biblioOption);



        mainOptions.add(findOption);

        JPanel secondaryOptions = new JPanel();
        secondaryOptions.setLayout(new BoxLayout(secondaryOptions, BoxLayout.X_AXIS));
        secondaryOptions.setPreferredSize(new Dimension(1200,50));
        secondaryOptions.setBackground(Color.decode("#bababa"));
        header.add(secondaryOptions, BorderLayout.SOUTH);

        JPanel secondaryBiblioOptions = new JPanel();
        secondaryBiblioOptions.setLayout(new BoxLayout(secondaryBiblioOptions, BoxLayout.X_AXIS));
        secondaryBiblioOptions.setAlignmentX(JButton.CENTER_ALIGNMENT);
        secondaryBiblioOptions.setPreferredSize(new Dimension(1200,50));
        secondaryBiblioOptions.setBackground(Color.decode("#bababa"));
        secondaryBiblioOptions.setVisible(true);
        secondaryOptions.add(secondaryBiblioOptions);


        JPanel secondaryFindOptions = new JPanel();
        secondaryFindOptions.setLayout(new BoxLayout(secondaryFindOptions, BoxLayout.X_AXIS));
        secondaryFindOptions.setAlignmentX(JButton.CENTER_ALIGNMENT);
        secondaryFindOptions.setPreferredSize(new Dimension(1200,50));
        secondaryFindOptions.setBackground(Color.decode("#bababa"));
        secondaryFindOptions.setVisible(false);
        secondaryOptions.add(secondaryFindOptions);

        JPanel[] secondaryOptionsPanels = {secondaryBiblioOptions, secondaryFindOptions};
        biblioOptions(secondaryOptionsPanels);
        findOptions(secondaryOptionsPanels, mainButtons);
        mouseHoverEvents(biblioOption, "#aeaeae", "#cacaca");
        mainButtonsCLickEvent(biblioOption, mainButtons,secondaryOptionsPanels);
        mouseHoverEvents(findOption,  "#aeaeae", "#cacaca");
        mainButtonsCLickEvent(findOption, mainButtons,secondaryOptionsPanels);


        mainFrame.add(header, BorderLayout.NORTH);
    }

    private void buttonStyle(JButton jButton, String primaryColor) {
        jButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        jButton.setBackground(Color.decode(primaryColor));
        jButton.setMargin(new Insets(12,10,12,10));
        jButton.setFont(new Font("Arial", Font.PLAIN, 25));
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);

    }

    private void mainButtonsCLickEvent(JButton jButton, JButton[] mainButtons, JPanel[] secondaryOptionsPanel){
        jButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (mainContent.getLayout());
                if (jButton == mainButtons[0]){
                    secondaryOptionsPanel[0].setVisible(true);
                    secondaryOptionsPanel[1].setVisible(false);
                    cl.show(mainContent, "Panel 1");
                } else if (jButton == mainButtons[1]) {
                    secondaryOptionsPanel[0].setVisible(false);
                    secondaryOptionsPanel[1].setVisible(true);
                    cl.show(mainContent, "Panel 2");

                } else {
                    System.out.println("pepe");

                }
            }




        });
    }
    private void mouseHoverEvents(JButton jButton, String primaryColor, String secondaryColor){
        jButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setBackground(Color.decode(secondaryColor));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setBackground(Color.decode(primaryColor));
            }
        });
    }

    private void biblioOptions(JPanel[] secondaryOptionsPanels) {

        // add to library button
        JButton addNewFile = new JButton("Agregar");
        buttonStyle(addNewFile, "#bababa");
        mouseHoverEvents(addNewFile, "#bababa", "#dadada");
        addNewFile.addActionListener(e -> {
            try {
                fileManager.getFiles(library);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        secondaryOptionsPanels[0].add(addNewFile);


        deleteFile = new JButton("Eliminar");
        deleteFile.setEnabled(false);
        buttonStyle(deleteFile, "#bababa");
        mouseHoverEvents(deleteFile, "#bababa", "#dadada");
        deleteFile.addActionListener(e -> {
            App.createActionInfoPanel("In progress -- Deleting files from Library (deindexing files)");
            library.deleteFromLibrary(selectedRowFile);
            for (JPanel rowToDelete : selectedRowJPanel) {
                rowToDelete.setVisible(false);
            }
            deleteFile.setEnabled(false);
            selectedRowJPanel.clear();
            selectedRowFile.clear();
            App.createActionInfoPanel("Done -- Files deindexed and deleted from library");
        });
        secondaryOptionsPanels[0].add(deleteFile);


        JButton refresh = new JButton("Refrescar");
        buttonStyle(refresh, "#bababa");
        mouseHoverEvents(refresh, "#bababa", "#dadada");
        refresh.addActionListener(e -> {
            System.out.println("refrescando");
        });
        secondaryOptionsPanels[0].add(refresh);

    }



    private void findOptions(JPanel[] secondaryOptionsPanels, JButton[] mainButtons) {
        JLabel enterTextLabel = new JLabel("Ingrese el texto:");
        enterTextLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        enterTextLabel.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 0, Color.decode("#bababa")));
        secondaryOptionsPanels[1].add(enterTextLabel);

        JTextField input = new JTextField();
        input.setFont(new Font("Arial", Font.PLAIN, 20));
        input.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 0, Color.decode("#bababa")));
        secondaryOptionsPanels[1].add(input);

        JButton findWord = new JButton("Buscar palabras");
        findWord.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 12, Color.decode("#bababa")));
        buttonStyle(findWord,"#aeaeae");
        mouseHoverEvents(findWord, "#aeaeae", "#cacaca");
        findWord.addActionListener(e -> {
            listFoundFilesOnScreen(input);
        });
        secondaryOptionsPanels[1].add(findWord);
        JButton findPhrase = new JButton("Buscar palabras");
        findPhrase.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 12, Color.decode("#bababa")));
        buttonStyle(findPhrase,"#aeaeae");
        mouseHoverEvents(findPhrase, "#aeaeae", "#cacaca");
        findPhrase.addActionListener(e -> {
            System.out.println("buscando frase");
        });
        secondaryOptionsPanels[1].add(findPhrase);
    }


    private void createMainContentPanel() throws IOException {
        mainContent = new JPanel();
        mainContent.setLayout(new CardLayout());
        mainContent.setPreferredSize(new Dimension(1200,700));
        mainContent.setBackground(Color.decode("#111111"));

        showLibraryFiles(mainContent);
        showFoundFiles(mainContent);

//        JPanel mainOptions = new JPanel();
//        mainOptions.setLayout(new BoxLayout(mainOptions, BoxLayout.X_AXIS));
//        mainOptions.setPreferredSize(new Dimension(1200,50));
//        mainOptions.setBackground(Color.decode("#aeaeae"));
//        mainContent.add(mainOptions, BorderLayout.NORTH);





        mainFrame.add(mainContent, BorderLayout.CENTER);

    }
    private void showLibraryFiles(JPanel mainContent) throws IOException {
        libraryFilesPanel = new JPanel();
        libraryFilesPanel.setLayout(new BoxLayout(libraryFilesPanel, BoxLayout.Y_AXIS));
        libraryFilesPanel.setAlignmentY(JButton.TOP);
        //libraryFilesPanel.setPreferredSize(new Dimension(1200, 30000));
        libraryFilesPanel.setBackground(Color.decode("#eeeeee"));
        //mainContent.add(libraryFilesPanel);


        JScrollPane scrollPane1 = scrollPaneConfig(libraryFilesPanel);

        mainContent.add(scrollPane1, "Panel 1");

        listFilesOnScreen();




    }
    private void showFoundFiles(JPanel mainContent){
        libraryFoundFilesPanel = new JPanel();
        libraryFoundFilesPanel.setLayout(new BoxLayout(libraryFoundFilesPanel, BoxLayout.Y_AXIS));
        libraryFoundFilesPanel.setAlignmentY(JButton.TOP);
        //libraryFilesPanel.setPreferredSize(new Dimension(1200, 30000));
        libraryFoundFilesPanel.setBackground(Color.decode("#eeeeee"));
        //libraryFoundFilesPanel.setBackground(Color.decode("#ff000"));

        //libraryFoundFilesPanel.setVisible(false);
        //mainContent.add(libraryFoundFilesPanel);


        JScrollPane scrollPane2 = scrollPaneConfig(libraryFoundFilesPanel);


        mainContent.add(scrollPane2, "Panel 2");


        //listFoundFilesOnScreen();




    }

    private void listFoundFilesOnScreen(JTextField input) {
        libraryFoundFilesPanel.removeAll();
        libraryFoundFilesPanel.revalidate();
        libraryFoundFilesPanel.repaint();
        String text = fileManager.stripAccents(input.getText());
        ArrayList<ArrayList> findWord = avlTree.search(text);
        if (findWord != null) {

            ArrayList filesOfOccurrenceList = findWord.getFirst();
            System.out.println(filesOfOccurrenceList);
            ArrayList occurrenceNumList = avlTree.search(text).get(1);
            ArrayList noDuplicatesOccurrenceList = new ArrayList(List.of(new HashSet<>(filesOfOccurrenceList).toArray())) ;

            ArrayList<ArrayList<String>> textOnFiles = new ArrayList<>();

            for (Object filePath : noDuplicatesOccurrenceList) {
                File fileToRead = new File(String.valueOf(filePath));
                String fileExtension = library.getFileExtension(fileToRead);
                ArrayList<String> fileText;
                if (Objects.equals(fileExtension, "txt")) {
                    fileText = fileManager.openTXT(fileToRead);
                    textOnFiles.add(fileText);
                } else if (Objects.equals(fileExtension, "docx")) {
                    fileText = fileManager.openDOCX(fileToRead);
                    textOnFiles.add(fileText);

                } else if (Objects.equals(fileExtension, "pdf")) {
                    fileText = fileManager.openPDF(fileToRead);
                    textOnFiles.add(fileText);

                }
            }
            for (int i = 0; i < filesOfOccurrenceList.size(); i++) {



                JPanel resultRow = new JPanel();
                ArrayList<String> wordListOfFile = textOnFiles.get(noDuplicatesOccurrenceList.indexOf(filesOfOccurrenceList.get(i)));
                int wordIndexInFile = findNthOccurrence(wordListOfFile, text, (Integer) occurrenceNumList.get(i));
                ArrayList<String> textToPrint = getTextToPrint(wordIndexInFile, wordListOfFile, i);

                if (wordIndexInFile != -1) {
                    JTextPane textPane = new JTextPane();
                    textPane.setEditable(false); // Make the text pane read-only

                    StyledDocument doc = textPane.getStyledDocument();

                    // Define regular style
                    Style regularStyle = textPane.addStyle("RegularStyle", null);
                    StyleConstants.setFontSize(regularStyle, 16);
                    StyleConstants.setForeground(regularStyle, Color.BLACK);

                    // Define highlighted style
                    Style highlightedStyle = textPane.addStyle("HighlightedStyle", null);
                    StyleConstants.setFontSize(highlightedStyle, 16);
                    StyleConstants.setForeground(highlightedStyle, Color.RED);


                    try {
                        for (String word : textToPrint) {
                            if (word.contains("###$$$###")) {
                                String removeMark = word.replace("###$$$###", "");
                                doc.insertString(doc.getLength(), removeMark, highlightedStyle);
                            } else {
                                doc.insertString(doc.getLength(), word, regularStyle);
                            }
                        }
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }

                    resultRow.add(textPane);
                }
                libraryFoundFilesPanel.add(resultRow);
            }
        } else {
            JLabel notFoundRow = new JLabel("Not Found");

            notFoundRow.setPreferredSize(new Dimension(900, 50));

            Font fileTextFont = new Font("Arial", Font.PLAIN, 20);
            notFoundRow.setFont(fileTextFont);
            libraryFoundFilesPanel.add(notFoundRow);
        }
    }

    private ArrayList<String> getTextToPrint(int wordIndexInFile, ArrayList<String> wordListOfFile, int i) {
        ArrayList<String> textToPrint = new ArrayList<>();
        if (wordIndexInFile > 12) {
            for (int j = 0; j < 10; j++) {
                if ((wordIndexInFile - 3 + j) == wordIndexInFile) {
                    textToPrint.add(wordListOfFile.get(wordIndexInFile - 3 + j) + "###$$$###");
                } else {
                    textToPrint.add(wordListOfFile.get(wordIndexInFile - 3 + j));
                }
                textToPrint.add(" ");
            }

        } else {

            for (int j = 0; j < wordListOfFile.size(); j++) {
                if (j == wordIndexInFile) {
                    textToPrint.add(wordListOfFile.get(i) + "###$$$###");
                } else {
                    textToPrint.add(wordListOfFile.get(i));
                }
                textToPrint.add(" ");
            }
        }
        return textToPrint;
    }

    private int findNthOccurrence(ArrayList<String> occurrences, String element, int occurrenceToFind) {
        int occurrenceCount = 0;
        for (int i = 0; i < occurrences.size(); i++) {
            if (Objects.equals(occurrences.get(i), element)) {
                ++occurrenceCount;
            }
            if (occurrenceToFind == occurrenceCount) {
                return i;
            }
        }
        return -1;
    }


    private JScrollPane scrollPaneConfig(JPanel childPanel) {
        JScrollPane scrollPane = new JScrollPane(childPanel);
        scrollPane.setBackground(Color.decode("#0B121E"));
        scrollPane.getVerticalScrollBar().setUnitIncrement(13);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setBackground(Color.decode("#1a1a1a"));
        return scrollPane;
    }

    public void listFilesOnScreen() throws IOException {
        libraryFilesPanel.removeAll();
        libraryFilesPanel.revalidate();
        libraryFilesPanel.repaint();
        for (File file: library.fileList){
            JPanel row = new JPanel();
            row.setLayout(new BorderLayout());
            rowMouseListener(row, file);

            JLabel fileName = new JLabel(file.getName());
            fileName.setPreferredSize(new Dimension(400,40));

            JLabel fileDate = new JLabel(library.getFileDate(file));
            fileDate.setPreferredSize(new Dimension(400,40));

            JLabel fileSize = new JLabel(library.getFileSize(file) + " KB");
            fileSize.setPreferredSize(new Dimension(350,40));

            Font fileTextFont = new Font("Arial", Font.PLAIN, 20);
            fileName.setFont(fileTextFont);
            fileDate.setFont(fileTextFont);
            fileSize.setFont(fileTextFont);

            row.add(fileName, BorderLayout.WEST);
            row.add(fileDate, BorderLayout.CENTER);
            row.add(fileSize, BorderLayout.EAST);





            libraryFilesPanel.add(row);
        }
    }

    private ArrayList<File> selectedRowFile = new ArrayList<>();
    private ArrayList<JPanel> selectedRowJPanel = new ArrayList<>();
    private void rowMouseListener(JPanel row, File file) {

        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                row.setBackground(Color.decode("#DDDDDD"));
                selectedRowJPanel.add(row);
                selectedRowFile.add(file);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                deleteFile.setEnabled(true);
//                for (int i = 0; i < selectedRowFile.size(); i++) {
//                    File toDelete = selectedRowFile.get(i);
//                    //toDelete
//                    selectedRowFile.remove(i);
//                    selectedRowJPanel.remove(i);
//
//                }

            }
        });

    }

    public static void createActionInfoPanel(String actionDescription){

        message.setText(actionDescription);
        message.setOpaque(true);
        message.setBackground(Color.decode("#bababa"));
        message.setFont(new Font("Arial", Font.PLAIN, 15));

    }
}

