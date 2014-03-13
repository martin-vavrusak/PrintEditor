/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.actions;

import java.awt.Point;
import java.awt.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *  Zvetsi scenu pri presunuti widgetu mimo rozsah sceny
 * @author Martin
 */
public class ResizeParentByMoveActionProvider implements MoveProvider {
    private static final Logger logger = LogManager.getLogger(ResizeParentByMoveActionProvider.class);
    
    
    public void movementStarted(Widget widget) {
        logger.trace("movementStarted:" + widget);
    }

    public void movementFinished(Widget widget) {
        logger.trace("movementFinished:" + widget);
    }

    public Point getOriginalLocation(Widget widget) {
        logger.trace("getOriginalLocation:" + widget.getPreferredLocation());
        return widget.getPreferredLocation ();
    }

    public void setNewLocation(Widget widget, Point location) {
        logger.trace("setNewLocation:" + widget.getPreferredLocation()+ " location:" + location 
                    + "Bounds: " + widget.getParentWidget().getBounds() 
                    + " insets: " + widget.getParentWidget().getBorder().getInsets());
        widget.setPreferredLocation (location);
//        Rectangle bounds = widget.getBounds();
//        bounds.setLocation(location);
//        
//        widget.setPreferredBounds(bounds);
        
        Widget parent = widget.getParentWidget();
        Rectangle parentBounds = parent.getBounds();    //get parent bounds
        
        logger.trace("Parent bounds: " + parentBounds + " location: " + location);
        parentBounds.add(location);
        logger.trace("Parent bounds after: " + parentBounds);
        
        parent.setPreferredBounds(parentBounds);
    }
    
}
