/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.DAO;

import cz.muni.fi.vavmar.printeditor.PaperSettings;
import cz.muni.fi.vavmar.printeditor.Table;

import java.awt.Color;
import java.awt.Font;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import javax.swing.text.TabExpander;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.model.MColor;
import org.compiere.model.MTable;
import org.compiere.print.MPrintColor;
import org.compiere.print.MPrintFont;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.print.MPrintPaper;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openide.util.Exceptions;

/**
 *
 * @author Martin
 */
public class DataProviderIdempiere implements DBManager {
    public static final String AD_TABLE = "AD_Table";
    public static final String COLUMN_TABLE_NAME = "tablename";	//Name of column containing names of all tables of iDempiere
    public static final String COLUMN_TABLE_DESCRIPTION = "description"; 
        
    private static final Logger logger = LogManager.getLogger(DataProviderIdempiere.class);
//    DataSource  ds = new DataSourceImpl("localhost", "5432", "idempiere", "adempiere", "adempiere");
    

    public Table getTable(String tableName){
        Table table = new Table();
        List<String> columns = new ArrayList<String>();
        
        String sql = "SELECT "
                            + COLUMN_TABLE_NAME + ", "
                            + COLUMN_TABLE_DESCRIPTION
                    + " FROM " + AD_TABLE
                    + " WHERE " 
                            + COLUMN_TABLE_NAME + " = ?";
        
              
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = DB.prepareStatement(sql, null);
            ps.setString(1, tableName);
            
            logger.trace("Prepared Statement: " + ps);
            
            if( !ps.execute() ){
                logger.error("Unable to get tables from DB. SQL: " + sql);
            } else {
                resultSet = ps.getResultSet();
                
                if(resultSet == null){
                    logger.error("Result set is null!");
                } else {
                    resultSet.next();
                    table.setName( resultSet.getString(COLUMN_TABLE_NAME) );
                    table.setDescription( resultSet.getString(COLUMN_TABLE_DESCRIPTION) ); 
                }
            }
//            ps.close();
            
            //Retrieving the columns of the table
            DatabaseMetaData metadata = ps.getConnection().getMetaData();
            ResultSet metaDataResultSet = metadata.getColumns(null, null, tableName.toLowerCase(), null); //Get All columns of table if last is filled should return info about particular column
            
            while ( metaDataResultSet.next()){
                String columnName = metaDataResultSet.getString("COLUMN_NAME");
                columns.add(columnName);
                logger.trace( "colum name: " +  columnName);
            }
            table.setColumns(columns);
//            ps.close();
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            
        } finally {
             DB.close(resultSet, ps);
             resultSet = null;
             ps = null;
        }
        
        return table;
    }
    
    /*
    //TODO Udelat vypleni SQL
    @Override
    public Table getTable(String tableName){
        Table table = new Table();
        List<String> columns = new ArrayList<String>();
        
        String sql = "SELECT "
                            + COLUMN_TABLE_NAME + ", "
                            + COLUMN_TABLE_DESCRIPTION
                    + " FROM " + AD_TABLE
                    + " WHERE " 
                            + COLUMN_TABLE_NAME + " = ?";
        
              
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
                    table.setName( resultSet.getString(COLUMN_TABLE_NAME) );
                    table.setDescription( resultSet.getString(COLUMN_TABLE_DESCRIPTION) ); 
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
    */
    
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
                            + COLUMN_TABLE_NAME + ", "
                            + COLUMN_TABLE_DESCRIPTION
                    + " FROM " + AD_TABLE;
        
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = DB.prepareStatement(sql, null);
            if( !ps.execute() ){
                logger.error("Unable to get tables from DB. SQL: " + sql);
                
            } else {
                resultSet = ps.getResultSet();
                
                if(resultSet == null){
                    logger.error("Result set is null!");
                } else {
                    while (resultSet.next()) {
                        Table tbInfo = new Table();
                        tbInfo.setName( resultSet.getString(COLUMN_TABLE_NAME) );
                        tbInfo.setDescription(resultSet.getString(COLUMN_TABLE_DESCRIPTION) );
                        tables.add( tbInfo );
                    }
                }
            }
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            
        } finally {
        	DB.close(resultSet, ps);
        	ps = null;
        	resultSet = null;
        }
        
        return tables;
    }
    
    /*
    public List<Table> getTables() {
        List<Table> tables = new ArrayList<Table>();
        
        String sql = "SELECT "
                            + COLUMN_TABLE_NAME + ", "
                            + COLUMN_TABLE_DESCRIPTION
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
                        tbInfo.setName( resultSet.getString(COLUMN_TABLE_NAME) );
                        tbInfo.setDescription(resultSet.getString(COLUMN_TABLE_DESCRIPTION) );
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
    */

    
    public List<String> getTableSchema(String tableName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean hasPriviledges(String tableName, String userRole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getViews() {
    	List<String> tables = new ArrayList<String>();
    	
    	
    	String sql = "SELECT " + COLUMN_TABLE_NAME + " FROM AD_Table WHERE isview='Y'";
    	
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while(rs.next()){
				tables.add(rs.getString(1));
			}
		}
		catch (Exception e)
		{
			logger.log(Level.ERROR, "Error during retrieving views: ", e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
    	return tables;
    }
    
    /*
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
    */

    public List<String> getViews(String userRole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public int getTableID(String tableName) {
		logger.trace("Resolving id of table: " + tableName);
//		MTable ad_table = new MTable(Env.getCtx(), 100, null);
//		logger.trace( "Table ID: 100 name: " + ad_table.getName() );
		
		int tableID = MTable.getTable_ID(tableName);
		
		logger.trace( "Table '" + tableName + "' has ID: " + tableID );
		return tableID;
	}
    
    
//	//Return map of all formats aviable for specified table
//    public Map<Integer, String> getPrintFormats( int tableID ){
//    	Map<Integer, String> returnMap = new HashMap<Integer, String>();
//
//    	String sql = "SELECT ad_printformat_id, name FROM AD_PrintFormat WHERE AD_Table_ID=?";
//    	PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try
//		{
//			pstmt = DB.prepareStatement (sql, null);
//			pstmt.setInt (1, tableID);
//			rs = pstmt.executeQuery ();
//			while(rs.next()){
//				returnMap.put(rs.getInt(1), rs.getString(2));
//			}
//		}
//		catch (Exception e)
//		{
//			logger.log(Level.ERROR, "Unable to get print formats of table: " + tableID, e);
//		}
//		finally {
//			DB.close(rs, pstmt);
//			rs = null; pstmt = null;
//		}
//		
//    	return returnMap;
//    }

    /**
     * Return all print formats belonging specified table or all available if table is null.
     * 
     * @param tableName id of table to retrieve print formats for. If null all print formats availavble in system is returned.
     * @param excludeFormType There are two basic types of print formats. Form type and standard header type. If true only print formats in the standard format is returned. (ie doesn't have form option checked = without header and footer).
     * @return Map<Integer, String> with IDs and names of print formats.
     */
	@Override
	public Map<Integer, String> getPrintFormats ( String tableName, boolean excludeFormType ){
		if(tableName == null){
			return getPrintFormats(-1, excludeFormType);
			
		} else {
			int tableID = getTableID(tableName);
			return getPrintFormats(tableID, excludeFormType);
		}
		
	}
	
    /**
     * Return all print formats belonging specified table or all available if table is > -1.
     * 
     * @param tableID id of table to retrieve print formats for. If -1 all print formats availavble in system is returned.
     * @param excludeFormType There are two basic types of print formats. Form type and standard header type. If true only print formats in the standard format is returned. (ie doesn't have form option checked = without header and footer).
     * @return Map<Integer, String> with IDs and names of print formats.
     */
	@Override
    public Map<Integer, String> getPrintFormats ( int tableID, boolean excludeFormType ){
    	Map<Integer, String> returnMap = new HashMap<Integer, String>();

    	StringBuilder sqlString = new StringBuilder();
    	sqlString.append("SELECT ad_printformat_id, name FROM AD_PrintFormat ");
    	
    	if( tableID > -1 ){
    		logger.trace("Table id is: " + tableID);
    		sqlString.append(" WHERE AD_Table_ID=?");
    	} else {
    		logger.trace("No table id specified: " + tableID + " retrieving all print formats.");
    	}
    	
    	if( excludeFormType ){
    		if(tableID > -1){
    			sqlString.append(" AND isform='N'");		//WHERE clause is allready present append only next condition
    			
    		} else {
    			sqlString.append(" WHERE isform='N'");
    		}
    	}
    	
    	logger.trace("SQL string to retrieve print formats: '" + sqlString + "'.");
    	
//    	String sql = "SELECT ad_printformat_id, name FROM AD_PrintFormat WHERE AD_Table_ID=? AND isform='N'";
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sqlString.toString(), null);
			if( tableID > -1 ) { pstmt.setInt (1, tableID); }
			rs = pstmt.executeQuery ();
			while(rs.next()){
				returnMap.put(rs.getInt(1), rs.getString(2));
			}
		}
		catch (Exception e)
		{
			logger.log(Level.ERROR, "Unable to get print formats of table: " + tableID, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
    	return returnMap;
    }
    
    
	@Override
	public List<MPrintFormatItem> getFormatItems(int printFormatID) {
    	List<Integer> itemIDs = new ArrayList<Integer>();
    	List<MPrintFormatItem> items = new ArrayList<MPrintFormatItem>();
    	
    	/*
    	 * Load ids of all print format items
    	 */
    	String sql = "SELECT ad_printformatitem_id, seqno FROM AD_PrintFormatItem WHERE ad_printformat_id=? ORDER BY	seqno";
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, printFormatID);
			rs = pstmt.executeQuery ();
			while(rs.next()){
				itemIDs.add( rs.getInt(1) );
			}
		}
		catch (Exception e)
		{
			logger.log(Level.ERROR, "Unable to get items for print format: " + printFormatID, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		
		/*
		 * load items
		 */
		Properties context = Env.getCtx();
		
		for(Integer i: itemIDs){
			MPrintFormatItem item = new MPrintFormatItem(context, i, null);
			items.add(item);
		}
		
    	return items;
	}

	@Override
	public List<MPrintFont> getFonts() {
		int[] ids = MPrintFont.getAllIDs(MPrintFont.Table_Name, null, null);
		List<MPrintFont> fontsList = new ArrayList<MPrintFont>();
		
		for(int i : ids){
			fontsList.add( MPrintFont.get(i) );
		}
		
		return fontsList;
	}

	//TODO maybe should chceck font presence first
	@Override
	public int createFont(Font f) {
		MPrintFont font = new MPrintFont(Env.getCtx(), 0, null);
		font.setFont(f);
		
		StringBuilder fontName = new StringBuilder();
		fontName.append(f.getFamily());
		
		if (f.isBold()){
			fontName.append(" bold");
		}
		
		if (f.isItalic()){
			fontName.append(" italic");
		}
		fontName.append(" ").append(f.getSize());
		
		
		font.setName(fontName.toString());				//this must be unique!!!
		
		if( !font.save() ){
			logger.error("Error when saving new font: " + font);
		}
		return font.get_ID();
	}

	@Override
	public MPrintFont loadFont(int fontID) {
		MPrintFont font = MPrintFont.get(fontID);
		if(font == null){
			logger.error("Error while loading font with id: " + fontID);
		}
		return font;
	}

	
	/**
	 * Retrieve all available paper settings from database.
	 * @return list of {@link PaperSettings}
	 */
	public List<PaperSettings> getAvailablePapers(){
		String sql = "SELECT ad_printpaper_id, name, description, islandscape, code, margintop, marginleft, marginright, marginbottom, sizex, sizey, dimensionunits "
						+ " FROM ad_printpaper";
		List<PaperSettings> paperSettingsList = new ArrayList<PaperSettings>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while(rs.next()){
				PaperSettings ps = new PaperSettings();
				ps.setPaperID ( rs.getInt(1) );
				ps.setName( rs.getString(2) );
				ps.setDescription( rs.getString(3) );
				ps.setLandscape( "Y".equalsIgnoreCase( rs.getString(4) ) );
				ps.setCode( rs.getString(5) );
				ps.setTopMargin( rs.getInt(6) );
				ps.setLeftMargin( rs.getInt(7) );
				ps.setRightMargin( rs.getInt(8) );
				ps.setBottomMargin( rs.getInt(9) );
				ps.setWidth( rs.getFloat(10) );
				ps.setHeight( rs.getFloat(11) );
				ps.setUnits( rs.getString(12) );
				
				paperSettingsList.add(ps);
			}
		}
		catch (Exception e)
		{
			logger.log(Level.ERROR, "Unable to get items ad_printtable. ", e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return paperSettingsList;
	}
	
	@Override
	public int ceckAndCreateColor(Color color) {
		String sql = "SELECT ad_printcolor_id, code FROM ad_printcolor ";
		
		Map<Color, Integer> colors = new HashMap<Color, Integer>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while(rs.next()){
				
				String colorInt = rs.getString(2);
				int dbID = rs.getInt(1);
				
				try{
					int colorIteger = Integer.parseInt(colorInt);
					
					colors.put(new Color(colorIteger), dbID);
					
				} catch (Exception e){
					// do nothing - unpareable
				}
						
				
			}
		}
		catch (Exception e)
		{
			logger.log(Level.ERROR, "Unable to get items ad_printtable. ", e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		logger.trace("Retrieved colors: " + colors);
		
		Integer colorID = -1;
		colorID = colors.get(color);
		logger.trace("ID of color found: " + colorID);
		
		if(colorID != null){	//If color already present in system return this color
			return colorID;
			
		} else {
			return saveColor(color, null);	//else save this color to iDempiere
		}
		
		
	}
	
	/**
	 * Save color to iDempiere. If name is null than hex code is saved as name.
	 * 
	 * @param color color to be saved
	 * @param name name of color, if null than hex code of color is used. e.g. #FF11AB
	 * @return ID of newly inserted color or -1.
	 */
	public int saveColor(Color color, String name){
		if(color == null)	return -1;
		
		MPrintColor iColor = new MPrintColor(Env.getCtx(), 0, null);
		iColor.setColor(color);
		if(name != null && !name.trim().isEmpty() ){
			iColor.setName(name);
		} else {
			iColor.setName( String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()) );
		}
		
		if( iColor.save() ){
			return iColor.get_ID();
		} else {
			return -1;
		}
	}
}
