package cz.muni.fi.vavmar.printeditor.DAO;

import cz.muni.fi.vavmar.printeditor.Table;
import java.util.List;
import java.util.Map;

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
     * Returns all available print formats of table.
     * 
     * @param tableID
     * @return map of available print formats of table.
     */
    public Map<Integer, String> getPrintFormats( int tableID );
    
    /**
     * Return all items of print format sorted by seqential number.
     * 
     * @param printFormatID
     * @return list of print format items.
     */
    public List<MPrintFormatItem> getFormatItems ( int printFormatID);
}
