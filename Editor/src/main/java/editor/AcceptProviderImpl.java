/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import cz.fi.muni.vavmar.editor.tools.AbstractTool;
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
    private final Scene scene;
    
    public AcceptProviderImpl(Scene scene) {
        this.scene = scene;
    }
    
    @Override
    public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
        System.out.println("AcceptProviderImpl.isAcceptable(Widget widget, Point point, Transferable transferable):");
        System.out.println("Scene: ");
        System.out.println("\twidget:" + widget);
        System.out.println("\tpoint" + point);
        System.out.println("\ttransferable:" + transferable);

        Object o = null;
        try {
            o = transferable.getTransferData(Table.DATA_FLAVOUR);

        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        if( o != null ){
            if( o instanceof Table){
                System.out.println("Akceptuji: " + ((Table) o).getName() );
                return ConnectorState.ACCEPT;
            }
            
            if(o instanceof AbstractTool){
                logger.trace("Objekt je typu AbstractTool!! Huraaaa!" + o);
                return ConnectorState.ACCEPT;
            }
            
            
        }
        logger.debug("Nerozpoznany objekt!" + o);
        return ConnectorState.REJECT;
    }

    @Override
    public void accept(Widget widget, Point point, Transferable transferable) {
        System.out.println("AcceptProviderImpl.accept(Widget widget, Point point, Transferable transferable)");
        Object o = null;
        
        try {
            o = transferable.getTransferData(Table.DATA_FLAVOUR);
            
            if( o != null ){
                if( o instanceof Table){
                    System.out.println("Akceptuji: " + ((Table) o).getName() );
                    //Vytvorim novy widget
                    getTableWidgetFromTransfer(widget, point, transferable);
                } else {
                    System.out.println("Objekt neni typu Table! Nelze vytvorit widget: " + o);
                }
                
                if ( o instanceof AbstractTool ){
                    logger.trace("Vytvarim widget nastroje: " + o);
                    scene.addChild( ((AbstractTool) o).createWidget(scene) );    //Vytvorime widget
                }
            }
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        
    }

    private Widget getTableWidgetFromTransfer(Widget widget, Point point, Transferable transferable) throws UnsupportedFlavorException, IOException{
        System.out.println("Widget: " + widget);
        LabelWidget lw = new LabelWidget(scene);
        Table tbl = (Table) transferable.getTransferData(DataFlavor.imageFlavor);
        
        lw.setLabel(tbl.getName());
        lw.setToolTipText("SQL: ???");
        lw.getActions().addAction(ActionFactory.createMoveAction());
        lw.setPreferredLocation(point);
        scene.addChild(lw);
        return lw;
    }
}
