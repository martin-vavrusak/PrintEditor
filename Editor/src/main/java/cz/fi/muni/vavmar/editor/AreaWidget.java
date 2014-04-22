/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.MoveStrategy;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class AreaWidget extends Widget{
    private static final Logger logger = LogManager.getLogger(AreaWidget.class);
    
    public static final int DEFAULT_THICKNESS = 1;
    public static final Color DEFAULT_COLOR = Color.BLACK;
    
    public static final int HEADER_TYPE = 1;
//    public static final int CONTENT_TYPE = 10;
    public static final int FOOTER_TYPE = 2;
    
    
    public static final int LEFT_MARGIN_TYPE = 100;
    public static final int RIGHT_MARGIN_TYPE = 200;
    public static final int TOP_MARGIN_TYPE = 300;
    public static final int BOTTOM_MARGIN_TYPE = 400;
    
    private MainScene scene;
    private int height;
    private int width;
    private int type = -1;
    
    private int thickness = DEFAULT_THICKNESS;
    private Color color = DEFAULT_COLOR;
    
    private PaperSettings paper;        //This varable is used for sharing among instances so any change among footer header and page settings will stay in sync
    
    private Rectangle area;
    
    /**
     * Default thickness = 2, default color = Black
     * 
     * Note: there is presumption that there will exist only one instance of each type
     * @param scene
     * @param paper
     * @param type 
     */
    public AreaWidget(MainScene scene, PaperSettings paper, int type) {
        this(scene, paper, type, Color.BLUE, DEFAULT_THICKNESS);        
    }
    
    public AreaWidget(MainScene scene, PaperSettings paper, int type, Color color, int thickness) {
        super(scene);
        logger.trace("Paper settings: " + paper);
        this.scene = scene;
        this.paper = paper;
        this.type = type;
        this.color = color;
        this.thickness = thickness;
        
        this.setOpaque(true);
        this.setForeground(color);
        this.setBackground(color);
        
//        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));          //doesn't work!!!
         
//        if(type == HEADER_TYPE || type == FOOTER_TYPE || type == TOP_MARGIN_TYPE || type == BOTTOM_MARGIN_TYPE){
//
////            this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
//            
//            if( type == HEADER_TYPE || type == FOOTER_TYPE ){
//                this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
//                this.getActions().addAction(ActionFactory.createMoveAction(new MoveStrategyArea(), new MoveProviderArea()));
//            }
//            
//        } else if (type == LEFT_MARGIN_TYPE || type == RIGHT_MARGIN_TYPE){
////            this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
//        }
        
        if( type == HEADER_TYPE || type == FOOTER_TYPE ){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
            this.getActions().addAction(ActionFactory.createMoveAction(new MoveStrategyArea(), new MoveProviderArea()));
        }
        
        revalidateChange();
    }
    
    public void setPaper(PaperSettings newPaper){
        this.paper = newPaper;
        revalidateChange();
    }

    public PaperSettings getPaper() {
        return paper;
    }

    
    /**
     * po zmene papiru provede revalidaci polohy
     */
    public final void revalidateChange(){
        if(type == HEADER_TYPE){
            setPreferredSize( new Dimension(paper.getSceneWidth() - paper.getLeftMargin() - paper.getRightMargin(), thickness) );
            setPreferredLocation( new Point(paper.getLeftMarginPosition(), paper.getTopMargin() + paper.getHeaderMargin()) );
            
        } else if( type == FOOTER_TYPE){
            setPreferredSize( new Dimension(paper.getSceneWidth() - paper.getLeftMargin() - paper.getRightMargin(), thickness) );
            setPreferredLocation( new Point(paper.getLeftMargin() , paper.getSceneHeight() - paper.getBottomMargin() - paper.getFooterMargin()) );
            
        } else if ( type == TOP_MARGIN_TYPE ){
            setPreferredSize( new Dimension(paper.getSceneWidth(), thickness));
            setPreferredLocation( new Point(0 , paper.getTopMargin()));
            
        } else if ( type == BOTTOM_MARGIN_TYPE ){
            setPreferredSize( new Dimension(paper.getSceneWidth(), thickness));
            setPreferredLocation( new Point(0 , paper.getSceneHeight() - paper.getBottomMargin()) );
            
        } else if ( type == LEFT_MARGIN_TYPE ){
            setPreferredSize( new Dimension( thickness, paper.getSceneHeight()));
            setPreferredLocation( new Point(paper.getLeftMargin() , 0) );
            
        } else if ( type == RIGHT_MARGIN_TYPE ){
            setPreferredSize( new Dimension( thickness, paper.getSceneHeight()));
            setPreferredLocation( new Point(paper.getSceneWidth() - paper.getRightMargin(), 0) );
        }
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    private class MoveProviderArea implements MoveProvider {
        private final Logger logger = LogManager.getLogger(MoveProviderArea.class);
        
        public void movementStarted(Widget widget) {
                logger.trace("");
        }

        public void movementFinished(Widget widget) {
            if(widget instanceof AreaWidget){
                AreaWidget area = (AreaWidget) widget;
                int areaType = area.getType();
                PaperSettings ps = area.getPaper();
                
                if( areaType == HEADER_TYPE ){
                    int height = widget.getPreferredLocation().y - ps.getTopMargin();
                    area.getPaper().setHeaderMargin(height);
                    
                } else if( areaType == FOOTER_TYPE ) {
                    int height = ps.getSceneHeight() - widget.getPreferredLocation().y - ps.getBottomMargin();
                    area.getPaper().setFooterMargin(height);
                }
                logger.trace("New paper:" + ps);
            } else {
                logger.warn("Somethink weird happend. Finished movement of uknown widget: " + widget);
            }
        }

        public Point getOriginalLocation(Widget widget) {
            logger.trace("");
            return widget.getPreferredLocation();
        }

        public void setNewLocation(Widget widget, Point location) {
            widget.setPreferredLocation(location);
        }
        
    }
    
    private class MoveStrategyArea implements MoveStrategy {
        private final Logger logger = LogManager.getLogger(MoveStrategyArea.class);
        
        public Point locationSuggested(Widget widget, Point originalLocation, Point suggestedLocation) {
            logger.trace("Widget: " + widget);
            
            if(widget instanceof AreaWidget){
                AreaWidget area = (AreaWidget) widget;
                int areaType = area.getType();
                PaperSettings ps = area.getPaper();
                
                int footerAbsolutePosition = ps.getSceneHeight() - ps.getBottomMargin() - ps.getFooterMargin();
                int headerAbsolutePosition = ps.getTopMargin() + ps.getHeaderMargin();
                logger.trace("suggested location Y: " + suggestedLocation.y);
                
                if( areaType == HEADER_TYPE || areaType == FOOTER_TYPE ){
                    int positionX = originalLocation.x;                                     //lock X axis
                    if(type == HEADER_TYPE){                                                //check if new position is between topMargin and footerMargin
                        if( suggestedLocation.y < ps.getTopMargin() ){                      //if we are above top margin block position up to topMargin
                            return new Point(positionX , ps.getTopMargin());                //this is ok 
                            
                        } else if ( footerAbsolutePosition < suggestedLocation.y) {           
                            return new Point(positionX, footerAbsolutePosition);              //need to compute location
                            
                        } else {
                            return new Point(positionX, suggestedLocation.y);
                        }
                        
                    } else if (type == FOOTER_TYPE){
                        logger.trace("Bottom margin position: " + ps.getBottomMarginRosition());
                        logger.trace("Header absolute position: " + headerAbsolutePosition);
                        if( suggestedLocation.y > ps.getBottomMarginRosition() ){                      //if we are below bottom margin -> block
                            return new Point(positionX , ps.getBottomMarginRosition());                //this is ok 
                            
                        } else if ( headerAbsolutePosition > suggestedLocation.y) {           
                            return new Point(positionX, headerAbsolutePosition);              //need to compute location
                            
                        } else {
                            return new Point(positionX, suggestedLocation.y);
                        }
                    }

                }   //end of HEADER and FOOTER
            }
            return suggestedLocation;
        }
        
    }
}
