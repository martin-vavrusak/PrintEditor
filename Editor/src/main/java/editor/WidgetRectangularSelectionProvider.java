/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.RectangularSelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;


/**
 * WidgetRectangularSelectionProvider
 * @author Martin
 */
public class WidgetRectangularSelectionProvider implements RectangularSelectProvider {
    private static final Logger logger = LogManager.getLogger(WidgetRectangularSelectionProvider.class);
    public static final Border selectedBorder = BorderFactory.createDashedBorder(Color.BLACK, 3, 2, 1, true);
    
    private static WidgetAction multipleMovementAction;       //TODO mozna presunout do MainScene a get/set -ovat to
    private MainScene scene;
    private LayerWidget layerOfWidgets;
    
    /**
     * Provide selection of widgets at layer provided as parameter
     * @param scene
     * @param layerOfWidgets - LayerWidget layer at which selection of widgets should be performed
     */
    public WidgetRectangularSelectionProvider(MainScene scene, LayerWidget layerOfWidgets) {
        this.scene = scene;
        this.layerOfWidgets = layerOfWidgets;
        multipleMovementAction = scene.getMultipleMovementAction();
    }
    
    
    
    public void performSelection(Rectangle sceneSelection) {
        logger.trace("Selection: " + sceneSelection);
        
        Set<Widget> selectedWidgets = getSelectedWidgets(sceneSelection, layerOfWidgets);
        
        if(!scene.isControlPressed()){  //if CTRL is not pressed make siple selection
            //delete previously selected widgets
            scene.clearSelection();  //"mazani" musi byt volano v teto tride jinak by
                                    //nedoslo k spravnemu nastaveni borderu v pripade
                                    //kdy je opakovane vybran ten samy widget
            
            for(Widget w : selectedWidgets){    //edit all selected widgets to have decorated border and action
                
                w.setBorder( selectedBorder );
                scene.setMultiMoveAction( w, multipleMovementAction );
                logger.trace("Do vyberu pridan widget: " + w);
            }

            scene.setSelectedWidgets( selectedWidgets );
            
        } else {    //otherwise perform differencial selection (select unselected and unselect selected)
            logger.trace("Performing differencial selection.");
            Set<Widget> oldSelestion = scene.getSelectedWidgets();
            
            for(Widget w: selectedWidgets){
                if(oldSelestion.contains(w)){
                    //unselect
                    w.setBorder(BorderFactory.createEmptyBorder());
                    w.getActions().removeAction( multipleMovementAction );
                    oldSelestion.remove(w);
                    
                } else {
                    //select & add
                    w.setBorder(selectedBorder);
                    scene.setMultiMoveAction(w, multipleMovementAction);
                    oldSelestion.add(w);
                }
            }
            
        }
    }
    
    /**
     * Check all widgets on the layer whether are contained in selection
     * 
     * @param selectionRectangle
     * @return List list of selected widgets or empty list. Never returns null.
     */
    private Set<Widget> getSelectedWidgets(Rectangle selectionRectangle, LayerWidget selectionLayer){
         
        Set<Widget> selectedWidgets = new HashSet<Widget>();

        //vyber je mozne provest:
        //      1. zleva doprava a dolu, nebo
        //      2. zespod doleva nahoru
        
        if (selectionRectangle.width < 0) {
            selectionRectangle.x += selectionRectangle.width;   //prevedeni zpusobu 2. na zpusob 1.
            selectionRectangle.width *= -1;
        }
        if (selectionRectangle.height < 0) {
            selectionRectangle.y += selectionRectangle.height;  //prevedeni zpusobu 2. na zpusob 1.
            selectionRectangle.height *= -1;
        }
        
        
        logger.trace("scene position:" + scene.getBounds());
        for( Widget w : scene.getChildren() ){
            logger.trace("Layer position:" + w.getBounds());
        }
        
        for ( Widget w : selectionLayer.getChildren() ){
            logger.trace(">>>> Selection rectangle: " + selectionRectangle );
            
//            if(w instanceof LabelWidget){
                                
                        logger.trace("Widget bounds: " + w.getBounds());
                        logger.trace("Widget prefferedBounds: " + w.getPreferredBounds());
                        logger.trace("Widget location: " + w.getLocation());
                        logger.trace("Widget prefferedLocation: " + w.getPreferredLocation());
                        logger.trace("Widget prefferedSize:" + w.getPreferredSize() );
                
                Rectangle widgetPosition = w.getBounds();
                //sometimes bounds of widget are not changed after moving widget
                if( !widgetPosition.getLocation().equals( w.getPreferredLocation() ) ){
                    widgetPosition.setLocation( w.getPreferredLocation());
                }
                
                        logger.trace("Test:");
                        logger.trace("Pozice widgetu: " + widgetPosition);
                        logger.trace("Pozice vyberu: " + selectionRectangle + "(prepoctena do kladnych hodnot)");
                        logger.trace("sceneSelection.intersects( widgetPosition ): " + selectionRectangle.intersects( widgetPosition ) );
                        logger.trace( " widgetPosition.intersects(sceneSelection): " + widgetPosition.intersects( selectionRectangle ) );
                        logger.trace("converted Bounds: " + scene.convertLocalToScene(widgetPosition) );

                if( selectionRectangle.intersects( widgetPosition ) ){
                    
                    selectedWidgets.add(w);    
                    logger.trace("Do vyberu pridan widget: " + w);
                }
                
//            } else {
//                logger.error("This is not a LayerWidget:" + w);
//            }
        }
        return selectedWidgets;
    }
}
