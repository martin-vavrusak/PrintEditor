/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.actions;

import cz.fi.muni.vavmar.editor.utils.Utils;
import java.awt.Color;
import java.awt.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.TwoStateHoverProvider;
import org.netbeans.api.visual.widget.ImageWidget;
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
                if(widget instanceof ImageWidget){
                    ImageWidget iw = (ImageWidget) widget;
                    Image image = iw.getImage();
                    
                    Image darkerImage = Utils.brighterImage(image);
                    
                    iw.setImage(darkerImage);
                    widget.repaint();
                    
                } else if(widget instanceof LabelWidget){
                    logger.trace( ((LabelWidget) widget).getLabel() );
                    widget.setBackground (Color.WHITE);
                    widget.setForeground (Color.BLACK);
                    logger.trace( ((LabelWidget) widget).getLabel() );
                    
                } else {
                    logger.warn("Unrecognizet widget: " + widget);
                }

            }
        }

        public void setHovering(Widget widget) {
            if (widget != null) {
                logger.trace("Preffered bounds: " + widget.getPreferredBounds());
                logger.trace("Bounds: " + widget.getBounds());
                
                if(widget instanceof ImageWidget){
                    ImageWidget iw = (ImageWidget) widget;
                    Image image = iw.getImage();
                    
                    Image darkerImage = Utils.darkerImage(image);
                    
                    iw.setImage(darkerImage);
                    widget.repaint();
                    
                } else if(widget instanceof LabelWidget){
                    logger.trace( ((LabelWidget) widget).getLabel() );
                    widget.setBackground (Color.GRAY);
                    widget.setForeground (Color.WHITE);
                    
                    
                } else {
                    logger.warn("Unrecognizet widget: " + widget);
                }
                
            }
        }
    
}
