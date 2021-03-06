package cz.muni.fi.vavmar.reporteditor.DAO;

import cz.muni.fi.vavmar.reporteditor.PaperSettings;
import cz.muni.fi.vavmar.reporteditor.Table;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import org.compiere.print.MPrintFont;
import org.compiere.print.MPrintFormatItem;

public interface DBManager {

    /**
     * Returns list of Views from db.
     * 
     * @return list of Views name
     */
    public List<String> getViews();
    
    /**
     * Returns list of Views accessible from defined role.
     * 
     * @return list of Views name
     */
    public List<String> getViews(String userRole);
    
    /**
     * Returns fullfilled Table from db.
     * 
     * @param tableName
     * @return 
     */
    public Table getTable(String tableName);
    
    /**
     * For specified user returns all names of tables which this user has access to.
     * 
     * @param userRole role of user in iDempiere system. Mostly this will be the name of user.
     * 
     * @return list of names of tables obtainable from user role
     */
    public List<String> getTables(String userRole);

    /**
     * Returns all names of reacheble tables.
     * 
     * @return list of names of tables.
     */
    public List<Table> getTables();


    /**
     * For specified table finds their columns and returns it as List of Strings.
     * 
     * @param tableName name of table to get columns from
     * 
     * @return List of column names of specified table. Null if user doesn't have rights to access given table.
     */
    public List<String> getTableSchema( String tableName);

    /**
     * Tests if user has privileges to work with specified table.
     * 
     * @param tableName name of table to chech user privileges
     * @param userRole role of user in iDempiere system
     * 
     * @return true if given user has rights to access specified table. False otherwise.  
     */
    public boolean hasPriviledges( String tableName, String userRole);
    
    /**
     * Returns ID of passed table
     * 
     * @param tableName name of a table to be processed.
     * @return id numeric identifier in idempiere of tableName passed as parameter.
     */
    public int getTableID(String tableName);
    
    /**
     * Return all print formats belonging specified table or all available if table is > -1.
     * 
     * @param tableID id of table to retrieve print formats for. If -1 all print formats availavble in system is returned.
     * @param excludeFormType There are two basic types of print formats. Form type and standard header type. If true only print formats in the standard format is returned. (ie doesn't have form option checked = without header and footer).
     * @return Map<Integer, String> with IDs and names of print formats.
     */
    public Map<Integer, String> getPrintFormats ( int tableID, boolean excludeFormType );
	
    /**
     * Return all print formats belonging specified table or all available if table is null.
     * 
     * @param tableName id of table to retrieve print formats for. If null all print formats availavble in system is returned.
     * @param excludeFormType There are two basic types of print formats. Form type and standard header type. If true only print formats in the standard format is returned. (ie doesn't have form option checked = without header and footer).
     * @return Map<Integer, String> with IDs and names of print formats.
     */
    public Map<Integer, String> getPrintFormats ( String tableName, boolean excludeFormType );
    
    /**
     * Return all items of print format sorted by area type (header, content, footer) and seqential number.
     * 
     * @param printFormatID
     * @return list of print format items.
     */
    public List<MPrintFormatItem> getFormatItems ( int printFormatID );
    
    /**
     * Retrieve all available fonts from iDempiere.
     * 
     * @return list of available fonts in system.
     */
    public List<MPrintFont> getFonts();
    
    /**
     * Create new font in database.
     * 
     * @param f new font to be created.
     * @return ID of new font created.
     */
    public int createFont(Font f);
    
    /**
     * Loads font from database.
     * 
     * @param ID id of font to be loaded.
     * @return new {@link MPrintFont} or null if not found.
     */
    public MPrintFont loadFont(int fontID);
    
    /**
	 * Retrieve all available paper settings from database.
	 * @return list of {@link PaperSettings}
	 */
    public List<PaperSettings> getAvailablePapers();
    
    /**
     * Check if specified color is already present in system.
     * If not create new color and save it to the database.
     * Else return id of first color matching.
     * 
     * @param c new color to be created.
     * @return ID of new color or color already present.
     */
    public int ceckAndCreateColor(Color c);
    
}
