package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import gui.DataVerification;

public class Library {

    private ArrayList<Book> books;

    private ArrayList<LibraryMember> members;

    private DataVerification verification;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        verification = new DataVerification();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<LibraryMember> getMembers() {
        return members;
    }

    public boolean addBook(String title, String author, String yearOfTheRelease, String category) {
        if (verification.verifyBook(title, author, yearOfTheRelease)
                && verification.libraryContainsBook(title, author, yearOfTheRelease, books) == -1) {
            return books.add(new Book(title, author, Integer.parseInt(yearOfTheRelease), category, null));
        }
        return false;
    }

    public boolean modifyBook(int idx, String title, String author, String yearOfTheRelease, String category) {
        if (verification.verifyBook(title, author, yearOfTheRelease)
                && (verification.libraryContainsBook(title, author, yearOfTheRelease, books) == -1
                        || verification.libraryContainsBook(title, author, yearOfTheRelease, books) == idx)) {
            books.get(idx).setTitle(title);
            books.get(idx).setAuthor(author);
            books.get(idx).setYearOfTheRelease(Integer.parseInt(yearOfTheRelease));
            books.get(idx).setCategory(category);
            return true;
        }
        return false;
    }

    public boolean removeBook(int idx) {
        if (!books.isEmpty() && idx >= 0 && books.remove(idx) != null) {
            return true;
        }
        return false;
    }

    public boolean addMember(String name, String birthYear, String phoneNumber) {
        int id = verification.libraryContainsMember(phoneNumber, members);
        if (verification.verifyMember(name, birthYear, phoneNumber)
                && id == -1) {
            LibraryMember member = new LibraryMember(name, Integer.parseInt(birthYear), phoneNumber);
            return members.add(member);
        }
        return false;
    }

    public boolean rentBook(String title, String author, String yearOfTheRelease, String name, String phoneNumber) {
        int id = verification.libraryContainsMember(phoneNumber, members);
        if (verification.verifyBook(title, author, yearOfTheRelease)
                && verification.libraryContainsBook(title, author, yearOfTheRelease, books) >= 0 && id != -1
                && !verification.libraryBookLoaned(title, author, yearOfTheRelease, books)) {
            Book tempForBook = new Book(title, author, Integer.parseInt(yearOfTheRelease));
            LibraryMember tempForMember = new LibraryMember(name, phoneNumber);
            for (Book i : books) {
                if (i.equals(tempForBook)) {
                    for (LibraryMember j : members) {
                        if (j.equals(tempForMember)) {
                            i.setLoanedBy(j);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean returnBook(String title, String author, String yearOfTheRelease) {
        if (verification.libraryContainsBook(title, author, yearOfTheRelease, books) >= 0) {
            Book temp = new Book(title, author, Integer.parseInt(yearOfTheRelease));
            for (Book i : books) {
                if (i.equals(temp)) {
                    i.setLoanedBy(null);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean modifyMember(int idx, String name, String birthYear, String phoneNumber) {
        int id = verification.libraryContainsMember(phoneNumber, members);
        if (verification.verifyMember(name, birthYear, phoneNumber) && (id == -1 || id == idx)) {
            members.get(idx).setName(name);
            members.get(idx).setBirthYear(Integer.parseInt(birthYear));
            members.get(idx).setPhoneNumber(phoneNumber);
            return true;
        }
        return false;
    }

    public boolean removeMember(int idx) {
        if (!members.isEmpty() && idx < 0) {
            return false;
        } else {
            for (Book i : books) {
                if (i.getLoaner() != null && i.getLoaner().equals(members.get(idx))) {
                    i.setLoanedBy(null);
                }
            }
            if (members.remove(idx) != null) {
                return true;
            }
            return false;
        }
    }

    public void save() {
        File file = new File("database\\database.txt");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(books);
            out.writeObject(members);
            out.close();
        } catch (IOException e) {
            try {
                file.createNewFile();
            } catch (IOException ea) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void load() {
        File file = new File("database\\database.txt");
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            books = (ArrayList<Book>) in.readObject();
            members = (ArrayList<LibraryMember>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            try {
                file.createNewFile();
            } catch (IOException ea) {
                System.out.println(e.getMessage());
            }
        }
    }
}
