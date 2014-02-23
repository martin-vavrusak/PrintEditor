package cz.fi.muni.vavmar.editor;

import cz.fi.muni.vavmar.editor.tools.TextTool;
import editor.Table;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.persistence.jpa.jpql.JPQLQueryProblemMessages;

/**
 * Unit test for simple App.
 */
public class TextToolTest extends TestCase {
    
    private TextTool tool;
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TextToolTest( String testName )
    {
        super( testName );
//        tool = new TextTool(null);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TextToolTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testGetIcon()
    {
//        assertNotNull(tool.getIcon());
    }
}
