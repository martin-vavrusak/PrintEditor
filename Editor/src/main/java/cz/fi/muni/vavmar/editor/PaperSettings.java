/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  Contains setting of the page in the main scene.
 * 
 * @author Martin
 */
public class PaperSettings {
    private static final Logger logger = LogManager.getLogger(PaperSettings.class);
    
    private double ADJUSTMENT = 2.81;   //1pt in scene = 1/72" = 0,353mm   =>   1mm = 2,83pt)
    private boolean isLandscape = false;
    
    private int leftMargin;             //
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;
    
    private int headerMargin;       //margin from the top of paper
    private int footerMargin;
    
    private int width;              //absolute width of page
    private int height;

    /**
     * Margin from the left side of page in 1/72".
     * Usable area = width - leftMargin - RightMargin
     * @return 
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * Margin from the left side of page in 1/72".
     * 
     * Usable area = width - leftMargin - RightMargin
     * 
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    
    public int getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }
    
    /**
     * Margin height from the top side of page in 1/72".
     * Usable area =  pageHeight - topMargin - headerMargin - bottomMargin - footerMargin.
     * 
     * @return topMargin height of margin.
     */
    public int getTopMargin() {
        return topMargin;
    }

    /**
     * Height of top margin in 1/72".
     * @param topMargin height of top margin in 1/72"
     */
    public void setTopMargin(int topMargin) {
//        logger.trace("Top margin changed: ");
//        logger.trace("Original: " + this.topMargin);
//        logger.trace("New: " + topMargin);
        
        this.topMargin = topMargin;
    }

    /**
     * Margin height from the bootom side of page in 1/72".
     * Usable area =  pageHeight - topMargin - headerMargin - bottomMargin - footerMargin.
     * 
     * @return bottomMargin height of margin.
     */
    public int getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    /**
     * Margin height, relative to the top margin of a page.
     * The position is not taken from top of page but from position of topMargin.
     * 
     * Area of content will be: contentArea = pageHeight - topMargin - headerMargin - bottomMargin - footerMargin.
     * 
     * @return height of header beggining from topMargin of a page.
     */
    public int getHeaderMargin() {
        return headerMargin;
    }

    public void setHeaderMargin(int headerMargin) {
        this.headerMargin = headerMargin;
    }

    /**
     * Margin height, relative to the bottom margin of a page.
     * The position is not taken from bootom of a page directly, but from position of bottomMargin.
     * 
     * It means area of content will be: contentArea = pageHeight - topMargin - headerMargin - bottomMargin - footerMargin.
     * 
     * @return height of footer beggining from {@link bottomMargin} of a page.
     */
    public int getFooterMargin() {
        return footerMargin;
    }

    
    public void setFooterMargin(int footerMargin) {
        this.footerMargin = footerMargin;
    }

    /**
     * Returns scene width in mm. Is influenced by {@link isLadscape()}
     * @return scene width in mm.
     */
    public int getWidth() {
        if(isLandscape){
            return height;
        } else {
            return width;
        }
    }

    /**
     * Set scene width in mm.
     * @param width width of paper in mm.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns scene height in mm. Is influenced by {@link isLadscape()}
     * @return scene height in mm.
     */
    public int getHeight() {
        if(isLandscape){
            return width;
        } else {
            return height;
        }
    }

    /**
     * Set scene height in mm.
     * @param height height of paper in mm.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isIsLandscape() {
        return isLandscape;
    }

    public void setLandscape(boolean isLandscape) {
        this.isLandscape = isLandscape;
    }
    
    /**
     * Returns adjusted scene width in scene points (1/72").
     * ADJUSTMENT = 1pt in scene = 1/72" = 0,353mm   =>   1mm = 2,83pt)
     * Influenced by isLandscape option;
     * 
     * @return width * ADJUSTMENT (2,83)
     */
    public int getSceneWidth(){
        return (int) (getWidth() * ADJUSTMENT);
    }
    
    /**
     * Returns adjusted scene height in scene points (1/72").
     * ADJUSTMENT = 1pt in scene = 1/72" = 0,353mm   =>   1mm = 2,83pt)
     * Influenced by isLandscape option;
     * 
     * @return height * ADJUSTMENT (2,83)
     */
    public int getSceneHeight(){
        return (int) (getHeight() * ADJUSTMENT);
    }
    
    /**
     * Return absolute position in Y axis of top margin in scene (in scene pt units 1 pt = 1/72").
     * @return absolute position in Y axis of top margin in scene.
     */
    public int getTopMarginPosition(){
        return (int) (getTopMargin() * ADJUSTMENT);
    }
    
    public int getLeftMarginPosition(){
        return (int) (getLeftMargin() * ADJUSTMENT);
    }
    
    public int getRightMarginPosition(){
        return (int) ( (getWidth() - getRightMargin()) * ADJUSTMENT);
    }
    
    public int getBottomMarginRosition(){
        return (int) ( (getHeight() - getBottomMargin()) * ADJUSTMENT);
    }
    
    public static PaperSettings A4(){
        PaperSettings ps = new PaperSettings();
        ps.setHeaderMargin(50);
        ps.setFooterMargin(50);
        
        ps.setTopMargin(36);
        ps.setBottomMargin(36);
        ps.setLeftMargin(36);
        ps.setRightMargin(36);
        
        ps.setWidth(210);
        ps.setHeight(297);
        
        return ps;
    }

    @Override
    public String toString() {
        return "PaperSettings{" + "leftMargin=" + leftMargin + ", rightMargin=" + rightMargin + ", topMargin=" + topMargin + ", bottomMargin=" + bottomMargin + ", headerMargin=" + headerMargin + ", footerMargin=" + footerMargin + ", width=" + width + ", height=" + height + '}';
    }
}
