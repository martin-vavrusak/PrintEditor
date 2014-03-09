/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.utils;

import java.awt.Color;
import java.awt.Image;
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
        BufferedImage bi = (BufferedImage) img;
        
        
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
        BufferedImage bi = (BufferedImage) img;
        
        
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
