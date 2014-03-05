/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.HoverProvider;
import org.netbeans.api.visual.action.RectangularSelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.MouseHoverAction;
import org.netbeans.modules.visual.action.SelectAction;


/**
 * WidgetRectangularSelectionProvider
 * @author Martin
 */
public class WidgetRectangularSelectionProvider implements RectangularSelectProvider {
    private static final Logger logger = LogManager.getLogger(WidgetRectangularSelectionProvider.class);

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
        
        List<Widget> selectedWidgets = new ArrayList<Widget>();

        clearSceneSelection();  //"mazani" musi byt volano v teto tride jinak by
                                //nedoslo k spravnemu nastaveni borderu v pripade
                                //kdy je opakovane vybran ten samy widget
        
        //vyber je mozne provest:
        //      1. zleva doprava a dolu, nebo
        //      2. zespod doleva nahoru
        
        if (sceneSelection.width < 0) {
            sceneSelection.x += sceneSelection.width;   //prevedeni zpusobu 2. na zpusob 1.
            sceneSelection.width *= -1;
        }
        if (sceneSelection.height < 0) {
            sceneSelection.y += sceneSelection.height;  //prevedeni zpusobu 2. na zpusob 1.
            sceneSelection.height *= -1;
        }
        
        logger.trace("scene position:" + scene.getBounds());
        for( Widget w : scene.getChildren() ){
            logger.trace("Layer position:" + w.getBounds());
        }
        
        for ( Widget w : layerOfWidgets.getChildren() ){
            logger.trace(">>>> Selection rectangle: " + sceneSelection );
            if(w instanceof LabelWidget){
                
                
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
                logger.trace("Pozice vyberu: " + sceneSelection + "(prepoctena do kladnych hodnot)");
                logger.trace("sceneSelection.intersects( widgetPosition ): " + sceneSelection.intersects( widgetPosition ) );
                logger.trace( " widgetPosition.intersects(sceneSelection): " + widgetPosition.intersects( sceneSelection ) );
                logger.trace("converted Bounds: " + scene.convertLocalToScene(widgetPosition) );

                if( sceneSelection.intersects( widgetPosition ) ){
                    w.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1, 1));
                    selectedWidgets.add(w);
                    
                    scene.setMultiMoveAction(w, multipleMovementAction);
                    
                    logger.trace("Do vyberu pridan widget: " + w);
                }
                
            } else {
                logger.error("This is not a LayerWidget:" + w);
            }
        }
        
        scene.setSelectedWidgets(selectedWidgets);
//        scene.setSelectedObjects(selectedWidgetsSet);
        
        
    }
    
//    private void setMultiMoveAction(Widget widget, WidgetAction action){
//        List<WidgetAction> actions = widget.getActions().getActions();
//        if(actions.size() >= 2){
//            WidgetAction firstAction = actions.get(0);
//            WidgetAction secondAction = actions.get(1);
//            if( (firstAction instanceof SelectAction || firstAction instanceof MouseHoverAction) ||
//                (secondAction instanceof SelectAction || secondAction instanceof MouseHoverAction)  ){
//                
//                if(actions.size() > 2){
//                    widget.getActions().addAction(2, action);
//                    return;
//                } else {
//                    widget.getActions().addAction(action);
//                    return;
//                }
//            }
//        }
//        
//        
//        if(actions.size() >= 1) {
//            WidgetAction widgetAction = actions.get(0);
//            if( widgetAction instanceof SelectAction || widgetAction instanceof MouseHoverAction ) {
//                if(actions.size() > 1){
//                    widget.getActions().addAction(1, action);
//                    return;
//                } else {
//                    widget.getActions().addAction(action);
//                    return;
//                }
//            }
//        }
//        
////        if( actions.size() > 1 && actions.get(0) instanceof SelectAction ){ //jestlize widget ma vice nez 1 akci a prvni je SelectAction
////            widget.getActions().addAction(1, action);    //vloz movement hned za select
////
////        } else if ( actions.size() == 1 && actions.get(0) instanceof SelectAction ){
////            widget.getActions().addAction(action);
////        } else {
////            widget.getActions().addAction(0, action);
////        }
//    }
    
    private void clearSceneSelection(){
        List<Widget> oldWidgetSelection = scene.getSelectedWidgets();
        if(oldWidgetSelection != null && oldWidgetSelection.size() > 0){
            for(Widget w : scene.getSelectedWidgets()){
                //reset border
                w.setBorder(BorderFactory.createEmptyBorder());
                
                //Cancel multiple moving provider
                w.getActions().removeAction( multipleMovementAction );
                logger.trace("Selection cancelled: " + w);
            }
        }
        
    }
}
