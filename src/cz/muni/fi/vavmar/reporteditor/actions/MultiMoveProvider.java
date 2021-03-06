/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor.actions;

import cz.muni.fi.vavmar.reporteditor.MainScene;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 * Movement provider for moving with set of selected Widgets
 * @author Martin
 */
public class MultiMoveProvider implements MoveProvider {
    private static final Logger logger = LogManager.getLogger(MultiMoveProvider.class);
    private MainScene scene;
    private Map<Widget, Point> originalPositions;
    private Point originalRepresentativeWidgetPosition; //posistion of representant of selected group
                                                        //(Widget in selected group which is draged)
    
    public MultiMoveProvider(MainScene scene) {
        this.scene = scene;
    }
    
    
    public void movementStarted(Widget widget) {
        originalPositions = new HashMap<Widget, Point>();
        for(Widget w : scene.getSelectedWidgets() ){
            originalPositions.put(w, new Point(w.getPreferredLocation()));
        }
    }

    public void movementFinished(Widget widget) {
        revalidateParentLayerBounds();
    }

    //Called as 1 st method
    public Point getOriginalLocation(Widget widget) {
        originalRepresentativeWidgetPosition = widget.getPreferredLocation();
        return originalRepresentativeWidgetPosition;
    }
    
    /**
     * Called at every movement of mouse movement.
     * Compute vector of movement, add it to original position and set it to the widgets.
     * @param widget - widget whose position has been changed
     * @param location - new location of mouse
     */
    public void setNewLocation(Widget widget, Point location) {
        int dx = location.x - originalRepresentativeWidgetPosition.x;   //get movement vector
        int dy = location.y - originalRepresentativeWidgetPosition.y;
        
        logger.trace("original position: " + originalPositions);
        logger.trace("suggested position: " + location);
        logger.trace("dx: " + dx + " dy: " + dy);
        
        for( Widget w : scene.getSelectedWidgets() ){
            //must be created new Point otherwise widget wont be moved
            Point position = new Point(originalPositions.get(w));
            position.translate(dx, dy);
            w.setPreferredLocation( position ); 
        }
    }
    
    /**
     * Check if any of widgets lays out of scene bounds
     * if so then expands scene bounds to contain this "outern" widget <br/>
     * <b>Note:</b> <br/>
     * Must be called after all final positions has been set!<br>
     * All widgets must be in the same layer!
     */
    private void revalidateParentLayerBounds(){
        Set<Widget> selectedWidgets = scene.getSelectedWidgets();
        
        if(selectedWidgets != null && selectedWidgets.size() > 0){
            Rectangle parentBounds;
                    
            //Retrieve bounds of parent LayerWidget
            Iterator<Widget> iterator = selectedWidgets.iterator();
             
                            //no need of checking for next checket at outern if() clause
            parentBounds = iterator.next().getParentWidget().getBounds();   //need to get parent layer so use anyone of widget moved
            logger.trace("Parent bounds: " + parentBounds);

            for( Widget w: selectedWidgets ){
                Point widgetPosition = w.getPreferredLocation();
                logger.trace("Widget preffered location: " + widgetPosition);

                parentBounds.add(widgetPosition);
//                scen
            } 
            
        } else {
            //There are no selected widgets
            logger.warn("Weird situation in multimovement selectedWidgets: " + selectedWidgets);
        }
    }
}
