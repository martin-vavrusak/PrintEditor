/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 *
 * @author Martin
 */
public class DnDListHandler extends TransferHandler {

    //Do listu zobrazujiciho sloupce tabulky nechceme nic vkladat
    @Override
    public boolean canImport(TransferSupport support) {
//        System.out.println("DnDListHandler.canImport(TransferSupport support)");
        return false;
    }

    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
//        System.out.println("DnDListHandler.canImport(JComponent comp, DataFlavor[] transferFlavors)");
        return false;
    }
    

    
    @Override
    protected Transferable createTransferable(JComponent c) {
        System.out.println("DnDListHandler.createTransferable: Objekt:" + c);
        
        String s = "";
        if(c instanceof JList){
            JList list = (JList) c;
             s = (String) list.getSelectedValue();
        }
        
        return new Table("Exportovana tabulka:" + s);
//        return super.createTransferable(c); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Must be Owerriden otherwise DnD will not work
    @Override
    public int getSourceActions(JComponent c) {
        System.out.println("DnDListHandler.getSourceActions(JComponent c)");
        return TransferHandler.MOVE; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        System.out.println("DnDListHandler.exportAsDrag(JComponent comp, InputEvent e, int action)");
        super.exportAsDrag(comp, e, action); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
         System.out.println("DnDListHandler.exportToClipboard(JComponent comp, Clipboard clip, int action)");
        super.exportToClipboard(comp, clip, action); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
}
