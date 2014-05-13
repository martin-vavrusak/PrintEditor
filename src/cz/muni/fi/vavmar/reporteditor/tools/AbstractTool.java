/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor.tools;

import cz.muni.fi.vavmar.reporteditor.MainScene;
import cz.muni.fi.vavmar.reporteditor.utils.Utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public abstract class AbstractTool extends JLabel implements Transferable, Serializable {
    private static final long serialVersionUID = 3547198343264864303L;
    protected static Logger logger = LogManager.getLogger(AbstractTool.class.getName());

    public static final DataFlavor DATA_FLAVOUR = new DataFlavor(AbstractTool.class, "AbstractTool");   //DataFlavour which represents Tool classes
    
    public AbstractTool(Icon icon) {
        super(icon);
        init();
    }

    public AbstractTool(String text) {
        super(text);
        init();
    }
    
    public AbstractTool(String text, String iconPath) {
        super(text, CENTER);
        setIcon(iconPath);
        init();
    }

    
    public abstract Widget createWidget(MainScene scene);
    
    
    /**
     * Urcuje cestu uvnitr JAR archivu
     * @param path - relativni cesta uvnitr archivu
     */
    public Icon setIcon( String path ){
        Icon icon = null; //
        if (path != null){
            URL url = getClass().getClassLoader().getResource(path);
            logger.trace("Obrazek: '" + path + "' umisten na: " + url);
            if(url == null){
            	Image iconImage = Utils.createDefaultImage( new Dimension(32, 32) );
            	icon = new ImageIcon(iconImage);
            } else {
            	icon = new ImageIcon(url);
            }
        }
        setIcon(icon);  //metoda JLabel
        return icon;
    }
    
    private void init(){
        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalTextPosition(JLabel.CENTER);
        //TODO prepsat!!!
//        setTransferHandler( new TransferHandler("text") );
        setTransferHandler( new DnDHandler() );
        
        addMouseMotionListener(new MouseDragAdapter());
        
        
    }

    
    //TODO popremyslet jestli nenechat kazdy nastroj implementovat zvlast se zvlastnim DataFlavour
    //Metods from Transferable interface
    public DataFlavor[] getTransferDataFlavors() {
        logger.trace("");
        return new DataFlavor[] { DATA_FLAVOUR };       //Return DataFlavour represented by this class
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        logger.trace("Recieved flavour:" + flavor);
        return flavor.equals(DATA_FLAVOUR);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        logger.trace("Recieved flavour:" + flavor);
        return this;        //return this instance of tool to work with (for retrieving windget - getWidget())
    }
   
    /**
     * Handler obsluhujici prenos instance 
     */
    private class DnDHandler extends TransferHandler {

        public DnDHandler() {
            super("text");
        }
        
        
        //Nechceme importovat do labelu nic - nechceme nic prijmat
        @Override
        public boolean canImport(TransferSupport support) {
            logger.trace("(TransferSupport support)" + support);
            return false;
        }

        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            logger.trace("(JComponent comp, DataFlavor[] transferFlavors)" + comp + " flavours: " + transferFlavors.toString());
            return false;
        }

        /**
         * 
         * @param Jelikoz trida AbstractTool implemntuje transferable, vracime primo instanci AbstractTool resp jeji dedici tridy
         * @return should return itself
         */
        @Override
        protected Transferable createTransferable(JComponent c) {
            logger.trace("Ziskana komponenta: " + c);
            return AbstractTool.this;
        }

        //Urcuje jake akce podporuje zdron DnD - Musi byt pretizeno !!!! Jinak DnD nebude fungovat
        @Override
        public int getSourceActions(JComponent c) {
            logger.trace("");
//            return super.getSourceActions(c);
            return TransferHandler.COPY_OR_MOVE;    //TODO nechapu proc musi byt COPY_OR_MOVE, samostatne nefunguje! Pravdepodobe to ma co delat s MouseActions
        }

        
    }
    
    private class MouseDragAdapter extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            logger.trace("Mouse dragged: " + e.paramString());
            System.out.println("Mouse Dragg");
            JComponent c = (JComponent) e.getSource();
            
            c.getTransferHandler().exportAsDrag(c, e, TransferHandler.COPY);    //Proto nefunguje move :D
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
            JComponent c = (JComponent) e.getSource();
            
            c.getTransferHandler().exportAsDrag(c, e, TransferHandler.COPY);
            logger.trace("Mouse pressed: " + e.paramString());
            System.out.println("Mouse press");
        }
        
        
    }
}
