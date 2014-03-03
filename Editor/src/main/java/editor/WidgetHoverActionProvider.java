/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.TwoStateHoverProvider;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class WidgetHoverActionProvider implements TwoStateHoverProvider {

     private final Logger logger = LogManager.getLogger(WidgetHoverActionProvider.class);
        
        //Copied from VisualLibrary Demo "ActionDemo"
        public void unsetHovering(Widget widget) {
            if (widget != null) {
                logger.trace( ((LabelWidget) widget).getLabel() );
                widget.setBackground (Color.RED);
                widget.setForeground (Color.BLACK);
            }
        }

        public void setHovering(Widget widget) {
            if (widget != null) {
                logger.trace( ((LabelWidget) widget).getLabel() );
                widget.setBackground (new Color (52, 124, 150));
                widget.setForeground (Color.GREEN);
            }
        }
    
}
