package cz.muni.fi.vavmar.printeditor;

import java.awt.Font;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.print.MPrintFormat;
import org.compiere.util.Env;
import org.netbeans.api.visual.widget.Widget;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;

public class SavePerformer {
	private static final Logger logger = LogManager.getLogger(SavePerformer.class);
	
	private DBManager dataProvider;
	private MainScene scene;
	private String tableName;
	private int printFormatID;
	
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
    	
    	logger.trace( "is new set: " + newPrintFormat.is_new() );
    	newPrintFormat.setReplication(true);
    	newPrintFormat.save();
    	
    	int newPrintFormatID = newPrintFormat.get_ID();
    	logger.trace( "New print format saved with ID: " +  newPrintFormatID);
    	
    	for(Widget widget: scene.getMainLayer().getChildren()){
//    		saveItemDB(widget, newPrintFormatID);
    	}
	}
	
    
    private void saveItemDB(Widget widget, int formatID){
		/**
		 * 1. Get front from Widget
		 * 2. Get color - search if already exist
		 */
    	Font widgetFont = widget.getFont();
//    	MPrintFont
	}
}

	
