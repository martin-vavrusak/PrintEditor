package cz.fi.muni.vavmar.editor.experimental;

import cz.fi.muni.vavmar.editor.utils.DataFetcher;
import cz.fi.muni.vavmar.editor.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DataFetcherTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataFetcherTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DataFetcherTest.class );
    }

    /**
     * Rigourous Test :-)
     */

    public void testSearchTables()
    {
        DataFetcher df = new DataFetcher();
        df.setData(createTables());
        
        assertTrue(df.searchTables("o").size() == 4);
    }
    
    
    private HashMap<Table, List<String>> createTables(){
        HashMap<Table, List<String>> tables = new HashMap<Table, List<String>>();
        List<String> columns = new ArrayList<String>();
        
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 5; j++){
                columns.add("Column" + j);
            }
            tables.put(new Table("Table" + i, null), columns);
            //tables.add(new Table("Table" + i, columns));
            
            columns = new ArrayList<String>();
        }
        
        columns = new ArrayList<String>();
        columns.add("jmeno");
        columns.add("prijmeni");
        tables.put(new Table("Osoby", null), columns);
        
        columns = new ArrayList<String>();
        columns.add("jedna");
        columns.add("dve");
        tables.put(new Table("Ostatni", null), columns);
        
        columns = new ArrayList<String>();
        columns.add("Vaha");
        columns.add("Vyska");
        columns.add("Vek");
        tables.put(new Table("Olina", null), columns);
        
        columns = new ArrayList<String>();
        columns.add("Euro");
        columns.add("Koruna");
        columns.add("Dolar");
        tables.put(new Table("obezivo", null), columns);
        
        return tables;
    };
}
