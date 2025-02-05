package gui.books.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;

import app.BookSearching;
import app.Library;
import app.Searching;
import gui.books.book.BookTableModel;

public class BookPanel extends JPanel {
    private JButton adding;
    private JButton loaning;
    private JButton returnBook;
    private JMenu searchingBy;
    private JMenuItem searchingByTitle;
    private JMenuItem searchingByAuthor;
    private JMenuItem searchingByYearOfTheRelease;
    private JMenuItem searchByPhoneNumber;
    private JMenuItem searchByCategory;
    private JTextField searchingField;
    private JButton submitSearching;
    private JButton resetTable;
    private transient Library library;
    private BookTableModel modelForBooks;
    private JTable table;
    private String[] labels = { "Cím", "Szerző", "Kiadás éve", "Kategória" };

    public BookPanel(Library library, BookTableModel bookModel) {
        this.library = library;
        this.modelForBooks = bookModel;
        initComponents();
    }

    private void initComponents() {
        Border border = BorderFactory.createTitledBorder("Könyvek");
        setBorder(border);
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        adding = new JButton("Új Könyv hozzáadása");
        adding.setBackground(Color.white);
        loaning = new JButton("Könyv Kölcsönzés");
        loaning.setBackground(Color.white);
        returnBook = new JButton("Könyv Visszatétele");
        returnBook.setBackground(Color.white);
        searchingBy = new JMenu("Keresés alapján");
        searchingByTitle = new JMenuItem("Cím");
        searchingByAuthor = new JMenuItem("Szerző");
        searchingByYearOfTheRelease = new JMenuItem("Kiadás éve");
        searchByCategory = new JMenuItem("Kategória");
        searchByPhoneNumber = new JMenuItem("Kölcsönző Telefonszáma");
        searchingField = new JTextField(30);
        searchingField.setText("Keresés");
        searchingField.setForeground(Color.LIGHT_GRAY);
        submitSearching = new JButton("Keresés Indítása");
        submitSearching.setBackground(Color.white);
        resetTable = new JButton("Visszaállítás");
        resetTable.setBackground(Color.white);

        menuBar.add(adding);
        menuBar.add(loaning);
        menuBar.add(returnBook);
        searchingBy.add(searchingByTitle);
        searchingBy.add(searchingByAuthor);
        searchingBy.add(searchingByYearOfTheRelease);
        searchingBy.add(searchByCategory);
        searchingBy.add(searchByPhoneNumber);
        menuBar.add(searchingBy);
        menuBar.add(searchingField);
        menuBar.add(submitSearching);
        menuBar.add(resetTable);
        add(menuBar, BorderLayout.NORTH);

        this.table = new JTable(modelForBooks);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        addingFunction();
        modifyingAndRemovingFunction();
        rentingFunction();
        returnBookFunction();
        searchingFunction();
    }

    private JDialog createErrorMessage(JFrame parnet, String label) {
        JDialog dialog = new JDialog(parnet, "Sikertelen művelet!");
        dialog.add(new JLabel(label));
        dialog.setSize(500, 75);
        dialog.setResizable(false);
        return dialog;
    }

    private void addingFunction() {
        adding.addActionListener(eventAdding -> {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel(new GridLayout(5, 2));

            JTextField[] fields = new JTextField[4];

            for (int i = 0; i < labels.length; i++) {
                panel.add(new JLabel(labels[i]));
                fields[i] = new JTextField(20);
                panel.add(fields[i]);
            }

            JButton submitButton = new JButton("Hozzáad");
            submitButton.addActionListener(innerEvent -> {
                if (library.addBook(fields[0].getText(), fields[1].getText(), fields[2].getText(),
                        fields[3].getText())) {
                    modelForBooks.refresh();
                    frame.setVisible(false);
                } else {
                    JDialog failed = createErrorMessage(frame, "Már létezik ilyen könyv vagy helytelen formátum!");
                    failed.setVisible(true);
                }
            });

            panel.add(submitButton);
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

    }

    private void modifyingAndRemovingFunction() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                JFrame frame = new JFrame("Rekord Módosítása és Törlése");
                JPanel panel = new JPanel(new GridLayout(5, 2));

                JTextField[] fields = new JTextField[4];

                for (int i = 0; i < labels.length; i++) {
                    panel.add(new JLabel(labels[i]));
                    fields[i] = new JTextField(String.valueOf(table.getValueAt(selectedRow, i)), 20);
                    panel.add(fields[i]);
                }

                JButton modifying = new JButton("Módosítás");
                modifying.addActionListener(eventForModifying -> {
                    if (library.modifyBook(selectedRow, fields[0].getText(), fields[1].getText(), fields[2].getText(),
                            fields[3].getText())) {
                        modelForBooks.refresh();
                        frame.setVisible(false);
                    } else {
                        JDialog failed = createErrorMessage(frame, "Sikertelen módosítás!");
                        failed.setVisible(true);
                    }
                });
                panel.add(modifying);

                JButton removing = new JButton("Törlés");
                removing.addActionListener(eventForRemoving -> {
                    if (library.removeBook(selectedRow)) {
                        modelForBooks.refresh();
                        frame.setVisible(false);
                    } else {
                        JDialog failed = createErrorMessage(frame, "Sikertelen törlés!");
                        failed.setVisible(true);
                    }
                });
                panel.add(removing);

                frame.add(panel);
                frame.pack();
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
            table.clearSelection();
        });
    }

    private void rentingFunction() {
        loaning.addActionListener(eventForLoaning -> {
            JFrame frame = new JFrame("Könyv Kölcsönzése");
            if (!library.getBooks().isEmpty()) {

                JPanel panel = new JPanel(new GridLayout(6, 2));

                JTextField[] fields = new JTextField[5];

                for (int i = 0; i < 3; i++) {
                    panel.add(new JLabel(labels[i]));
                    fields[i] = new JTextField(20);
                    panel.add(fields[i]);
                }

                panel.add(new JLabel("Kölcsöző neve"));
                fields[3] = new JTextField(20);
                panel.add(fields[3]);

                panel.add(new JLabel("Kölcsönző telefonszáma"));
                fields[4] = new JTextField(20);
                panel.add(fields[4]);

                JButton rent = new JButton("Kölcsönzés");
                rent.addActionListener(innerEventForLoaning -> {
                    if (library.rentBook(fields[0].getText(), fields[1].getText(), fields[2].getText(),
                            fields[3].getText(), fields[4].getText())) {
                        modelForBooks.refresh();
                        frame.setVisible(false);
                    } else {
                        JDialog failed = createErrorMessage(frame,
                                "Ilyen könyv nincs vagy nem könyvtár tag a személy vagy már a könyv ki van kölcsönözve!");
                        failed.setVisible(true);
                    }
                });
                panel.add(rent);

                frame.add(panel);
                frame.pack();
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            } else {
                JDialog failed = createErrorMessage(frame, "Nincs könyv a könyvtárban!");
                failed.setVisible(true);
            }
        });
    }

    private void returnBookFunction() {
        returnBook.addActionListener(event -> {
            JFrame frame = new JFrame("Könyv Kölcsönzése");
            if (!library.getBooks().isEmpty()) {

                JPanel panel = new JPanel(new GridLayout(4, 2));

                JTextField[] fields = new JTextField[4];

                for (int i = 0; i < 3; i++) {
                    panel.add(new JLabel(labels[i]));
                    fields[i] = new JTextField(20);
                    panel.add(fields[i]);
                }

                JButton submit = new JButton("Visszatétel");
                submit.addActionListener(innerEvent -> {
                    if (library.returnBook(fields[0].getText(), fields[1].getText(), fields[2].getText())) {
                        modelForBooks.refresh();
                        frame.setVisible(false);
                    } else {
                        JDialog failed = createErrorMessage(frame, "Nincs ilyen könyv vagy nincs kikölcsönözve!");
                        failed.setVisible(true);
                    }
                });
                panel.add(submit);

                frame.add(panel);
                frame.pack();
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            } else {
                JDialog failed = createErrorMessage(frame, "Nincs könyv a könyvtárban!");
                failed.setVisible(true);
            }
        });

    }

    private void searchingFunction() {

        searchingByTitle.addActionListener(eventForTitle -> searchingBy.setText(searchingByTitle.getText()));

        searchingByAuthor.addActionListener(eventForAuthor -> searchingBy.setText(searchingByAuthor.getText()));

        searchingByYearOfTheRelease.addActionListener(
                eventForYearOfTheRelease -> searchingBy.setText(searchingByYearOfTheRelease.getText()));

        searchByPhoneNumber.addActionListener(eventForId -> searchingBy.setText(searchByPhoneNumber.getText()));

        searchByCategory.addActionListener(eventForCategory -> searchingBy.setText(searchByCategory.getText()));

        BookSearching.books = library.getBooks();
        HashMap<String, Searching> commands = new HashMap<>();
        commands.put("Cím", BookSearching::searchByTitle);
        commands.put("Szerző", BookSearching::searchByAuthor);
        commands.put("Kiadás éve", BookSearching::searchByYearOfTheRelease);
        commands.put("Kölcsönző Telefonszáma", BookSearching::searchByLoaner);
        commands.put("Kategória", BookSearching::searchByCategory);

        submitSearching.addActionListener(eventForSubmit -> {

            if (!library.getBooks().isEmpty() && !searchingField.getText().equals("")
                    && !searchingBy.getText().equals("Keresés alapján")) {
                modelForBooks.setBooks(commands.get(searchingBy.getText()).execute(searchingField.getText()));
                modelForBooks.refresh();
            } else {
                JFrame frame = new JFrame();
                JDialog failed = createErrorMessage(frame,
                        "Nincs könyv a könyvtárban vagy nem adta meg a megkeresési feltételeket helyesen!");
                failed.setVisible(true);
            }
        });

        resetTable.addActionListener(eventForResetTable -> {
            if (!library.getBooks().isEmpty()) {
                searchingField.setText(null);
                searchingBy.setText("Keresés alapján");
                searchingField.setText("Keresés");
                modelForBooks.setBooks(library.getBooks());
                modelForBooks.refresh();
            } else {
                JFrame frame = new JFrame();
                JDialog failed = createErrorMessage(frame, "Nincs könyv a könyvtárban!");
                failed.setVisible(true);
            }

        });
    }
}