/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Martin
 */
public class Table implements Serializable, Transferable {
    private static final Logger logger = LogManager.getLogger(Table.class);
    
    public static DataFlavor DATA_FLAVOUR = new DataFlavor(Table.class, "TableInformaitons");
    private static final long serialVersionUID = 3234894312346698435L;

    
    private String selectedColumn;
    private String description;	
    private String selectSQL;
    private String name;
    private List<String> columns;

    public Table(){
        this(null, null, null, new ArrayList<String>() );
    }
    
    public Table(String name, String description) {
        this(name, description, null, new ArrayList<String>() );
    }
    
    public Table(String name, String description, String selectSQL, List<String> columns) {
        this.description = description;
        this.selectSQL = selectSQL;
        this.name = name;
        this.columns = columns;
    }

    public String getSelectedColumn() {
        return selectedColumn;
    }

    public void setSelectedColumn(String selectedColumn) {
        this.selectedColumn = selectedColumn;
    }
    
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectSQL() {
        return selectSQL;
    }

    public void setSelectSQL(String selectSQL) {
        this.selectSQL = selectSQL;
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

    public DataFlavor[] getTransferDataFlavors() {
        logger.trace("");
        return new DataFlavor[] { new DataFlavor(Table.class, "TableInformations")};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        logger.trace("Table.isDataFlavorSupported(DataFlavor flavor): Ziskany DF:" + flavor);
        
        logger.trace("flavor.equals( new DataFlavor(Table.class, \"BlaBla\"):" + flavor.equals( new DataFlavor(Table.class, "BlaBla")) );
        return (flavor.equals( new DataFlavor(Table.class, "BlaBla")));
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
//        logger.trace("Table.getTransferData(DataFlavor flavor):");
        logger.trace("flavor.getRepresentationClass():" + flavor.getRepresentationClass());
//        return new Table("Aahaha Jsem tady!");
        return this;
    }

}
