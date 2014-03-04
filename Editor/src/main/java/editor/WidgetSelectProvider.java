/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.Point;
import java.util.List;
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
        logger.trace("Selected widget: " + widget);
        logger.trace("At: " + localLocation + "Inverted selection: " + invertSelection);
        
        List<Widget> selectedWidgets = scene.getSelectedWidgets();
        
        if( selectedWidgets != null && selectedWidgets.size() > 0 ){
            
        }
        //test na CTRL
        //setBorder
        //scene.addSelectedWidget(widget)
        //Resize action
    }
    
}
