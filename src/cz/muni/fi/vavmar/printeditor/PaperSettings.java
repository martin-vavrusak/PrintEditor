/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor;

import java.awt.PageAttributes.MediaType;

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.standard.MediaName;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

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
    
    private int headerMargin = 0;       //margin from the top of paper
    private int footerMargin = 0;
    
    private double width;              //absolute width of page in mm
    private double height;

    private String code;			//IF code is set ignore width and height!!
    private String units;			// ONLY MM ARE SUPPORTED YET!!!!
    private String name;
    private String description;		
    private int paperID = -1;
    
    private PaperSettings.MediaSizeNameWrapper standardFormatSearch = new PaperSettings.MediaSizeNameWrapper(0);
    
    public void setMargins(int top, int left, int right, int bottom){
    	setTopMargin(top);
    	setLeftMargin(left);
    	setRightMargin(right);
    	setBottomMargin(bottom);
    }
    
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
    public int getHeaderHeight() {
        return headerMargin;
    }

    public void setHeaderHeight(int headerMargin) {
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
    public int getFooterHeight() {
        return footerMargin;
    }

    
    public void setFooterHeight(int footerMargin) {
        this.footerMargin = footerMargin;
    }

    /**
     * Returns scene width in mm. Is influenced by {@link isLadscape()}
     * If valid code is set, then vales set by setWidth(), setHeight() are ignored and value defined by MediaSize belonging to code is returned.
     * For codes see source of {@link MediaSizeName} getStringTable().
     * 
     * @return scene width in mm.
     */
    public double getWidth() {
    	if(code != null){
    		MediaSize mediaSize = standardFormatSearch.getMedia(code);
    		if( mediaSize != null ){
    			if(isLandscape){
    				return mediaSize.getY(MediaSize.MM);
    			} else{
    				return mediaSize.getX(MediaSize.MM);
    			}
    		}
    	}

//    	if("custom".equals(code)){
	        if(isLandscape){
	            return height;
	        } else {
	            return width;
	        }
//    	} else {
//    		//find settings from ISO defaults
//    	}
    }

    /**
     * Set scene width in mm. For exmple value of A4 would be 210.
     * <b>NOTE:</b> If valid code is set, then vales set by setWidth(), setHeight() are ignored.
     * 
     * @param width width of paper in mm.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns scene height in mm. Is influenced by {@link isLadscape()}
     * If valid code is set, then vales set by setWidth(), setHeight() are ignored and value defined by MediaSize belonging to code is returned.
     * For codes see source of {@link MediaSizeName} getStringTable().
     * 
     * @return scene height in mm.
     */
    public double getHeight() {
    	if(code != null){
    		MediaSize mediaSize = standardFormatSearch.getMedia(code);
    		if( mediaSize != null ){
    			if(isLandscape){
    				return mediaSize.getX(MediaSize.MM);
    			} else{
    				return mediaSize.getY(MediaSize.MM);
    			}
    		}
    	}
    	
        if(isLandscape){
            return width;
        } else {
            return height;
        }
    }

    /**
     * Set scene height in mm. For exmple value of A4 would be 297.
     * <b>NOTE:</b> If valid code is set, then vales set by setWidth(), setHeight() are ignored.
     * @param height height of paper in mm.
     */
    public void setHeight(double height) {
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
        return (int) (getTopMargin());
    }
    
    /**
     * Return absolute position in X axis of left margin in scene (in scene pt units, 1 pt = 1/72").
     * @return absolute position in X axis of left margin in scene.
     */
    public int getLeftMarginPosition(){
        return (int) (getLeftMargin());
    }
    
    /**
     * Return absolute position in X axis of right margin in scene (in scene pt units, 1 pt = 1/72").
     * @return absolute position in X axis of right margin in scene.
     */
    public int getRightMarginPosition(){
        return (int) ( (getSceneWidth() - getRightMargin()) );
    }
    
    /**
     * Return absolute position in Y axis of bottom margin in scene (in scene pt units 1 pt = 1/72").
     * @return absolute position in Y axis of bottom margin in scene.
     */
    public int getBottomMarginRosition(){
        return (int) ( (getSceneHeight() - getBottomMargin()) );
    }
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getPaperID() {
		return paperID;
	}

	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public boolean isLandscape() {
		return isLandscape;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
    public static PaperSettings A4(){
        PaperSettings ps = new PaperSettings();
        ps.setHeaderHeight(50);
        ps.setFooterHeight(50);
        
        ps.setTopMargin(36);
        ps.setBottomMargin(36);
        ps.setLeftMargin(36);
        ps.setRightMargin(36);
        
        ps.setCode("iso-a4");
        ps.setName("iDempiere A4 standard");
        ps.setPaperID(103);
        
        ps.setWidth(210);
        ps.setHeight(297);
        
        return ps;
    }

    @Override
    public String toString() {
        return "PaperSettings{" + "leftMargin=" + leftMargin + ", rightMargin=" + rightMargin + ", topMargin=" + topMargin + ", bottomMargin=" + bottomMargin + ", headerMargin=" + headerMargin + ", footerMargin=" + footerMargin + ", width=" + width + ", height=" + height + '}';
    }
    
    /**
     * Returns MediaSize object representing standardized paper specified by a code.
     * For codes see source of {@link MediaSizeName} getStringTable()
     * 
     * @param paperCode code defined in {@link MediaSizeName} getStringTable().
     * @return MediaSize object representing paper scale or null.
     */
    public MediaSize getMediaSize(String paperCode){
    	return  standardFormatSearch.getMedia(paperCode);
    }

    //Workaround to for searching for standardized paper settings
    public static class MediaSizeNameWrapper extends MediaSizeName {

		protected MediaSizeNameWrapper(int value) {
			super(value);
			// TODO Auto-generated constructor stub
		}
    	
		public String[] getStringTable(){
			return super.getStringTable();
		}
		
		/**
	     * Returns MediaSize object representing standardized paper specified by a code.
	     * For codes see source of {@link MediaSizeName} getStringTable()
	     * 
	     * @param paperCode code defined in {@link MediaSizeName} getStringTable().
	     * @return MediaSize object representing paper scale or null.
	     */
		public MediaSize getMedia (String mediaCode){
			if(mediaCode == null) return null;
			
			final String[] paperNames = getStringTable();
			
			int counter = 0;
			for(String name: paperNames){
				if ( name.equalsIgnoreCase(mediaCode) ){
					MediaSizeName mediaSizeName = new MediaSizeNameWrapper(counter);

					return MediaSize.getMediaSizeForName( (MediaSizeName) getEnumValueTable()[counter] );
				}
				counter++;
			}
			return null;
		}
    }
}
