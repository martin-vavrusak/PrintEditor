/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.TransferHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handler for draging items from column JList.
 * 
 * @author Martin
 */
public class DnDColumnListHandler extends TransferHandler {
    private static final Logger logger = LogManager.getLogger(DnDColumnListHandler.class);
    private MainFrame mainFrame;

    public DnDColumnListHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    
    //Do listu zobrazujiciho sloupce tabulky nechceme nic vkladat
    @Override
    public boolean canImport(TransferSupport support) {
//        System.out.println("DnDColumnListHandler.canImport(TransferSupport support)");
        logger.trace("");
        return false;
    }

    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
//        System.out.println("DnDColumnListHandler.canImport(JComponent comp, DataFlavor[] transferFlavors)");
        logger.trace("");
        return false;
    }
    

    /**
     * Pouze vytvoreni "servisnich informaci" realny objekt a data ziskavat az pomoci accept() na prijmaci strane
     * @param c
     * @return 
     */
    @Override
    protected Transferable createTransferable(JComponent c) {
        logger.trace("");
        System.out.println("DnDListHandler.createTransferable: Objekt:" + c);
        
        String tableName = (String) mainFrame.getTablesListArea().getSelectedValue();
        if(tableName == null){
            logger.error("Error preparing transfer of column. Unable to get table name from Table List. Table retrieved: ");
        }
        
        logger.trace("Retrieved table from table list: " + tableName);
        String s = "";
        if(c instanceof JList){
            JList list = (JList) c;
             s = (String) list.getSelectedValue();      //Get selected item from list (returns string prepresenting column)
        }
        
        Table t = new Table(tableName, null);                      //pass the name for future use
        t.setSelectedColumn(s);
        
        return t;
    }
    
    //Must be Owerriden otherwise DnD will not work
    @Override
    public int getSourceActions(JComponent c) {
        logger.trace("");
        System.out.println("DnDListHandler.getSourceActions(JComponent c)");
        return TransferHandler.MOVE; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        logger.trace("");
        System.out.println("DnDListHandler.exportAsDrag(JComponent comp, InputEvent e, int action)");
        super.exportAsDrag(comp, e, action); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
        logger.trace("");
         System.out.println("DnDListHandler.exportToClipboard(JComponent comp, Clipboard clip, int action)");
        super.exportToClipboard(comp, clip, action); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
}
