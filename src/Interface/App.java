package Interface;

import Logic.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class App {
    public static JFrame mainFrame;
    public static Library library;
    public static App app;
    public static JPanel libraryFilesPanel;
    public static JPanel libraryFoundFilesPanel;
    public static ArrayList<File> filesOfOccurrenceList;
    public static JPanel mainContent;
    public static JButton deleteFile;
    public static JTextField input;
    public static AVLTree avlTree;
    public static FileManager fileManager;
    public boolean onBiblioSection = true;

    public static void main(String[] args) throws IOException {
        avlTree = new AVLTree();
        library = new Library();
        fileManager = new FileManager();
        filesOfOccurrenceList = new ArrayList<>();
        app = new App();

        for (File file : library.fileList) {
            library.indexer.indexFiles(file);
        }

    }

    public App() throws IOException {
        mainFrame = new JFrame("Text Finder");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);

        createHeaderPanel();
        createMainContentPanel();


        mainFrame.setVisible(true);




    }

    public void createHeaderPanel() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(1200,125));
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

        mainOptions.add(biblioOption);



        mainOptions.add(findOption);

        JPanel secondaryOptions = new JPanel();
        secondaryOptions.setLayout(new BoxLayout(secondaryOptions, BoxLayout.X_AXIS));
        secondaryOptions.setPreferredSize(new Dimension(1200,50));
        secondaryOptions.setBackground(Color.decode("#bababa"));
        header.add(secondaryOptions, BorderLayout.CENTER);

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


        JPanel sortOptions = new JPanel();
        sortOptions.setLayout(new BoxLayout(sortOptions, BoxLayout.X_AXIS));
        sortOptions.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sortOptions.setPreferredSize(new Dimension(1200,25));
        sortOptions.setBackground(Color.decode("#bababa"));
        JLabel sortLabel = new JLabel("Ordenar por: ");
        sortLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        JButton sortByName = new JButton("Nombre");
        buttonStyle(sortByName, "#bababa");
        mouseHoverEvents(sortByName, "#bababa", "#dadada");
        sortByName.addActionListener(e -> {
            selectedRowFile.clear();
            deleteFile.setEnabled(false);
            QuickSort quickSort = new QuickSort();
            if (onBiblioSection) {
                try {
                    quickSort.sort(library.fileList);
                    listFilesOnScreen();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    quickSort.sort(filesOfOccurrenceList);
                    listFoundFilesOnScreen(input);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton sortByDate = new JButton("Fecha de modificacion");
        buttonStyle(sortByDate, "#bababa");
        mouseHoverEvents(sortByDate, "#bababa", "#dadada");
        sortByDate.addActionListener(e -> {
            selectedRowFile.clear();
            deleteFile.setEnabled(false);
            BubbleSort bubbleSort = new BubbleSort();
            if (onBiblioSection) {
                try {
                    bubbleSort.sortByDate(library.fileList);
                    listFilesOnScreen();
                } catch (ParseException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    bubbleSort.sortByDate(filesOfOccurrenceList);
                    listFoundFilesOnScreen(input);
                } catch (ParseException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton sortBySize = new JButton("Tamaño");
        buttonStyle(sortBySize, "#bababa");
        mouseHoverEvents(sortBySize, "#bababa", "#dadada");
        sortBySize.addActionListener(e -> {
            selectedRowFile.clear();
            deleteFile.setEnabled(false);
            RadixSort radixSort = new RadixSort();
            if (onBiblioSection) {
                try {
                    radixSort.sort(library.fileList);
                    listFilesOnScreen();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    radixSort.sort(filesOfOccurrenceList);
                    listFoundFilesOnScreen(input);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        sortOptions.add(sortLabel);
        sortOptions.add(sortByName);
        sortOptions.add(sortByDate);
        sortOptions.add(sortBySize);

        header.add(sortOptions, BorderLayout.SOUTH);

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
                    onBiblioSection = true;
                } else if (jButton == mainButtons[1]) {
                    secondaryOptionsPanel[0].setVisible(false);
                    secondaryOptionsPanel[1].setVisible(true);
                    cl.show(mainContent, "Panel 2");
                    onBiblioSection = false;
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
            library.deleteFromLibrary(selectedRowFile);
            for (JPanel rowToDelete : selectedRowJPanel) {
                rowToDelete.setVisible(false);
            }
            deleteFile.setEnabled(false);
            selectedRowJPanel.clear();
            selectedRowFile.clear();
        });
        secondaryOptionsPanels[0].add(deleteFile);


        JButton refresh = new JButton("Refrescar");
        buttonStyle(refresh, "#bababa");
        mouseHoverEvents(refresh, "#bababa", "#dadada");
        refresh.addActionListener(e -> {
            for (File file :library.fileList) {
                library.indexer.indexFiles(file);
            }
        });
        secondaryOptionsPanels[0].add(refresh);

    }

    private void findOptions(JPanel[] secondaryOptionsPanels, JButton[] mainButtons) {
        JLabel enterTextLabel = new JLabel("Ingrese el texto: ");
        enterTextLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        enterTextLabel.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 0, Color.decode("#bababa")));
        secondaryOptionsPanels[1].add(enterTextLabel);

        input = new JTextField();
        input.setFont(new Font("Arial", Font.PLAIN, 20));
        input.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 0, Color.decode("#bababa")));
        secondaryOptionsPanels[1].add(input);

        JButton findWord = new JButton("Buscar palabras");
        findWord.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 12, Color.decode("#bababa")));
        buttonStyle(findWord,"#aeaeae");
        mouseHoverEvents(findWord, "#aeaeae", "#cacaca");
        findWord.addActionListener(e -> {
            try {
                listFoundFilesOnScreen(input);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
        mainContent.setPreferredSize(new Dimension(1200,675));
        mainContent.setBackground(Color.decode("#111111"));

        showLibraryFiles(mainContent);
        showFoundFiles(mainContent);

        mainFrame.add(mainContent, BorderLayout.CENTER);

    }

    private void showLibraryFiles(JPanel mainContent) throws IOException {
        libraryFilesPanel = new JPanel();
        libraryFilesPanel.setLayout(new BoxLayout(libraryFilesPanel, BoxLayout.Y_AXIS));
        libraryFilesPanel.setAlignmentY(JButton.TOP);
        libraryFilesPanel.setBackground(Color.decode("#eeeeee"));


        JScrollPane scrollPane1 = scrollPaneConfig(libraryFilesPanel);

        mainContent.add(scrollPane1, "Panel 1");

        listFilesOnScreen();

    }

    private void showFoundFiles(JPanel mainContent){
        libraryFoundFilesPanel = new JPanel();

        libraryFoundFilesPanel.setLayout(new BoxLayout(libraryFoundFilesPanel, BoxLayout.Y_AXIS));
        libraryFoundFilesPanel.setAlignmentY(JButton.TOP);
        libraryFoundFilesPanel.setBackground(Color.decode("#eeeeee"));

        JScrollPane scrollPane2 = scrollPaneConfig(libraryFoundFilesPanel);


        mainContent.add(scrollPane2, "Panel 2");

    }

    private void listFoundFilesOnScreen(JTextField input) throws IOException {
        libraryFoundFilesPanel.removeAll();
        libraryFoundFilesPanel.revalidate();
        libraryFoundFilesPanel.repaint();
        String text = fileManager.stripAccents(input.getText());
        ArrayList<ArrayList> findWord = avlTree.search(text);
        if (findWord != null) {

            filesOfOccurrenceList = findWord.getFirst();
            ArrayList<Integer> occurrenceNumList = avlTree.search(text).get(1);
            ArrayList<File> noDuplicatesOccurrenceList = new ArrayList(List.of(new HashSet<>(filesOfOccurrenceList).toArray())) ;

            ArrayList<ArrayList<String>> textOnFiles = new ArrayList<>();

            for (Object filePath : noDuplicatesOccurrenceList) {
                File fileToRead = new File(String.valueOf(filePath));
                String fileExtension = library.getFileExtension(fileToRead);
                ArrayList<String> fileText;
                if (Objects.equals(fileExtension, "txt")) {
                    fileText = fileManager.getWordsFromTXT(fileToRead);
                    textOnFiles.add(fileText);
                } else if (Objects.equals(fileExtension, "docx")) {
                    fileText = fileManager.getWordsFromDOCX(fileToRead);
                    textOnFiles.add(fileText);

                } else if (Objects.equals(fileExtension, "pdf")) {
                    fileText = fileManager.getWordsFromPDF(fileToRead);
                    textOnFiles.add(fileText);

                }
            }
            for (int i = 0; i < filesOfOccurrenceList.size(); i++) {
                createFoundFileRow(i, textOnFiles, noDuplicatesOccurrenceList, filesOfOccurrenceList, text, occurrenceNumList);
            }

        } else {
            JLabel notFoundRow = new JLabel("Not Found");

            notFoundRow.setPreferredSize(new Dimension(900, 50));

            Font fileTextFont = new Font("Arial", Font.PLAIN, 20);
            notFoundRow.setFont(fileTextFont);
            libraryFoundFilesPanel.add(notFoundRow);
        }
    }

    private void createFoundFileRow(int i,
                                    ArrayList<ArrayList<String>> textOnFiles,
                                    ArrayList<File> noDuplicatesOccurrenceList,
                                    ArrayList<File> filesOfOccurrenceList,
                                    String text,
                                    ArrayList<Integer> occurrenceNumList
    ) throws IOException {
        JPanel resultRow = new JPanel();
        resultRow.setLayout(new BorderLayout());
        resultRow.setBorder(BorderFactory.createMatteBorder(8, 8,0,8,Color.DARK_GRAY));
        ArrayList<String> wordListOfFile = textOnFiles.get(noDuplicatesOccurrenceList.indexOf(filesOfOccurrenceList.get(i)));
        int wordIndexInFile = findNthOccurrence(wordListOfFile, text, occurrenceNumList.get(i));
        ArrayList<String> textToPrint = getTextToPrint(wordIndexInFile, wordListOfFile, i);

        if (wordIndexInFile != -1) {
            JTextPane textPane = new JTextPane();
            textPane.setPreferredSize(new Dimension(1150,35));
            rowToOpenMouseListener(resultRow, filesOfOccurrenceList.get(i), textPane, text, wordIndexInFile);
            textPane.setFocusable(false);
            textPane.setEditable(false);

            StyledDocument doc = textPane.getStyledDocument();

            // Define regular style
            Style regularStyle = textPane.addStyle("RegularStyle", null);
            StyleConstants.setFontSize(regularStyle, 20);
            StyleConstants.setFontFamily(regularStyle,"Arial");
            StyleConstants.setForeground(regularStyle, Color.BLACK);

            // Define highlighted style
            Style highlightedStyle = textPane.addStyle("HighlightedStyle", null);
            StyleConstants.setFontSize(highlightedStyle, 20);
            StyleConstants.setFontFamily(highlightedStyle,"Arial");
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

            JPanel fileInfo = new JPanel();
            fileInfo.setLayout(new BorderLayout());

            JLabel fileName = new JLabel(filesOfOccurrenceList.get(i).getName());
            fileName.setPreferredSize(new Dimension(400,40));
            fileInfo.add(fileName , BorderLayout.WEST);

            JLabel fileLastModified = new JLabel(library.getFileDate(filesOfOccurrenceList.get(i)));
            fileName.setPreferredSize(new Dimension(400,40));
            fileInfo.add(fileLastModified , BorderLayout.CENTER);

            JLabel fileSize = new JLabel(library.getFileSize(filesOfOccurrenceList.get(i)) + " KB");
            fileName.setPreferredSize(new Dimension(350,40));
            fileInfo.add(fileSize , BorderLayout.EAST);

            Font fileTextFont = new Font("Arial", Font.PLAIN, 20);
            fileName.setFont(fileTextFont);
            fileLastModified.setFont(fileTextFont);
            fileSize.setFont(fileTextFont);

            resultRow.add(fileInfo, BorderLayout.NORTH);
            resultRow.add(textPane, BorderLayout.SOUTH);
        }
        libraryFoundFilesPanel.add(resultRow);
    }

    private ArrayList<String> getTextToPrint(int wordIndexInFile, ArrayList<String> wordListOfFile, int i) {
        ArrayList<String> textToPrint = new ArrayList<>();
        int wordNum = wordListOfFile.size();

        if (wordIndexInFile > 12 && wordNum - wordIndexInFile > 10) {
            for (int j = 0; j < 10; j++) {

                int currentIndex = wordIndexInFile - 3 + j;
                if (currentIndex == wordIndexInFile) {
                    textToPrint.add(wordListOfFile.get(currentIndex) + "###$$$###");
                } else {
                    textToPrint.add(wordListOfFile.get(currentIndex));
                }

                if (j < 9 && !isPunctuationMark(wordListOfFile.get(currentIndex + 1))) {
                    textToPrint.add(" ");
                }
            }

        } else if (wordNum > 10) {

            if (wordIndexInFile > wordNum / 2) {

                for (int j = wordNum / 2; j < wordNum; j++) {

                    if (j == wordIndexInFile) {
                        textToPrint.add(wordListOfFile.get(j) + "###$$$###");
                    } else {
                        textToPrint.add(wordListOfFile.get(j));
                    }

                    if (j < wordNum - 1 && !isPunctuationMark(wordListOfFile.get(j + 1))) {
                        textToPrint.add(" ");
                    }
                }
            } else {
                for (int j = 0; j < wordNum / 2; j++) {

                    if (j == wordIndexInFile) {
                        textToPrint.add(wordListOfFile.get(j) + "###$$$###");
                    } else {
                        textToPrint.add(wordListOfFile.get(j));
                    }

                    if (j < (wordNum / 2) - 1 && !isPunctuationMark(wordListOfFile.get(j + 1))) {
                        textToPrint.add(" ");
                    }
                }
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
            }
        });

    }

    private void rowToOpenMouseListener(JPanel row, File file, JTextPane textPane, String text, int wordIndexInFile) {

        // Mouse listener for the JTextPane
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                mainFrame.setEnabled(false);
                JFrame viewDocFrame = new JFrame();
                viewDocFrame.setSize(800,600);
                viewDocFrame.setLayout(new BorderLayout());
                viewDocFrame.setLocationRelativeTo(null);
                viewDocFrame.setVisible(true);

                String fileExtension = library.getFileExtension(file);
                ArrayList<String> fileText = new ArrayList<>();
                if (Objects.equals(fileExtension, "txt")) {
                    fileText = fileManager.getWordsFromTXT(file);
                } else if (Objects.equals(fileExtension, "docx")) {
                    fileText = fileManager.getWordsFromDOCX(file);
                } else if (Objects.equals(fileExtension, "pdf")) {
                    fileText = fileManager.getWordsFromPDF(file);
                }


                JTextPane jTextPane = new JTextPane();
                jTextPane.setPreferredSize(new Dimension(780, 580));
                jTextPane.setEditable(false);
                JScrollPane jScrollPane = new JScrollPane(jTextPane);
                StyledDocument doc = jTextPane.getStyledDocument();

                // Define regular style
                Style regularStyle = jTextPane.addStyle("RegularStyle", null);
                StyleConstants.setFontSize(regularStyle, 20);
                StyleConstants.setFontFamily(regularStyle,"Arial");
                StyleConstants.setForeground(regularStyle, Color.BLACK);

                // Define highlighted style
                Style highlightedStyle = jTextPane.addStyle("HighlightedStyle", null);
                StyleConstants.setFontSize(highlightedStyle, 20);
                StyleConstants.setFontFamily(highlightedStyle,"Arial");
                StyleConstants.setForeground(highlightedStyle, Color.RED);


                int wordStartPosition = 0;

                try {
                    for (int i = 0; i < fileText.size(); i++) {
                        String word = fileText.get(i);
                        Style style = Objects.equals(word, text) ? highlightedStyle : regularStyle;

                        if (i == wordIndexInFile) {
                            wordStartPosition = doc.getLength();
                        }


                        doc.insertString(doc.getLength(), word, style);

                        // Add a space if the next element is not a punctuation mark or we are not at the last element
                        if (i < fileText.size() - 1 && !isPunctuationMark(fileText.get(i + 1))) {
                            doc.insertString(doc.getLength(), " ", style);
                        }
                    }

                    final int position = wordStartPosition;
                    SwingUtilities.invokeLater(() -> {
                        jTextPane.setCaretPosition(position);
                        try {
                            jTextPane.scrollRectToVisible(jTextPane.modelToView(position));
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                viewDocFrame.add(jScrollPane);
                viewDocFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        mainFrame.setEnabled(true);
                    }
                });
            }
        };
        textPane.addMouseListener(mouseAdapter);

        row.addMouseListener(mouseAdapter);

    }
    private boolean isPunctuationMark(String str) {
        return str.matches("\\p{Punct}");
    }



}

