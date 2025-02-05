package gui.books.book;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import app.Book;

public class BookTableModel extends AbstractTableModel {
    private transient ArrayList<Book> books;
    private String[] columnNames = { "Cím", "Szerző", "Kiadás éve", "Kategória", "Kölcsönző",
            "Kölcsönző telefonszáma" };

    public BookTableModel(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getTitle();
            case 1:
                return book.getAuthor();
            case 2:
                return book.getYearOfTheRelease();
            case 3:
                return book.getCategory();
            case 4:
                if (book.getLoaner() == null) {
                    return null;
                } else {
                    return book.getLoaner().getName();
                }
            case 5:
                if (book.getLoaner() == null) {
                    return null;
                } else {
                    return book.getLoaner().getPhoneNumber();
                }
            default:
                return null;
        }
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void refresh() {
        fireTableRowsInserted(0, books.size() - 1);
    }

}
