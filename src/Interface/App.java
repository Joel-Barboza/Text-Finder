package Interface;

import Logic.AVLTree;
import Logic.FileManager;
import Logic.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class App {
    private static JFrame mainFrame;
    public static Library library;
    public static App app;
    public static JPanel libraryFilesPanel;
    public static JButton deleteFile;
    public static AVLTree avlTree;
    public static void main(String[] args) throws IOException {
        avlTree = new AVLTree();
        library = new Library();
        app = new App();

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
        mainFrame = new JFrame("Reproductor"); // create new frame
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); // set close operation
        mainFrame.setSize(1200,800); // set frame dimensions
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null); // position frame at the center of the screen

        createHeaderPanel();
        createMainContentPanel();


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
//        biblioOption.addActionListener(e -> {
//            FileManager fileManager = new FileManager();
//        });
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
                if (jButton == mainButtons[0]){
                    secondaryOptionsPanel[0].setVisible(true);
                    secondaryOptionsPanel[1].setVisible(false);
                } else if (jButton == mainButtons[1]) {
                    secondaryOptionsPanel[0].setVisible(false);
                    secondaryOptionsPanel[1].setVisible(true);

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
                new FileManager(library);
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

        JButton find = new JButton("Buscar");
        find.setBorder(BorderFactory.createMatteBorder(12, 12, 12, 12, Color.decode("#bababa")));
        buttonStyle(find,"#aeaeae");
        mouseHoverEvents(find, "#aeaeae", "#cacaca");
        find.addActionListener(e -> {
            System.out.println("buscando");
        });
        secondaryOptionsPanels[1].add(find);
    }


    private void createMainContentPanel() throws IOException {
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setPreferredSize(new Dimension(1200,700));
        mainContent.setBackground(Color.decode("#111111"));

        showLibraryFiles(mainContent);

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
        mainContent.add(libraryFilesPanel, BorderLayout.CENTER);

        listFilesOnScreen();

        JScrollPane scrollPane = new JScrollPane(libraryFilesPanel);
        scrollPane.setBackground(Color.decode("#0B121E"));
        scrollPane.getVerticalScrollBar().setUnitIncrement(13);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setBackground(Color.decode("#1a1a1a"));

        mainContent.add(scrollPane, BorderLayout.CENTER);


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
}

