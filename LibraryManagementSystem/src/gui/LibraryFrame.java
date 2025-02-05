package gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import app.Library;
import gui.books.book.BookTableModel;
import gui.books.view.BookPanel;
import gui.members.member.MemberTableModel;
import gui.members.view.MemberPanel;

/**
 * A Program egészét összefogó osztály
 */
public class LibraryFrame extends JFrame {

    private BookPanel panelForBooks;
    private MemberPanel panelForMembers;
    private transient Library library;

    public LibraryFrame() {
        setTitle("Könyvtárkezelő");
        library = new Library();
        library.load();
        MemberTableModel memberModel = new MemberTableModel(library.getMembers());
        BookTableModel bookModel = new BookTableModel(library.getBooks());
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelForBooks = new BookPanel(library, bookModel);
        panelForMembers = new MemberPanel(library, memberModel, bookModel);

        sp.setLeftComponent(panelForMembers);
        sp.setRightComponent(panelForBooks);
        add(sp);
        pack();
        setSize(1200, 400);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                library.save();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
}
