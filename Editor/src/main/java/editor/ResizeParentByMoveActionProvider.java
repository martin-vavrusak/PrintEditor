/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.Point;
import java.awt.Rectangle;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class ResizeParentByMoveActionProvider implements MoveProvider {

    public void movementStarted(Widget widget) {
        System.out.println("movementStarted:" + widget);
    }

    public void movementFinished(Widget widget) {
        System.out.println("movementFinished:" + widget);
    }

    public Point getOriginalLocation(Widget widget) {
        System.out.println("getOriginalLocation:" + widget.getPreferredLocation());
        return widget.getPreferredLocation ();
    }

    public void setNewLocation(Widget widget, Point location) {
        System.out.println("setNewLocation:" + widget.getPreferredLocation()+ " location:" + location 
                    + "Bounds: " + widget.getParentWidget().getBounds() 
                    + " insets: " + widget.getParentWidget().getBorder().getInsets());
        widget.setPreferredLocation (location);
        
        Widget parent = widget.getParentWidget();
        Rectangle parentBounds = parent.getBounds();    //get parent bounds
        
        System.out.println("Parent bounds: " + parentBounds + " location: " + location);
        parentBounds.add(location);
        System.out.println("Parent bounds after: " + parentBounds);
        
        parent.setPreferredBounds(parentBounds);
    }
    
}
