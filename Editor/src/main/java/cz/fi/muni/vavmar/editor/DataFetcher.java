/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Staticka trida reprezentujici data z databaze, vytvarejici data pro zobrazeni v jListech
 * V produkcni verzi musi byt nahrazena Tridou poskytujici data z databaze
 * @author Martin
 */
public class DataFetcher {
    
    private Map<Table, List<String>> tables;

    public DataFetcher() {
        createTables();
    };
    
    
    /*
    * Vyhledá tabulky začínající na daný string
    */
    public List<String> searchTables(String s){
        List<String> list = new ArrayList<String>();
        
        for(Table t: tables.keySet()){
            if(t.getName().length() < s.length()) return list;
                     
            if( t.getName().substring(0, s.length()).equalsIgnoreCase(s) ){
                list.add(t.getName());
            }
        }
        return list;
    }
    
    public List<String> getAllTablesString(){
        List<String> tableNames = new ArrayList<String>();
        for(Table t: tables.keySet()){
            tableNames.add(t.getName());
        }
        return tableNames;
    }
    
    public Collection<Table> getAllTables(){
        return Collections.unmodifiableCollection( tables.keySet() );
    };
    
    public List<String> getColumns(Table table){
        return tables.get(table);
    };
    
    private void createTables(){
        tables = new HashMap<Table, List<String>>();
        List<String> columns = new ArrayList<String>();
        
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 5; j++){
                columns.add("Column" + j);
            }
            tables.put(new Table("Table" + i, null), columns);
            //tables.add(new Table("Table" + i, columns));
            
            columns = new ArrayList<String>();
        }
        
        columns = new ArrayList<String>();
        columns.add("jmeno");
        columns.add("prijmeni");
        
        tables.put(new Table("Osoby"), columns);
        
        columns = new ArrayList<String>();
        columns.add("jedna");
        columns.add("dve");
        tables.put(new Table("Ostatni"), columns);
        
        columns = new ArrayList<String>();
        columns.add("Vaha");
        columns.add("Vyska");
        columns.add("Vek");
        tables.put(new Table("Olina"), columns);
        
        columns = new ArrayList<String>();
        columns.add("Euro");
        columns.add("Koruna");
        columns.add("Dolar");
        tables.put(new Table("obezivo"), columns);
    };

    public void setData(Map<Table, List<String>> data){
        tables = data;
    }
}
