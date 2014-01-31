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
import java.util.Date;

/**
 *
 * @author Martin
 */
public class MyTransferableObject implements Transferable {

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { new DataFlavor(Date.class, "Date data flavour") };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return true;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return new Date();
    }
    
    
}
