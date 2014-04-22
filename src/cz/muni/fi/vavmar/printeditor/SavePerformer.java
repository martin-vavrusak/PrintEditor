package cz.muni.fi.vavmar.printeditor;

import java.awt.Font;
import java.awt.Point;
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
    	
    	//Save Print Format
    	MPrintFormat newPrintFormat = new MPrintFormat(Env.getCtx(), 0, null);
    	newPrintFormat.setName(printFormatName);											//this doesn't have to be uniqe across iDempiere system
    	newPrintFormat.setAD_Table_ID(dataProvider.getTableID(tableName));		
    	newPrintFormat.setAD_PrintColor_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_COLOR_ID );	//this should be 100 TODO - better to implement new method searching for default color dataProvider.getSystemDefaultColor
    	newPrintFormat.setAD_PrintPaper_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_PAPER_ID );	//This should be 100
    	newPrintFormat.setAD_PrintFont_ID( MainScene.DEFAULT_IDEMPIERE_PRINT_FONT_ID );		//This should be 100
    	newPrintFormat.setIsStandardHeaderFooter(false);
    	newPrintFormat.setIsForm(true);		//we need header and footer
    	
    	newPrintFormat.setHeaderMargin(scene.getPaperSettings().getHeaderMargin());
    	newPrintFormat.setFooterMargin(scene.getPaperSettings().getFooterMargin());
    	
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
    		saveItemDB(widget, newPrintFormatID, seqNo);
    		seqNo += 10;
    	}

	}
	
    
    private void saveItemDB(Widget widget, int formatID, int seqNo){
		/**
		 * 1. Get front from Widget - check if its present and save it
		 * 2. Get color - search if already exist and save it
		 * 3. create new PrintFormatItem
		 */
    	if(widget instanceof LabelWidget){
    		Font widgetFont = widget.getFont();
    		String labelText = ((LabelWidget) widget).getLabel();
    		Point widgetLocation = widget.getPreferredLocation();
    		
    		int fontID = saveFont(widgetFont);
    		logger.trace("Font: " + widgetFont + " found in db as ID: " + fontID);
    		
    		MPrintFormatItem item = new MPrintFormatItem(Env.getCtx(), 0, null);
    		item.setAD_PrintFont_ID(fontID);					//Mandatory
//    		item.setAD_PrintColor_ID(AD_PrintColor_ID);
    		item.setAD_PrintFormat_ID(formatID);					//Mandatory - id of format to belong to
    		item.setName(labelText);							//Mandatory name of printitem
    		item.setSeqNo(seqNo);
    		item.setXPosition(widgetLocation.x);
    		item.setYPosition(widgetLocation.y);
    		
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
    	//bude potreba udelat fontWrapper aby bylo možné pøetížit funkci equals()
    }
}

	
