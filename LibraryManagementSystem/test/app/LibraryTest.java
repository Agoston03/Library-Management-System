package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LibraryTest {
    Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testAddBook() {
        assertTrue(library.addBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Regény"));
        assertEquals(1, library.getBooks().size());
    }

    @Test
    public void testAddMember() {
        assertTrue(library.addMember("Kiss Elemér", "1999", "3630123"));
        assertEquals(1, library.getMembers().size());
    }

    @Test
    public void testInvalidAddBook() {
        assertFalse(library.addBook("A kis herceg", "Antoine de Saint-Exupéry2", "1943", "Regény"));
        assertFalse(library.addMember("Kiss Elemér", "asd1999", "3630123"));
        assertEquals(0, library.getBooks().size());
        assertEquals(0, library.getMembers().size());
    }

    @Test
    public void testInvalidAddMember() {
        assertFalse(library.addMember("Kiss Elemér", "asd1999", "3630123"));
        assertEquals(0, library.getMembers().size());
    }

    @Test
    public void testRemoveBook() {
        library.addBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Regény");
        library.removeBook(0);
        assertEquals(0, library.getBooks().size());
    }

    @Test
    public void testRemoveMember() {
        library.addMember("Kiss Elemér", "1999", "3630123");
        library.removeMember(0);
        assertEquals(0, library.getMembers().size());
    }

    @Test
    public void testInvalidRemoveBook() {
        assertFalse(library.removeBook(0));
    }

    @Test
    public void testInvalidRemoveMember() {
        assertFalse(library.removeBook(0));
    }

    @Test
    public void testModifyBook() {
        library.addBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Regény");
        assertTrue(library.modifyBook(0, "Herceg", "hello", "1999", "r"));
        assertEquals("hello", library.getBooks().get(0).getAuthor());
    }

    @Test
    public void testModifyMember() {
        library.addMember("Kiss Elemér", "1999", "3630123");
        assertTrue(library.modifyMember(0, "Kiss", "1999", "3630123"));
        assertEquals("Kiss", library.getMembers().get(0).getName());
    }

    @Test
    public void testLoaningBook() {
        library.addBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Regény");
        library.addMember("Kiss Elemér", "1999", "3630123");
        assertTrue(library.rentBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Kiss Elemér", "3630123"));
        assertEquals(library.getMembers().get(0), library.getBooks().get(0).getLoaner());
    }

    @Test
    public void testReturnBook() {
        library.addBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Regény");
        library.addMember("Kiss Elemér", "1999", "3630123");
        library.rentBook("A kis herceg", "Antoine de Saint-Exupéry", "1943", "Kiss Elemér", "3630123");
        assertTrue(library.returnBook("A kis herceg", "Antoine de Saint-Exupéry", "1943"));
        assertEquals(null, library.getBooks().get(0).getLoaner());
    }

}
