package gui.members.member;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import app.LibraryMember;

public class MemberTableModel extends AbstractTableModel {
    private transient ArrayList<LibraryMember> members;
    private String[] columnNames = { "Név", "Születés éve", "Telefonszám" };

    public MemberTableModel(ArrayList<LibraryMember> members) {
        this.members = members;
    }

    @Override
    public int getRowCount() {
        return members.size();
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
        LibraryMember member = members.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return member.getName();
            case 1:
                return member.getbirthYear();
            case 2:
                return member.getPhoneNumber();
            default:
                return null;
        }
    }

    public void refresh() {
        fireTableRowsInserted(0, members.size() - 1);
    }
}
