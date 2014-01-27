package cz.fi.muni.vavmar.editor;

import editor.Table;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.persistence.jpa.jpql.JPQLQueryProblemMessages;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Table t1 = new Table("T1", null);
        Table t2 = new Table("T2", null);
        Table t3 = new Table("T1", null);
        
        assertTrue( t1.equals(t3) );
        assertEquals(t1, t3);
        
        assertFalse(t2.equals(t3));
        assertFalse(t2.equals(t1));
        assertFalse(t1.equals(t2));
    }
}
