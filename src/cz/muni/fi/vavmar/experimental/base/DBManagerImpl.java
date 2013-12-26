package cz.muni.fi.vavmar.experimental.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO pouzit radeji tridu reprezentujici AD_TABLE
public class DBManagerImpl implements DBManager {
	private static final String COLUMN_NAME_OF_TABLES_NAMES = "tablename";	//Name of column containing names of all tables of iDempiere
	private static final String COLUMN_NAME_OF_TABLE_DESCRIPTION = "description"; 
	
	private static Logger logger = LoggerFactory.getLogger(DBManagerImpl.class); 
	
	@Override
	public List<TableInfo> getTables() {
		List<TableInfo> tables = new ArrayList<TableInfo>();
		StringBuilder sql = new StringBuilder( "SELECT " + 
													COLUMN_NAME_OF_TABLES_NAMES + ", " +
													COLUMN_NAME_OF_TABLE_DESCRIPTION +
												" FROM AD_Table;" );
		ResultSet resultSet;
		
		PreparedStatement ps = DB.prepareStatement(sql.toString(), null);
		
		//Preparation and execution of sql query
		try {
			if( !ps.execute() ) {
				logger.error("Select from DB was executed with unknown error! SQL used: >>>" + sql.toString() + "<<<");
				return null;
			}
			
		} catch (SQLException e) {
			logger.error("Error while executing sql: >>>" + sql.toString() + "<<<");
			e.printStackTrace();
			return null;
		}
		
		
		//Retrieval of values from result set
		try {
			resultSet = ps.getResultSet();

			if (resultSet == null) {
				logger.error("Error when retrieving result set!");
				return null;
			} else {
				while (resultSet.next()) {
					TableInfo tbInfo = new TableInfo();
					tbInfo.setTableName( resultSet.getString(COLUMN_NAME_OF_TABLES_NAMES) );
					tbInfo.setTableName( resultSet.getString(COLUMN_NAME_OF_TABLES_NAMES) );
					tables.add( tbInfo );
				}
			}
			
		} catch (SQLException e) {
			logger.error("Error during data retrieval from ResultSet!");
			e.printStackTrace();
		}
		
		logger.info(">>>" + sql.toString() + "<<< Was successful");
		return tables;
	}
	
	@Override
	public List<String> getTables(String userRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTableSchema(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPriviledges(String tableName, String userRole) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
