package app;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BookSearchingTest {

    @Before
    public void setUp() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("A kis herceg", "Antoine de Saint-Exupéry", 1943, "Regény", null));
        books.add(new Book("Test", "Me", 2023, "None", null));
        BookSearching.books = books;
    }

    @Test
    public void testSearchByTitle() {
        ArrayList<Book> temp = BookSearching.searchByTitle("Tes");
        assertEquals("Me", temp.get(0).getAuthor());
    }

    @Test
    public void testSearchByAuthor() {
        ArrayList<Book> temp = BookSearching.searchByAuthor("An");
        assertEquals("A kis herceg", temp.get(0).getTitle());

    }
}
