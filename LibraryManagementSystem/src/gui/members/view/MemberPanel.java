package gui.members.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;

import app.Library;
import gui.books.book.BookTableModel;
import gui.members.member.MemberTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class MemberPanel extends JPanel {
    private Library library;
    private JButton adding;
    private MemberTableModel modelForMembers;
    private BookTableModel modelForBook;
    private JTable table;
    private String[] labels = { "Név", "Születés éve", "Telefonszám" };

    public MemberPanel(Library library, MemberTableModel memberModel, BookTableModel bookModel) {
        super(new BorderLayout());
        this.library = library;
        modelForMembers = memberModel;
        modelForBook = bookModel;
        initComponents();
    }

    private void initComponents() {
        Border border = BorderFactory.createTitledBorder("Könyvtár tagok");
        setBorder(border);
        JMenuBar menubar = new JMenuBar();
        adding = new JButton("Új Tag hozzáadása");
        adding.setBackground(Color.white);
        menubar.add(adding);
        add(menubar, BorderLayout.NORTH);

        table = new JTable(modelForMembers);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        addingFunction();
        removingAndModifyingFunction();
    }

    private JDialog createErrorMessage(JFrame parnet, String label) {
        JDialog dialog = new JDialog(parnet, "Sikertelen művelet!");
        dialog.add(new JLabel(label));
        dialog.setSize(400, 75);
        return dialog;
    }

    private void addingFunction() {
        adding.addActionListener(event -> {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel(new GridLayout(4, 2));
            TextField[] fields = new TextField[3];

            for (int i = 0; i < labels.length; i++) {
                panel.add(new JLabel(labels[i]));
                fields[i] = new TextField(20);
                panel.add(fields[i]);
            }

            JButton submit = new JButton("Hozzáad");
            submit.addActionListener(innerevent -> {
                if (library.addMember(fields[0].getText(), fields[1].getText(), fields[2].getText())) {
                    modelForMembers.refresh();
                    frame.setVisible(false);
                } else {
                    JDialog failed = createErrorMessage(frame,
                            "Már létezik ilyen telefonszámú rekord vagy helytelen a formátum!");
                    failed.setVisible(true);
                }
            });

            panel.add(submit);
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

        });
    }

    private void removingAndModifyingFunction() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel(new GridLayout(4, 2));

                TextField[] fields = new TextField[3];

                for (int i = 0; i < labels.length; i++) {
                    panel.add(new JLabel(labels[i]));
                    fields[i] = new TextField(String.valueOf(table.getValueAt(selectedRow, i)), 20);
                    panel.add(fields[i]);
                }

                JButton modify = new JButton("Módosítás");
                modify.addActionListener(innerEvent0 -> {
                    if (library.modifyMember(selectedRow, fields[0].getText(), fields[1].getText(),
                            fields[2].getText())) {
                        modelForBook.refresh();
                        modelForMembers.refresh();
                        frame.setVisible(false);
                    } else {
                        JDialog error = createErrorMessage(frame, "Sikertelen művelet!");
                        error.setVisible(true);
                    }
                });

                JButton remove = new JButton("Törlés");
                remove.addActionListener(innerEvent1 -> {
                    if (library.removeMember(selectedRow)) {
                        modelForMembers.refresh();
                        modelForBook.refresh();
                        frame.setVisible(false);
                    } else {
                        JDialog error = createErrorMessage(frame, "Ezt a rekordot már törölték!");
                        error.setVisible(true);
                    }
                });
                panel.add(modify);
                panel.add(remove);
                frame.add(panel);
                frame.pack();
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                table.clearSelection();
            }
        });
    }
}
