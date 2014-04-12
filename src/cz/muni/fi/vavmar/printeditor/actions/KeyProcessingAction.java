/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.actions;

import cz.muni.fi.vavmar.printeditor.MainScene;
import java.awt.event.KeyEvent;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class KeyProcessingAction extends WidgetAction.Adapter{
    private static final Logger logger = LogManager.getLogger(KeyProcessingAction.class);
    private MainScene scene;

    public KeyProcessingAction(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public State keyPressed(Widget widget, WidgetKeyEvent event) {
        logger.trace("widget: " + widget + " event: " + event.getKeyCode());
        logger.trace("KeyEvent.VK_DELETE: " + KeyEvent.VK_DELETE);
        
        if(event.getKeyCode() == KeyEvent.VK_DELETE){
//            List<Widget> selectedWidgets = scene.getSelectedWidgets();
            
            for( Widget w : scene.getSelectedWidgets()){
                logger.trace("Removing widget: " + w);
                w.getParentWidget().removeChild(w);
            }
        }
        
        if(event.getKeyCode() == KeyEvent.VK_CONTROL){
            logger.trace("Control pressed");
            scene.setControlPressed(true);
        }
        
        return State.REJECTED;
    }

    @Override
    public State keyReleased(Widget widget, WidgetKeyEvent event) {
        logger.trace("");
        
        if(event.getKeyCode() == KeyEvent.VK_CONTROL){
            logger.trace("Control released.");
            scene.setControlPressed(false);
        }
        return State.REJECTED;
    }
    
    
    
}
