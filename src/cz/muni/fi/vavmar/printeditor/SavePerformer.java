package cz.muni.fi.vavmar.printeditor;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.print.MPrintFont;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.print.PrintFormatUtil;
import org.compiere.util.Env;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;

public class SavePerformer {
	private static final Logger logger = LogManager.getLogger(SavePerformer.class);
	
	private DBManager dataProvider;
	private MainScene scene;
	private String tableName;
	private int printFormatID;
	private int seqNo = 0;
	
	private List<MPrintFont> databaseFonts;
	
	
	/**
	 * From scene create new print format and save. Loads data from database for checking of existing colors, fonts, formats....
	 * 
	 * @param dataProvider data provider used for database connection
	 * @param scene	scene to be saved
	 * @param tableName name of existing table in iDempiere. Mustn't be null.
	 * @param printFormatID id of existing print format. If -1 set than new format will be created for table.
	 */
	public SavePerformer(DBManager dataProvider, MainScene scene,
			String tableName, int printFormatID) {
		super();
		this.dataProvider = dataProvider;
		this.scene = scene;
		this.tableName = tableName;
		this.printFormatID = printFormatID;
		
		loadFonts();
	}
	
	
	public void saveAsNew(String printFormatName){
    	logger.debug("Saving scene as: '" + printFormatName + "' for table: '" + tableName + "'.");
    	
    	//Compute Header, Fotter and Content area:
    	PaperSettings paper = scene.getPaperSettings();
    	
    	paper.getTopMarginPosition();	//Y of left top corner of header margin
    	
    	int leftX = paper.getLeftMarginPosition();			//left X point of all areas.
    	int rightX = paper.getRightMarginPosition();		//right X point of all areas
    	int areaWidth = rightX - leftX;						//width of usable area between left and right margin this can be used for content
    	int contentHeight = paper.getSceneHeight() - paper.getTopMargin() - paper.getHeaderHeight() - paper.getBottomMargin() - paper.getFooterHeight();
    	
    	
    	Rectangle headerArea = new Rectangle(leftX, paper.getTopMarginPosition(),			//Left upper corner of header
    											areaWidth, paper.getHeaderHeight());		//header margin is width relative to topMargin
    	
    	Rectangle contentArea = new Rectangle(leftX, paper.getHeaderHeight() + paper.getTopMarginPosition(),	//it's in absolute scene units
    												areaWidth, contentHeight);
    	
    	Rectangle footerArea = new Rectangle(leftX, paper.getBottomMarginRosition() - paper.getFooterHeight(),
    												areaWidth, paper.getFooterHeight());
    	
    	//Save Print Format
    	MPrintFormat newPrintFormat = new MPrintFormat(Env.getCtx(), 0, null);
    	newPrintFormat.setName(printFormatName);											//this doesn't have to be uniqe across iDempiere system
    	newPrintFormat.setAD_Table_ID(dataProvider.getTableID(tableName));		
    	newPrintFormat.setAD_PrintColor_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_COLOR_ID );	//this should be 100 TODO - better to implement new method searching for default color dataProvider.getSystemDefaultColor
    	newPrintFormat.setAD_PrintPaper_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_PAPER_ID );	//This should be 100
    	newPrintFormat.setAD_PrintFont_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_FONT_ID );		//This should be 100
    	newPrintFormat.setIsStandardHeaderFooter(false);
    	newPrintFormat.setIsForm(true);		//we need header and footer
    	
    	newPrintFormat.setHeaderMargin(scene.getPaperSettings().getHeaderHeight());
    	newPrintFormat.setFooterMargin(scene.getPaperSettings().getFooterHeight());
    	
    	
    	logger.trace( "is new set: " + newPrintFormat.is_new() );
//    	newPrintFormat.setReplication(true);
    	if( !newPrintFormat.save() ){
    		logger.error("Error during saving print format: " + newPrintFormat);
    		return;
    	}
    	
    	int newPrintFormatID = newPrintFormat.get_ID();										//after succesfull save we have valid ID of newly created object
    	logger.trace( "New print format saved with ID: " +  newPrintFormatID);

    	seqNo = 0;
    	for(Widget widget: scene.getMainLayer().getChildren()){
    		saveItemDB(widget, newPrintFormatID, seqNo, headerArea, contentArea, footerArea);
    		seqNo += 10;
    	}

	}
	
    
    private void saveItemDB(Widget widget, int formatID, int seqNo, Rectangle headerArea, Rectangle contentArea, Rectangle footerArea){
    	//determine location
    	MPrintFormatItem item = new MPrintFormatItem(Env.getCtx(), 0, null);
    	Point widgetAbsolutePosition;
    	if(widget instanceof LabelWidget){							//In case of label widget there need to be done adjustment. 
    		Point loc = widget.getPreferredLocation();  			//location of left top corner is moved
    		int y = Math.abs( loc.y - Math.abs(widget.getPreferredBounds().y) ) ;
    		int x = loc.x;
    		widgetAbsolutePosition = new Point( x,y ); 
    	} else {
    		widgetAbsolutePosition = widget.getPreferredLocation();
    	}
    	
    	logger.trace("Widget: " + widget);
    	logger.trace(" has position: " + widgetAbsolutePosition);
    	logger.trace("Header: " + headerArea);
    	logger.trace("Content: " + contentArea);
    	logger.trace("Footer: " + footerArea);
    	
//    	int x = widgetAbsolutePosition.x;
    	
    	
    	if( headerArea.contains(widgetAbsolutePosition) ){
    		logger.trace("Item is in header.");
    		item.setPrintAreaType("H");
    		
    		//Determine location in area
    		int x = widgetAbsolutePosition.x - headerArea.x;
    		int y = widgetAbsolutePosition.y - headerArea.y;
    		item.setXPosition(x);
    		item.setYPosition(y);
    		logger.trace("Final position: x=" + x + ", y=" + y);
    		
    	} else if ( contentArea.contains(widgetAbsolutePosition) ){
    		logger.trace("Item is in content.");
    		item.setPrintAreaType("C");
    		
    		//Determine location in area
    		int x = widgetAbsolutePosition.x - contentArea.x;
    		int y = widgetAbsolutePosition.y - contentArea.y;
    		item.setXPosition(x);
    		item.setYPosition(y);
    		logger.trace("Final position: x=" + x + ", y=" + y);
    		
    	} else if ( footerArea.contains(widgetAbsolutePosition) ) {
    		logger.trace("Item is in footer.");
    		item.setPrintAreaType("F");
    		
    		//Determine location in area
    		int x = widgetAbsolutePosition.x - footerArea.x;
    		int y = widgetAbsolutePosition.y - footerArea.y;
    		item.setXPosition(x);
    		item.setYPosition(y);
    		logger.trace("Final position: x=" + x + ", y=" + y);
    		
		} else {
			logger.warn("Unable to determine location of item: " + widget + " with location: " + widget.getPreferredLocation());
			logger.warn("Header: " + headerArea + " Content: " + contentArea + " Footer: " + footerArea);
		}
    	
		/**
		 * 1. Get front from Widget - check if its present and save it
		 * 2. Get color - search if already exist and save it
		 * 3. create new PrintFormatItem
		 */
    	if(widget instanceof LabelWidget){
    		Font widgetFont = widget.getFont();
    		String labelText = ((LabelWidget) widget).getLabel();
    		
    		int fontID = saveFont(widgetFont);
    		logger.trace("Font: " + widgetFont + " found in db as ID: " + fontID);
    		
    		
    		item.setAD_PrintFont_ID(fontID);					//Mandatory
//    		item.setAD_PrintColor_ID(AD_PrintColor_ID);
    		item.setAD_PrintFormat_ID(formatID);					//Mandatory - id of format to belong to
    		item.setName(labelText);							//Mandatory name of printitem
    		item.setSeqNo(seqNo);
    		
    		item.setPrintName(labelText);						//text of label
    		item.setIsRelativePosition(false);
    		item.setSortNo(0);									//we don't need sorting
    		item.setLineAlignmentType("X");						//we don't need aligment
    		item.setPrintFormatType("T");						//T - text, I - image, R - rectange, L - line, F - DB field
    		
//    		item.setPrintAreaType("H");							//Header, Content (default), Footer
    		if ( !item.save() ){
    			logger.error("Error whne sawing: " + item);
    		}
    		logger.trace("Item saved with ID: " + item.get_ID());
    		
    	} else if(widget instanceof ImageWidget){
    		
    		
    	}
    	

	}
    
    /**
     * If font is not in the DB create font and save it to DB. Otherwise return id of matching font.
     * 
     * @param f font to be saved;
     * @return	id of matching or newly created font.
     */
    private int saveFont(Font saveFont){
    	
    	for(MPrintFont iDempiereFont : databaseFonts){
    		Font f =  iDempiereFont.getFont();
    		logger.trace("Testing font: " + f + "and : " + saveFont);
    		
    		if( f.getFamily().equalsIgnoreCase(saveFont.getFamily()) 
    				&& f.getSize() == saveFont.getSize()
    				&& f.getStyle() == saveFont.getStyle() ){
    			
    			logger.trace("Matching fount found: " + f + " for widget font: " + saveFont);
    			return iDempiereFont.get_ID();
    		}
    	}
    	
    	logger.trace("No matching font found saving font: " + saveFont);
    	
    	//if not corresponding font found create new
    	int fontID = dataProvider.createFont(saveFont);
    	
    	//add it to the list
    	MPrintFont newFont = dataProvider.loadFont( fontID ) ; 
    	databaseFonts.add( newFont );
    	
    	logger.trace("Font saved with ID: " + fontID);
    	return fontID;
    }
    
    
    private void loadFonts(){
    	databaseFonts = dataProvider.getFonts();    	
    	//bude potreba udelat fontWrapper aby bylo mo�n� p�et�it funkci equals()
    }
}

	