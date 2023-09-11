/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Person;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author s_vre
 */
public class PersonTableModel extends AbstractTableModel{

    private static final String[] COLUMN_NAMES = {
      "Id", "Name"
    };
    private List<Person> persons;

    public PersonTableModel(List<Person> persons) {
        this.persons = persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        fireTableDataChanged();
    }      
            
    @Override
    public int getRowCount() {
        return (persons == null) ? 0 : persons.size();
    }

    @Override
    public int getColumnCount() {
        return  COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return persons.get( rowIndex).getId();
            case 1:
                return persons.get( rowIndex).getName();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column]; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
