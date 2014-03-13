/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import cz.fi.muni.vavmar.editor.tools.AbstractTool;
import cz.fi.muni.vavmar.editor.tools.TableWidget;
import cz.fi.muni.vavmar.editor.tools.TextTool;
import cz.fi.muni.vavmar.editor.tools.Tool;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;

/**
 *
 * @author Martin
 */
public class AcceptProviderImpl implements AcceptProvider {
    private static final Logger logger = LogManager.getLogger(AcceptProviderImpl.class.getName());
    private final MainScene scene;  //TODO je to kvuli addWidget, zvazit predelani
    
    public AcceptProviderImpl(MainScene scene) {
        this.scene = scene;
    }
    
    @Override
    public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
        System.out.println("AcceptProviderImpl.isAcceptable(Widget widget, Point point, Transferable transferable):");
        System.out.println("Scene: ");
        System.out.println("\twidget:" + widget);
        System.out.println("\tpoint" + point);
        System.out.println("\ttransferable:" + transferable);

        
        try {
            if(transferable.isDataFlavorSupported(Table.DATA_FLAVOUR)){

                logger.trace("Transferable je  Tabulka: " + transferable.getTransferData(Table.DATA_FLAVOUR));
                return ConnectorState.ACCEPT;

            } else if(transferable.isDataFlavorSupported(AbstractTool.DATA_FLAVOUR)) {
                logger.trace("Transferable je  Tool: " + transferable.getTransferData(AbstractTool.DATA_FLAVOUR));
                return ConnectorState.ACCEPT;

            } else {
                logger.debug("Nerozpoznany objekt!" + transferable);
                return ConnectorState.REJECT;
            }
            
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        logger.warn("It soulhdn't be here!!! Something rond happened.");
        return ConnectorState.REJECT;
    }

    @Override
    public void accept(Widget widget, Point point, Transferable transferable) {
        System.out.println("AcceptProviderImpl.accept(Widget widget, Point point, Transferable transferable)");
        Object o = null;
        
        try {
            DataFlavor[] df = transferable.getTransferDataFlavors();
            
            if(df != null && df.length > 0) {
                o = transferable.getTransferData(df[0]);

                if( o != null ){
                    if( o instanceof Table){
                        System.out.println("Akceptuji: " + ((Table) o).getName() );
                        //Vytvorim novy widget
                        getTableWidgetFromTransfer(widget, point, transferable);
                        return;
                    }

                    if ( o instanceof AbstractTool ){
                        logger.trace("Vytvarim widget nastroje: " + o);

                        Widget wg = ((AbstractTool) o).createWidget(scene);

                        if(wg == null) return;

                        wg.setPreferredLocation(point);
                        scene.addWidget( wg );    //Vytvorime widget
                        return;
                    }

                }
            } else {
                logger.warn("Unable to get DataFlavour.");
            }
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
     logger.debug("Nerozpoznany objekt nelze vlozit do sceny:" + o);
    }

    private Widget getTableWidgetFromTransfer(Widget widget, Point point, Transferable transferable) throws UnsupportedFlavorException, IOException{
        System.out.println("Widget: " + widget);
        TableWidget lw = new TableWidget(scene);
        Table tbl = (Table) transferable.getTransferData(DataFlavor.imageFlavor);
        
        lw.setLabel(tbl.getName());
        lw.setToolTipText("SQL: ???");
        lw.getActions().addAction(ActionFactory.createMoveAction());
        lw.setPreferredLocation(point);
        lw.setOpaque(true);
        scene.addWidget(lw);
        return lw;
    }
}
