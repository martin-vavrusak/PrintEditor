package cz.muni.fi.vavmar.reporteditor;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.model.MAttachment;
import org.compiere.print.MPrintFont;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.print.PrintFormatUtil;
import org.compiere.util.Env;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

import cz.muni.fi.vavmar.reporteditor.DAO.DBManager;
import cz.muni.fi.vavmar.reporteditor.widgets.SubreportWidget;

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
		
		loadFonts();	//load fonts to preventing subsequent calls to database
	}
	
	
	public void saveAsNew(String printFormatName){
    	logger.debug("Saving scene as: '" + printFormatName + "' for table: '" + tableName + "'.");
    	
    	int tableID = dataProvider.getTableID(tableName);
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
    	
    	
    	//Save PageSettings
    	int paperSettingsID = paper.getPaperID();
    	if(paperSettingsID < 0){
    		paperSettingsID = 103;		//fallback of standard iDempiere paper
    	}
    	
    	//Save Print Format
    	MPrintFormat newPrintFormat = new MPrintFormat(Env.getCtx(), 0, null);
    	newPrintFormat.setName(printFormatName);											//this doesn't have to be uniqe across iDempiere system
    	newPrintFormat.setAD_Table_ID( tableID );		
    	newPrintFormat.setAD_PrintColor_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_COLOR_ID );	//this should be 100 TODO - better to implement new method searching for default color dataProvider.getSystemDefaultColor
//    	newPrintFormat.setAD_PrintPaper_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_PAPER_ID );	//This should be 100
    	newPrintFormat.setAD_PrintFont_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_FONT_ID );		//This should be 100
    	newPrintFormat.setIsStandardHeaderFooter(false);
    	newPrintFormat.setIsForm(true);		//we need header and footer
    	
    	newPrintFormat.setHeaderMargin(scene.getPaperSettings().getHeaderHeight());
    	newPrintFormat.setFooterMargin(scene.getPaperSettings().getFooterHeight());
    	newPrintFormat.setAD_PrintPaper_ID(paperSettingsID);
    	
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
    		saveItemDB(widget, tableID, newPrintFormatID, seqNo, headerArea, contentArea, footerArea);
    		seqNo += 10;
    	}

	}
	
    
    private void saveItemDB(Widget widget, int tableID, int formatID, int seqNo, Rectangle headerArea, Rectangle contentArea, Rectangle footerArea){
    	//determine location
    	MPrintFormatItem item = new MPrintFormatItem(Env.getCtx(), 0, null);
    	item.setAD_PrintFormat_ID(formatID);				//Mandatory
    	
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
    	
    	int itemPositionX = 0;
    	int itemPositionY = 0;
    	
    	//Determine area where item belongs to
    	if( headerArea.contains(widgetAbsolutePosition) ){
    		logger.trace("Item is in header.");
    		item.setPrintAreaType("H");
    		
    		//Determine location in area
    		itemPositionX = widgetAbsolutePosition.x - headerArea.x;
    		itemPositionY = widgetAbsolutePosition.y - headerArea.y;

    	} else if ( contentArea.contains(widgetAbsolutePosition) ){
    		logger.trace("Item is in content.");
    		item.setPrintAreaType("C");
    		
    		//Determine location in area
    		itemPositionX = widgetAbsolutePosition.x - contentArea.x;
    		itemPositionY = widgetAbsolutePosition.y - contentArea.y;
    		
    	} else if ( footerArea.contains(widgetAbsolutePosition) ) {
    		logger.trace("Item is in footer.");
    		item.setPrintAreaType("F");
    		
    		//Determine location in area
    		itemPositionX = widgetAbsolutePosition.x - footerArea.x;
    		itemPositionY = widgetAbsolutePosition.y - footerArea.y;
    		
		} else {
			logger.warn("Unable to determine location of item: " + widget + " with location: " + widget.getPreferredLocation());
			logger.warn("Header: " + headerArea + " Content: " + contentArea + " Footer: " + footerArea);
//			return;
		}
    	
    	
    	item.setXPosition(itemPositionX);
		item.setYPosition(itemPositionY);
		logger.trace("Final position: x=" + itemPositionX + ", y=" + itemPositionY);
    	
		/**
		 * 1. Get front from Widget - check if it's present in system and save it if not
		 * 2. Get color - search if already exist and save it
		 * 3. create new PrintFormatItem
		 */
    	if(widget instanceof SubreportWidget){
    		SubreportWidget subreportWidget = (SubreportWidget) widget;
//    		item.setAD_PrintFormat_ID(formatID);				//Mandatory - id of format to belong to
    		item.setName(subreportWidget.getName());			//Mandatory name of printitem
    		item.setSeqNo(seqNo);
    		
    		item.setPrintName(subreportWidget.getName());		//has no effect in subreport type
    		item.setPrintFormatType("P");
    		item.setAD_PrintFormatChild_ID(subreportWidget.getSubreportPrintformatID());
    		item.setAD_Column_ID(11475);						//Magic constant of AD_Client_ID column - there has to be something filled, but probably doesn't have any effect
    		
    		if(subreportWidget.isIgnorePosition()){				//in case reset position is set set 0,0 and relative positioning
    			item.setXSpace(0);
    			item.setYSpace(0);
    			item.setXPosition(0);
    			item.setYPosition(0);
    			item.setIsRelativePosition(true);
    			
    		} else {
				
	    		if( subreportWidget.isRelativePositioned() ){
	    			item.setIsRelativePosition(true);
	    			item.setXSpace(itemPositionX);
	    			item.setYSpace(itemPositionY);
	    			
	    		} else {
	    			item.setIsRelativePosition(false);				//the psition has been previously set
	    		}
    		}
    		
    		if ( !item.save() ){
    			logger.error("Error whne sawing: " + item);
    		}
    		logger.trace("Item saved with ID: " + item.get_ID());
    		
    	} else if(widget instanceof LabelWidget){
    		
    		Font widgetFont = widget.getFont();
    		String labelText = ((LabelWidget) widget).getLabel();
    		logger.trace("Saving label: " + labelText);
    		
    		int fontID = saveFont(widgetFont);
    		logger.trace("Font: " + widgetFont + " found in db as ID: " + fontID);
    		
    		
    		//Set color
    		int colorID = dataProvider.ceckAndCreateColor( widget.getForeground() );
    		if(colorID > 0){
    			item.setAD_PrintColor_ID(colorID);
    		}
    		
    		item.setAD_PrintFont_ID(fontID);					//Mandatory
//    		item.setAD_PrintFormat_ID(formatID);				//Mandatory - id of format to belong to
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
    		
    	} else if(widget instanceof ImageWidget){				//for transferability reasons we'll save image as a attachement
    		logger.trace("Saving image.");						//for future extensions itcould be extended to allowing just passing location as http protocol location
    															//but there would have to be implemented support in editor first (downloading image)
    		
//    		item.setAD_PrintFormat_ID(formatID);				//Mandatory
    		item.setName("Image");								//Mandatory
    		item.setSeqNo(seqNo);
    		
    		item.setIsRelativePosition(false);
    		item.setPrintFormatType("I");
    		//Set printformatitem for image
    		ImageWidget imageWidget = ((ImageWidget) widget);
    		Rectangle imageBounds = imageWidget.getPreferredBounds();
    		item.setMaxWidth(imageBounds.width);
    		item.setMaxHeight(imageBounds.height);
    		item.setImageIsAttached(true);
    		
    		if ( !item.save() ){
    			logger.error("Error when sawing: " + item);
    		}
    		logger.trace("Item for image saved with ID: " + item.get_ID());
    		logger.trace("Saving attachement.");
    		
    		//Save Image as attachement
    		BufferedImage bufferedImage = (BufferedImage) imageWidget.getImage();
//    		WritableRaster raster = bufferedImage.getRaster();
//    		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
    		
    		MAttachment	attachement = new MAttachment(Env.getCtx(), 0, null);
    		attachement.setAD_Table_ID( MPrintFormatItem.Table_ID );					//All attachements are attached to this table not to the table we are working with!!!
    		attachement.setRecord_ID( item.get_ID() );
    		

//    		attachement.setBinaryData(data.getData());								//
//    		attachement.addEntry("Obrazek.gif", data.getData());					//Temer, akorat se kousne pri nahravani
    		
//    		String tempDir = System.getProperty("java.io.tmpdir");
//    		logger.trace("Tempdir: " + tempDir);
    		String tempFileName = "iDempiere_img";
    		File tempFile = null;
    		try {
				tempFile = File.createTempFile(tempFileName, ".gif");
				logger.trace("Temporary file: " + tempFile);
				ImageIO.write(bufferedImage, "gif", tempFile);
				logger.trace("Image saved temporary: " + tempFile.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
//    		//Jeste moznost zkusit tohle:
    		attachement.addEntry( tempFile );
    		
    		attachement.setTextMsg("Obrazek");
    		attachement.setTitle("zip");									//Mandatory
    		
    		if ( !attachement.save() ){
    			logger.error("Error when sawing attachement.");
    		}

    		logger.trace("New Attachement saved with id: " + attachement.get_ID());
    		
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
    	//bude potreba udelat fontWrapper aby bylo možné pøetížit funkci equals()
    }
}

	
