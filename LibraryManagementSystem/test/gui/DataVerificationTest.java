package gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataVerificationTest {
    @Test
    public void testVerifyBook() {
        DataVerification ver = new DataVerification();
        assertFalse(ver.verifyBook("A kis herceg", "123", "asd"));
        assertFalse(ver.verifyBook("A kis herceg", "123", "1943"));
        assertTrue(ver.verifyBook("A kis herceg", "Me", "1943"));

    }

    @Test
    public void testVerifyMember() {
        DataVerification ver = new DataVerification();
        assertTrue(ver.verifyMember("Asd", "1932", "123"));
        assertFalse(ver.verifyMember("Asd", "1932", "123A"));
    }
}
