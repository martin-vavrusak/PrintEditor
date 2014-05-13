/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor.widgets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.ResizeStrategy;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class ImageWidgetWraper extends ImageWidget {

    private BufferedImage originalImage;
    private Rectangle startedRectangle;
    Point initialWidgetPosition;
    private WidgetAction resizeAction;
    
    public ImageWidgetWraper(Scene scene) {
        super(scene);
        resizeAction = ActionFactory.createResizeAction(new ImageResizeStrategy(), new ImageResizeProvider());
    }

    /**
     * Create new widget with origial imafe field. Used no to loose image quility when resizing.
     * @param scene
     * @param image 
     */
    public ImageWidgetWraper(Scene scene, BufferedImage image) {
        super(scene, image);
        originalImage = image;
        resizeAction = ActionFactory.createResizeAction(new ImageResizeStrategy(), new ImageResizeProvider());
    }

    public void activateResizeAction(boolean activate){
        if(activate){
            getActions().addAction(0, resizeAction);
        } else {
            getActions().removeAction(resizeAction);
        }
    }
    
    public void setOriginalImage( BufferedImage image ){
        originalImage = image;
        setImage(image);
    }
    
    @Override
    public void setImage(Image image) {
        if ( !(image instanceof BufferedImage) )
        {
            // Create a buffered image with transparency
            // http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
            BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
            super.setImage(image);
        }
        
        
        super.setImage(image);
    }
    
    
    
    private class ImageResizeProvider implements ResizeProvider{
        private final Logger logger = LogManager.getLogger(ImageResizeProvider.class);
        public void resizingStarted(Widget widget) {
            startedRectangle = widget.getPreferredBounds();
            logger.trace("startedRectangle preffered bounds: " + startedRectangle);
            initialWidgetPosition = widget.getPreferredLocation();
        }

        public void resizingFinished(Widget widget) {
            logger.trace("startedRectangle: " + startedRectangle);
            
//            //after resizin needs to clear space created for border.
//            Rectangle bounds = widget.getPreferredBounds();
//            int boundsThicknessX = bounds.x;
//            int boundsThicknessY = bounds.y;
//            
//            bounds.x -= boundsThicknessX;
////            bounds.width -= (-1*boundsThicknessX);		//-1 in case thickness is negative (it should be)
//            		
//            bounds.y -= boundsThicknessY;
////            bounds.height -= (-1*boundsThicknessY);
//            
//            widget.setPreferredBounds(bounds);
//
//            
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
    
    public class ImageResizeStrategy implements ResizeStrategy {
    private final Logger logger = LogManager.getLogger(ImageResizeStrategy.class);
    
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
        Image newImage = originalImage.getScaledInstance(suggestedBounds.width - (2*borderThickness), suggestedBounds.height - (2*borderThickness), Image.SCALE_SMOOTH);
        iw.setImage(newImage);
        
        int dx = originalBounds.x - suggestedBounds.x;
        int dy = originalBounds.y - suggestedBounds.y;
        Point p = iw.getPreferredLocation();
        
        logger.trace("dx: " + dx + " dy: " + dy + " suggestedBounds.width: " + suggestedBounds.width + " suggestedBounds.height: " + suggestedBounds.height);
        logger.trace("PrefferedLocation: " + p);

        iw.setPreferredBounds(new Rectangle(-borderThickness, -borderThickness, suggestedBounds.width, suggestedBounds.height));
        iw.setPreferredLocation(new Point( initialWidgetPosition.x - dx, initialWidgetPosition.y - dy));
        
        return iw.getPreferredBounds();
    }
    }
}
