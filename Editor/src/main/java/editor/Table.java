/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Table implements Serializable, Transferable{
    public static DataFlavor DATA_FLAVOUR = new DataFlavor(Table.class, "TableInformaitons");
    private static final long serialVersionUID = 3234894312346698435L;
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

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { new DataFlavor(Table.class, "TableInformations")};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        System.out.println("Table.isDataFlavorSupported(DataFlavor flavor): Ziskany DF:" + flavor);
        
        System.out.println("flavor.equals( new DataFlavor(Table.class, \"BlaBla\"):" + flavor.equals( new DataFlavor(Table.class, "BlaBla")) );
        return (flavor.equals( new DataFlavor(Table.class, "BlaBla")));
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        System.out.println("Table.getTransferData(DataFlavor flavor):");
        System.out.println("flavor.getRepresentationClass():" + flavor.getRepresentationClass());
//        return new Table("Aahaha Jsem tady!");
        return this;
    }

}
