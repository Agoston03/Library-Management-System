package app;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private int yearOfTheRelease;
    private String category;
    private LibraryMember loanedBy;

    public Book(String title, String author, int yearOfTheRelease, String category, LibraryMember loanedBy) {
        this.title = title;
        this.author = author;
        this.yearOfTheRelease = yearOfTheRelease;
        this.category = category;
        this.loanedBy = loanedBy;
    }

    public Book(String title, String author, int yearOfTheRelease) {
        this.title = title;
        this.author = author;
        this.yearOfTheRelease = yearOfTheRelease;
        this.category = null;
        this.loanedBy = null;
    }

    @Override
    public boolean equals(Object book) {
        if (book == null) {
            return false;
        }

        if (book == this) {
            return true;
        }

        if (!(book instanceof Book)) {
            return false;
        }

        Book temp = (Book) book;
        return title.equals(temp.getTitle()) && author.equals(temp.getAuthor())
                && yearOfTheRelease == temp.getYearOfTheRelease();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearOfTheRelease() {
        return yearOfTheRelease;
    }

    public String getCategory() {
        return category;
    }

    public LibraryMember getLoaner() {
        return loanedBy;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYearOfTheRelease(int yearOfTheRelease) {
        this.yearOfTheRelease = yearOfTheRelease;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLoanedBy(LibraryMember loanedBy) {
        this.loanedBy = loanedBy;
    }

}
