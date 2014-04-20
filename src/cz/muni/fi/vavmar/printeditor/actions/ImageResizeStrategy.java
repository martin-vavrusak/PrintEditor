/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.ResizeStrategy;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * 
 * @author Martin
 */
public class ImageResizeStrategy implements ResizeStrategy {
    private static final Logger logger = LogManager.getLogger(ImageResizeStrategy.class);
    private Rectangle startedRectangle;
    MyResizeProvider provider;
    Point initialWidgetPosition;

    Image originalImage;
    
    public ImageResizeStrategy() {
        provider = new MyResizeProvider();
    }
    
    
    /**
     * Called repeately until the insets are dragged. 
     */
    public Rectangle boundsSuggested(Widget widget, Rectangle originalBounds, Rectangle suggestedBounds, ResizeProvider.ControlPoint controlPoint) {
        
        logger.trace("original bounds: " + originalBounds);
        logger.trace("suggested bounds: " + suggestedBounds);
        logger.trace("Widget location: " + widget.getPreferredLocation());
        ImageWidget iw = ((ImageWidget) widget);
//        BufferedImage bi = (BufferedImage) iw.getImage();
//        
//        Graphics2D graphics = (Graphics2D) bi.createGraphics();
//        AffineTransform at = AffineTransform.getScaleInstance( (suggestedBounds.x / originalBounds.x), (suggestedBounds.y / originalBounds.y));
//        
//        graphics.drawRenderedImage(bi, at);
//        
//        iw.setImage(bi);
//        iw.revalidate();
//        iw.getScene().revalidate();
        
        
        
//        BufferedImage newImage = Utils.resizeImageWithHint(bi, suggestedBounds.width, suggestedBounds.height, bi.getType());
//        BufferedImage newImage = Utils.scale(bi, bi.getType(),
//                                    suggestedBounds.width - widget.getBorder().getInsets().right,
//                                    suggestedBounds.height - widget.getBorder().getInsets().bottom, 1.0, 1.0);
//                                    //(suggestedBounds.width / originalBounds.width),
//                                    //(suggestedBounds.height / originalBounds.height));
//                                    
//        
//        iw.setImage(newImage);
//        iw.repaint();
        
        int borderThickness = widget.getBorder().getInsets().right;
        logger.trace("Border thickness: " + borderThickness + "ControlPoint: " + controlPoint);
        Image newImage = originalImage.getScaledInstance(suggestedBounds.width - (2*borderThickness),	//(2*borderThickness) need to stay inside of a border
        												suggestedBounds.height - (2*borderThickness),
        												Image.SCALE_SMOOTH);
        iw.setImage(newImage);
        
        //adjustment when some of left and top corners is dragged
        int dx = originalBounds.x - suggestedBounds.x;		//Original bounds are bound before dragging started
        int dy = originalBounds.y - suggestedBounds.y;
        Point p = iw.getPreferredLocation();
        
        logger.trace("dx: " + dx + " dy: " + dy + " suggestedBounds.width: " + suggestedBounds.width + " suggestedBounds.height: " + suggestedBounds.height);
        logger.trace("PrefferedLocation: " + p);

        iw.setPreferredBounds(new Rectangle(-borderThickness, -borderThickness, suggestedBounds.width, suggestedBounds.height));	//set new proportions 
        iw.setPreferredLocation(new Point( initialWidgetPosition.x - dx, initialWidgetPosition.y - dy));							//adjust location (if some of left and top corners is dragger), otherwise imge would inappropriately move
        
        return iw.getPreferredBounds();
    }

    public ResizeProvider getResizeProvider(){
        return provider;
    }
    
    /**
     * Need for saving initial image and initial position
     * 
     * @author Martin
     */
    private class MyResizeProvider implements ResizeProvider {
        public void resizingStarted(Widget widget) {
            startedRectangle = widget.getBounds();
            logger.trace("startedRectangle: " + startedRectangle);
            originalImage = ((ImageWidget) widget).getImage();
            initialWidgetPosition = widget.getPreferredLocation();
        }

        public void resizingFinished(Widget widget) {
            logger.trace("startedRectangle: " + startedRectangle);
            
//            //after resizin needs to clear space created for border.
//            Rectangle bounds = widget.getPreferredBounds();
//            int boundsThicknessX = bounds.x;
//            int boundsThicknessY = bounds.y;
//            
//            bounds.x += boundsThicknessX;
//            bounds.width -= (-1*boundsThicknessX);		//-1 in case thickness is negative (it should be)
//            		
//            bounds.y += boundsThicknessY;
//            bounds.height -= (-1*boundsThicknessY);
//            
//            widget.setPreferredBounds(bounds);
            
//            ImageWidget iw = ((ImageWidget) widget);
//            BufferedImage bi = (BufferedImage) iw.getImage();
//            Rectangle newBounds = iw.getBounds();
//            
//            
//            Image newImage = bi.getScaledInstance(newBounds.width, newBounds.height, Image.SCALE_SMOOTH);
//            
//            logger.trace("finalBounds: " + newBounds);
//            
////            BufferedImage newImage = Utils.scale(bi, bi.getType(),
////                                    newBounds.width ,
////                                    newBounds.height ,
////                                    (newBounds.width / startedRectangle.width),
////                                    (newBounds.height / startedRectangle.height));
////                                    
////        
//            iw.setImage(newImage);
////            iw.repaint();
        }
    }
}
