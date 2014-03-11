/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.MouseHoverAction;
import org.netbeans.modules.visual.action.SelectAction;

/**
 *
 * @author Martin
 */
public class Utils {
    public static final Logger logger = LogManager.getLogger(Utils.class);
    
    public static void setMultiMoveAction(Widget widget, WidgetAction action){
               List<WidgetAction> actions = widget.getActions().getActions();
        if(actions.size() >= 2){
            WidgetAction firstAction = actions.get(0);
            WidgetAction secondAction = actions.get(1);
            if( (firstAction instanceof SelectAction || firstAction instanceof MouseHoverAction) ||
                (secondAction instanceof SelectAction || secondAction instanceof MouseHoverAction)  ){
                
                if(actions.size() > 2){
                    widget.getActions().addAction(2, action);
                    return;
                } else {
                    widget.getActions().addAction(action);
                    return;
                }
            }
        }
        
        
        if(actions.size() >= 1) {
            WidgetAction widgetAction = actions.get(0);
            if( widgetAction instanceof SelectAction || widgetAction instanceof MouseHoverAction ) {
                if(actions.size() > 1){
                    widget.getActions().addAction(1, action);
                    return;
                } else {
                    widget.getActions().addAction(action);
                    return;
                }
                
            } else {    //there is no action
                widget.getActions().addAction(0, action);
                return;
            }
        } else { //there is no action
            widget.getActions().addAction(action);
        }
        
//        if( actions.size() > 1 && actions.get(0) instanceof SelectAction ){ //jestlize widget ma vice nez 1 akci a prvni je SelectAction
//            widget.getActions().addAction(1, action);    //vloz movement hned za select
//
//        } else if ( actions.size() == 1 && actions.get(0) instanceof SelectAction ){
//            widget.getActions().addAction(action);
//        } else {
//            widget.getActions().addAction(0, action);
//        }
    }
    
    public static ListModel createListModel(final List<String> list){
        if(list == null | list.size() <= 0) {
            return new DefaultListModel();
        }
        
        return new AbstractListModel() {
            
            public int getSize() {
                return list.size();
            }

            public Object getElementAt(int index) {
                return list.get(index);
            }
        };
    }
    
    
    /**
     * Make image darker
     * @param img
     * @return 
     */
    public static Image darkerImage (Image img){
//        Graphics imageGraphic = img.getGraphics();
        BufferedImage bi = createBufferedImage(img);
        
        
        int height = bi.getHeight();
        int width = bi.getWidth();
        logger.trace("Image height: " + height + " width: " + width);
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int rgb = bi.getRGB(j, i);
                Color color = new Color( rgb );
                
                bi.setRGB( j, i, color.darker().getRGB() );
            }
        }
        
        return bi;
    }
    
    public static Image brighterImage (Image img){
//        Graphics imageGraphic = img.getGraphics();
        BufferedImage bi = createBufferedImage(img);
        
        
        int height = bi.getHeight();
        int width = bi.getWidth();
        logger.trace("Image height: " + height + " width: " + width);
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int rgb = bi.getRGB(j, i);
                Color color = new Color( rgb );
                
                bi.setRGB( j, i, color.brighter().getRGB());
            }
        }
        
        return bi;
    }
    
    
    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int newWidth, int newHight, int type){
 
	BufferedImage resizedImage = new BufferedImage(newWidth, newHight, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, newWidth, newHight, null);
	g.dispose();	
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
 
        
	return resizedImage;
    }
    
    /**
 * scale image
 * 
 * @param sbi image to scale
 * @param imageType type of image
 * @param dWidth width of destination image
 * @param dHeight height of destination image
 * @param fWidth x-factor for transformation / scaling
 * @param fHeight y-factor for transformation / scaling
 * @return scaled image
 */
public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
    BufferedImage dbi = null;
    if(sbi != null) {
        dbi = new BufferedImage(dWidth, dHeight, imageType);
        Graphics2D g = dbi.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
        g.drawRenderedImage(sbi, at);
    }
    return dbi;
}

public static BufferedImage createBufferedImage(Image img){
     if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
}

//    private static int getRGBA (int r, int g, int b, int a){
//        return (a << 24) | (r << 16) | (g << 8) | b;
//    }
//    
//    private static int[] getRGBA (int compoundColor){
//        int[] rGBA = new int[4];
//        
//        return rGBA;
//    }
//    }
}
