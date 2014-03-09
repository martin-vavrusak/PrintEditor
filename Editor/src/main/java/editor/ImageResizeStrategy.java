/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import editor.utils.Utils;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.ResizeStrategy;
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

    Image originalImage;
    
    public ImageResizeStrategy() {
        provider = new MyResizeProvider();
    }
    
    
    public Rectangle boundsSuggested(Widget widget, Rectangle originalBounds, Rectangle suggestedBounds, ResizeProvider.ControlPoint controlPoint) {
        
        logger.trace("original bounds: " + originalBounds);
        logger.trace("suggested bounds: " + suggestedBounds);
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
        logger.trace("Border thickness: " + borderThickness);
        Image newImage = originalImage.getScaledInstance(suggestedBounds.width - (2*borderThickness), suggestedBounds.height - (2*borderThickness), Image.SCALE_SMOOTH);
        iw.setImage(newImage);
        return suggestedBounds;
    }

    public ResizeProvider getProvider(){
        return provider;
    }
    
    private class MyResizeProvider implements ResizeProvider {
        public void resizingStarted(Widget widget) {
            startedRectangle = widget.getBounds();
            logger.trace("startedRectangle: " + startedRectangle);
            originalImage = ((ImageWidget) widget).getImage();
        }

        public void resizingFinished(Widget widget) {
            logger.trace("startedRectangle: " + startedRectangle);
            
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
