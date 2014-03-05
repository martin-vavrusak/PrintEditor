/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import editor.utils.Utils;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import static oracle.jrockit.jfr.events.Bits.length;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class WidgetSelectProvider implements SelectProvider {
    private static final Logger logger = LogManager.getLogger(WidgetSelectProvider.class);
    
    private MainScene scene;

    public WidgetSelectProvider(MainScene scene) {
        this.scene = scene;
    }
    
    
    public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
        logger.trace("");
        return false;   //Pro kooperaci s RectangleSelection by melo byt udajne true, ale aby fungovalo s MoveAction musi bt false :D :D
    }

    public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
        logger.trace("");
        return true;
    }

    public void select(Widget widget, Point localLocation, boolean invertSelection) {
        Border selectedBorder = BorderFactory.createDashedBorder(Color.ORANGE, 3, 2, 1, false);
        logger.trace("Selected widget: " + widget);
        logger.trace("At: " + localLocation + "Inverted selection: " + invertSelection);
        
        List<Widget> selectedWidgets = scene.getSelectedWidgets();
        logger.trace("Selected widgets: " + selectedWidgets);
        
        if( selectedWidgets == null ) selectedWidgets = new ArrayList<Widget>();
        
        if(!invertSelection) {  //without CRTL we want only select this widget (single selection)
            logger.trace("Invertion is not set performing single selection.");
            scene.clearSelection();
            widget.setBorder( selectedBorder );
            //set Resize Action pro obrazek!!!
            selectedWidgets.add(widget);
            
        } else {
            if ( selectedWidgets.size() == 1 ) {
                logger.trace("One widget is selected and invertion is set. Adding second widget.");
                //ulozit widget
                Widget previouslySelected = selectedWidgets.get(0);
                
                //smazat selekci
                scene.clearSelection();
                
                //nastavit ho na multiple movement
                previouslySelected.setBorder( selectedBorder );
                scene.setMultiMoveAction(previouslySelected, scene.getMultipleMovementAction());
                
                //pridat nove oznaceny widget
                widget.setBorder( selectedBorder );
                scene.setMultiMoveAction(widget, scene.getMultipleMovementAction());
                
                selectedWidgets.add(previouslySelected);
                selectedWidgets.add(widget);
                
            } else if ( selectedWidgets.size() > 2 ){
                logger.trace("Two widgets are selected and invertion is set. Resolving adding/removing.");
                
            } else {
                logger.warn("Something weird happend during selection of widgets!");
            }
            
        }
        
//        if( selectedWidgets.size() > 1 ){
//            if( selectedWidgets.contains(widget) && invertSelection ) {     //if widget is selected, deselec it
//                selectedWidgets.remove(widget);
//                widget.setBorder(BorderFactory.createEmptyBorder());
//                widget.getActions().removeAction( scene.getMultipleMovementAction() );
//                
//            } else {    //otherwise add it to the selection
//                widget.setBorder(BorderFactory.createDashedBorder(Color.BLUE, 1, 1));
//                Utils.setMultiMoveAction(widget, scene.getMultipleMovementAction());
//                
//                selectedWidgets.add(widget);
//            }
//        }
        
        logger.trace("Selected widgets: " + selectedWidgets.size() + " " + selectedWidgets);
        //test na CTRL
        //setBorder
        //scene.addSelectedWidget(widget)
        //Resize action
    }
    
}
