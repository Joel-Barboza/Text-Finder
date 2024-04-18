package Interface;

import Logic.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class App {
    private static JFrame mainFrame;
    public static void main(String[] args) {
        App app = new App();

    }

    public App() {
        mainFrame = new JFrame("Reproductor"); // create new frame
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); // set close operation
        mainFrame.setSize(1200,800); // set frame dimensions
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null); // position frame at the center of the screen

        headerJPanel();

        mainFrame.setVisible(true); // shows frame




    }

    public void headerJPanel() {
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

        JPanel secundaryBiblioOptions = new JPanel();
        secundaryBiblioOptions.setLayout(new BoxLayout(secundaryBiblioOptions, BoxLayout.X_AXIS));
        secundaryBiblioOptions.setAlignmentX(JButton.CENTER_ALIGNMENT);
        secundaryBiblioOptions.setPreferredSize(new Dimension(1200,50));
        secundaryBiblioOptions.setBackground(Color.decode("#bababa"));
        secundaryBiblioOptions.setVisible(false);
        secondaryOptions.add(secundaryBiblioOptions);


        JPanel secundaryFindOptions = new JPanel();
        secundaryFindOptions.setLayout(new BoxLayout(secundaryFindOptions, BoxLayout.X_AXIS));
        secundaryFindOptions.setAlignmentX(JButton.CENTER_ALIGNMENT);
        secundaryFindOptions.setPreferredSize(new Dimension(1200,50));
        secundaryFindOptions.setBackground(Color.decode("#bababa"));
        secundaryFindOptions.setVisible(false);
        secondaryOptions.add(secundaryFindOptions);

        JPanel[] secondaryOptionsPanels = {secundaryBiblioOptions, secundaryFindOptions};
        biblioOptions(secondaryOptionsPanels, mainButtons);
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

    private void biblioOptions(JPanel[] secondaryOptionsPanels, JButton[] mainButtons) {

        // add to library button
        JButton addNewFile = new JButton("Agregar");
        buttonStyle(addNewFile, "#bababa");
        mouseHoverEvents(addNewFile, "#bababa", "#dadada");
        addNewFile.addActionListener(e -> {
            //FileManager fileManager = new FileManager();
            System.out.println("agregando");
        });
        secondaryOptionsPanels[0].add(addNewFile);


        JButton deleteFile = new JButton("Eliminar");
        buttonStyle(deleteFile, "#bababa");
        mouseHoverEvents(deleteFile, "#bababa", "#dadada");
        deleteFile.addActionListener(e -> {
            System.out.println("eliminando");
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
}


//        String biblioOptions[] = {"Ver biblioteca", "Editar biblioteca", "Noida", "Kolkata", "New Delhi"};
//        JComboBox biblio = new JComboBox(biblioOptions);
//        biblio.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String selectedOption = (String) biblio.getItemAt(biblio.getSelectedIndex());
//                if (Objects.equals(selectedOption, "Ver biblioteca")){
//                    System.out.println("ver");
//
//                } else if (Objects.equals(selectedOption, "Editar biblioteca")){
//                    System.out.println("editar");
//
//                } else {
//                    System.out.println("si");
//                }
//            }
//        });
//
//        biblio.setBounds(200, 100, 200,50);
//        header.add(biblio);