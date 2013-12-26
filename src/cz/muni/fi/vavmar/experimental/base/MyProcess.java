package cz.muni.fi.vavmar.experimental.base;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyProcess extends SvrProcess {
	
	//private static CLogger logger = CLogger.getCLogger(MyProcess.class);
	//private static CLogger logger = CLogger.getCLogger("MyProcess", true);
	private Logger logger = LoggerFactory.getLogger(MyProcess.class);
	
	private String someString;
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
//			Object parameterAsObject = parameters[i].getParameter();
			
			
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
					someInt = (int)	parameters[i].getParameter_ToAsInt();
				}
				else {
					logger.info("Unknown parameter: " + parameterName);
				}
			}
		}
		DBManager dbManager = new DBManagerImpl();
		
		List<TableInfo> allTables = dbManager.getTables();
		
		logger.debug("Dostupne tabulky v databazi:");
		for (TableInfo tabInfo: allTables) {
			logger.info(">" + tabInfo.toString() + "< ");
		}
		logger.debug("_______ Konec vypisu tabulek________");
		
		ProcessInfo processInfo = getProcessInfo();
		Properties context = getCtx();
		
		log.info(processInfo.toString());
		log.info(context.toString());
		
		return "Hello";
	}
	
	
}
