package gui;

import java.util.ArrayList;

import app.Book;
import app.LibraryMember;

public class DataVerification {

    public boolean verifyBook(String title, String author, String yearOfTheRelease) {
        if (title.equals("") || yearOfTheRelease.equals("") || author.equals("")) {
            return false;
        }
        return !author.matches(".*\\d+.*") && yearOfTheRelease.matches("\\d*");
    }

    public boolean verifyMember(String name, String birthYear, String phoneNumber) {
        if (name.equals("") || birthYear.equals("") || phoneNumber.equals("")) {
            return false;
        }
        return !name.matches(".*\\d+.*") && birthYear.matches("\\d*") && phoneNumber.matches("\\d*");
    }

    public int libraryContainsBook(String title, String author, String yearOfTheRelease, ArrayList<Book> books) {
        Book temp = new Book(title, author, Integer.parseInt(yearOfTheRelease));
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equals(temp)) {
                return i;
            }
        }
        return -1;
    }

    public int libraryContainsMember(String phoneNumber, ArrayList<LibraryMember> members) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getPhoneNumber().equals(phoneNumber)) {
                return i;
            }
        }
        return -1;
    }

    public boolean libraryBookLoaned(String title, String author, String yearOfTheRelease, ArrayList<Book> books) {
        Book temp = new Book(title, author, Integer.parseInt(yearOfTheRelease));
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equals(temp) && books.get(i).getLoaner() == null) {
                return false;
            }
        }
        return true;
    }
}
