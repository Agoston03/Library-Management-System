package app;

import java.util.ArrayList;

public class BookSearching {
    public static ArrayList<Book> books;

    public static ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> output = new ArrayList<>();
        for (Book i : books) {
            if (i.getTitle().contains(title)) {
                output.add(i);
            }
        }
        return output;
    }

    public static ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> output = new ArrayList<>();
        for (Book i : books) {
            if (i.getAuthor().contains(author)) {
                output.add(i);
            }
        }
        return output;
    }

    public static ArrayList<Book> searchByYearOfTheRelease(String yearOfTheRelease) {
        if (!yearOfTheRelease.matches("\\d*")) {
            return books;
        }
        int year = Integer.parseInt(yearOfTheRelease);
        ArrayList<Book> output = new ArrayList<>();
        for (Book i : books) {
            if (i.getYearOfTheRelease() == year) {
                output.add(i);
            }
        }
        return output;
    }

    public static ArrayList<Book> searchByLoaner(String phoneNumber) {
        ArrayList<Book> output = new ArrayList<>();
        for (Book i : books) {
            if (i.getLoaner() != null && i.getLoaner().getPhoneNumber().contains(phoneNumber)) {
                output.add(i);
            }
        }
        return output;
    }

    public static ArrayList<Book> searchByCategory(String cat) {
        ArrayList<Book> output = new ArrayList<>();
        for (Book i : books) {
            if (i.getCategory().contains(cat)) {
                output.add(i);
            }
        }
        return output;
    }
}
