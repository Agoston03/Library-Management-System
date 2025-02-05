package app;

import java.io.Serializable;

public class LibraryMember implements Serializable {
    private String name;
    private int birthYear;
    private String phoneNumber;

    public LibraryMember(String name, int birthYear, String phoneNumber) {
        this.name = name;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
    }

    public LibraryMember(String name, String phoneNumber) {
        this.name = name;
        this.birthYear = 0;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object member) {
        if (member == null) {
            return false;
        }

        if (member == this) {
            return true;
        }

        if (!(member instanceof LibraryMember)) {
            return false;
        }

        LibraryMember temp = (LibraryMember) member;
        return phoneNumber.equals(temp.getPhoneNumber()) && name.equals(temp.getName());
    }

    public String getName() {
        return name;
    }

    public int getbirthYear() {
        return birthYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
