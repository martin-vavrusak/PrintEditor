/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.DAO;

import cz.muni.fi.vavmar.printeditor.Table;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openide.util.Exceptions;

/**
 *
 * @author Martin
 */
public class DataProvider implements DBManager {
    public static final String AD_TABLE = "AD_Table";
    public static final String COLUMN_NAME_OF_TABLES_NAMES = "tablename";	//Name of column containing names of all tables of iDempiere
    public static final String COLUMN_NAME_OF_TABLE_DESCRIPTION = "description"; 
        
    private static final Logger logger = LogManager.getLogger(DataProvider.class);
    DataSource  ds = new DataSourceImpl("localhost", "5432", "idempiere", "adempiere", "adempiere");
    

    //TODO Udelat vypleni SQL
    @Override
    public Table getTable(String tableName){
        Table table = new Table();
        List<String> columns = new ArrayList<String>();
        
        String sql = "SELECT "
                            + COLUMN_NAME_OF_TABLES_NAMES + ", "
                            + COLUMN_NAME_OF_TABLE_DESCRIPTION
                    + " FROM " + AD_TABLE
                    + " WHERE " 
                            + COLUMN_NAME_OF_TABLES_NAMES + " = ?";
        
              
        PreparedStatement ps = null;
        try {
            ps = ds.getConnection().prepareStatement(sql);
            ps.setString(1, tableName);
            
            logger.trace("Prepared Statement: " + ps);
            
            if( !ps.execute() ){
                logger.error("Unable to get tables from DB. SQL: " + sql);
            } else {
                ResultSet resultSet = ps.getResultSet();
                
                if(resultSet == null){
                    logger.error("Result set is null!");
                } else {
                    resultSet.next();
                    table.setName( resultSet.getString(COLUMN_NAME_OF_TABLES_NAMES) );
                    table.setDescription( resultSet.getString(COLUMN_NAME_OF_TABLE_DESCRIPTION) ); 
                }
            }
            ps.close();
            
            //Retrieving the columns of the table
            DatabaseMetaData metadata = ds.getConnection().getMetaData();
            ResultSet metaDataResultSet = metadata.getColumns(null, null, tableName.toLowerCase(), null); //Get All columns of table if last is filled should return info about particular column
            
            while ( metaDataResultSet.next()){
                String columnName = metaDataResultSet.getString("COLUMN_NAME");
                columns.add(columnName);
                logger.trace( "colum name: " +  columnName);
            }
            table.setColumns(columns);
            ps.close();
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            
        } finally {
            try {
                if(ps != null && !ps.isClosed()){
                     ps.close(); 
                }
            } catch ( Exception ex ){logger.debug("Error during closing statement after previous error: " + ex);}
        }
        
        return table;
    }
    
    public List<String> getTables(String userRole) {
        List<String> tables = new ArrayList<String>();
        for(Table ti : getTables()){
            tables.add(ti.getName());
        }
        Collections.sort(tables);
        return tables;
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<Table>();
        
        String sql = "SELECT "
                            + COLUMN_NAME_OF_TABLES_NAMES + ", "
                            + COLUMN_NAME_OF_TABLE_DESCRIPTION
                    + " FROM " + AD_TABLE;
        
        PreparedStatement ps;
        try {
            ps = ds.getConnection().prepareStatement(sql);
            if( !ps.execute() ){
                logger.error("Unable to get tables from DB. SQL: " + sql);
            } else {
                ResultSet resultSet = ps.getResultSet();
                
                if(resultSet == null){
                    logger.error("Result set is null!");
                } else {
                    while (resultSet.next()) {
                        Table tbInfo = new Table();
                        tbInfo.setName( resultSet.getString(COLUMN_NAME_OF_TABLES_NAMES) );
                        tbInfo.setDescription(resultSet.getString(COLUMN_NAME_OF_TABLE_DESCRIPTION) );
                        tables.add( tbInfo );
                    }
                }
            }
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        
//        logger.trace("Tables retrieved: ");
//        for(Table ti : tables){
//            logger.trace(ti);
//        }
        return tables;
    }

    public List<String> getTableSchema(String tableName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean hasPriviledges(String tableName, String userRole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getViews() {
        List<String> tables = new ArrayList<String>();
        
        String sql = "SELECT "
                            + COLUMN_NAME_OF_TABLES_NAMES + ", "
                            + COLUMN_NAME_OF_TABLE_DESCRIPTION
                    + " FROM " + AD_TABLE;
        
        try {
            DatabaseMetaData metaData = ds.getConnection().getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, new String[]{"VIEW"} );
            
            while(rs.next()){
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
            }
        } catch (SQLException ex){
            logger.error("Error retrieving Views from DB: " + ex);
        }

        return tables;
    }

    public List<String> getViews(String userRole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
