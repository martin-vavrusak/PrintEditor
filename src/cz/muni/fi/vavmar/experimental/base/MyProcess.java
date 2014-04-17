package cz.muni.fi.vavmar.experimental.base;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MColor;
import org.compiere.model.MColumn;
import org.compiere.model.MRole;
import org.compiere.model.MTable;
import org.compiere.model.MTableAccess;
import org.compiere.model.MUser;
import org.compiere.model.X_AD_Table;
import org.compiere.print.MPrintColor;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.vavmar.printeditor.MainScene;

public class MyProcess extends SvrProcess {
	
	//private static CLogger logger = CLogger.getCLogger(MyProcess.class);
	//private static CLogger logger = CLogger.getCLogger("MyProcess", true);
	private Logger logger = LoggerFactory.getLogger(MyProcess.class);
	
	private String someString;
	private int printTableID = 0;
	private Integer someInt;
	private Button	someButton;
	private int m_product_id;
	
	@Override
	protected void prepare() {
		logger.warn("MyProcess: Logovani funkce prepare()");
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		logger.warn("MyProcess: Logovani funkce doIt() Hello");
		ProcessInfoParameter [] parameters = getParameter();
		
		for( int i=0; i < parameters.length; i++){
			String parameterName = parameters[i].getParameterName();
			String para = parameters[i].getParameterAsString();

			logger.info("ParameterAsSring: " + para + " As Object: " + parameters[i]);
//			logger.log(Level.INFO, "Parameter je typu:" + );
			
			if(parameterName != null){
				if(parameterName.equalsIgnoreCase("M_Product_OD")){
					m_product_id = parameters[i].getParameter_ToAsInt();
				}
				else if (parameterName.equalsIgnoreCase("SomeString")){
					someString = (String) parameters[i].getParameter();
				}
				else if (parameterName.equalsIgnoreCase("SomeIteger")){
					someInt = parameters[i].getParameterAsInt();
				}
				else if (parameterName.equalsIgnoreCase("PrintFormat")){
					printTableID = parameters[i].getParameterAsInt();
				}
				else {
					logger.info("Unknown parameter: " + parameterName);
				}
			}
		}
		
		DBManager dbManager = new DBManagerImpl();
		
		List<TableInfo> allTables = dbManager.getTables();
		
//		logger.debug("Dostupne tabulky v databazi:");
//		for (TableInfo tabInfo: allTables) {
//			logger.info(">" + tabInfo.toString() + "< ");
//		}
//		logger.debug("_______ Konec vypisu tabulek________");
		
		//Zkouska internich entit
		ProcessInfo processInfo = getProcessInfo();
		Properties context = getCtx();
		
		log.info(processInfo.toString());
		log.info(context.toString());
		
		log.info("MROLE: " + MRole.getDefault());
		log.info("MUSER: " + MUser.get(Env.getCtx()));
		
		//Zkouska ukladani do tabulky s barvami
		if(someString != null){
	//		MTable.getTable_ID("ad_printcolor");
			MTable colorTable =  MTable.get(Env.getCtx(), MTable.getTable_ID("AD_PrintColor"));
			log.info("MTABLETEST: " +  colorTable);
			
			MColumn[] columns = colorTable.getColumns(true);
			log.info("Table: '" + colorTable.getName() + "' has columns: ");
			for(MColumn col: columns){
				log.info("Column name: " + col.getName());
			}
			
			int colorValue = -1 * someInt;
			java.awt.Color color = new java.awt.Color(colorValue);
			logger.info("Color components for colot int: " + colorValue + " Red: " + color.getRed() + " Blue: " + color.getBlue() + " Green: " + color.getGreen() );
			
			
			String colorToDecode = someString;
			java.awt.Color decodedColor = null;
			if(colorToDecode != null ){
				decodedColor = java.awt.Color.decode(colorToDecode);
				logger.info("Decoded color '" + colorToDecode + "': " + decodedColor);
			} else {
				logger.info("Color field is null.");
			}
			
	//		MColor mcolor = new MColor(Env.getCtx(), colorValue, null);
	//		mcolor.setName("Test Color");	//povinny
	////		mcolor.set_Attribute("colortype", "1");	//pitomost! Nepouziva se!
	//		mcolor.set_CustomColumn("colortype", "G");
	//
	////		mcolor.is_new();	//testuje jestli tahle barva byla nahrana z DB nebo byla vytvorena a nema oporu v DB
	//		mcolor.save();	//ulozi data do databaze
			
			MPrintColor printColor = new MPrintColor(Env.getCtx(), 0, null);
			printColor.setColor(decodedColor);
			printColor.setName(someString);
	//		printColor.saveReplica(true);
			printColor.save();
		}
		
		logger.warn("ziskane id tabulky z do printFormat: " + printTableID);
		if(printTableID > 0){
			MPrintFormat printFormat = new MPrintFormat(Env.getCtx(), printTableID, null);
			logger.warn("Jmeno printFormatu: " + printFormat.getName() );
			
		}
		
		//Nacti tabulku AD_Table
		MTable ad_table = new MTable(Env.getCtx(), MTable.Table_ID, null);
		logger.warn( "Table ID: 100 name: " + ad_table.getTableName() );
		

		return "Hello";
	}
	
	
}
