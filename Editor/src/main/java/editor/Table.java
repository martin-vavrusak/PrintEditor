/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Table {
    private String name;
    private List<String> columns;

    public Table(String name) {
        this.name = name;
        columns = new ArrayList<String>();
    }

    
    public Table(String name, List<String> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && (obj instanceof Table)){
            Table tbl = (Table) obj;
            return this.name.equals(tbl.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    
    
    
}
