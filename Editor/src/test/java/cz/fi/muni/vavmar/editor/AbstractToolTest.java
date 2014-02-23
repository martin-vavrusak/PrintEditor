/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import cz.fi.muni.vavmar.editor.tools.AbstractTool;
import javax.swing.Icon;
import junit.framework.TestCase;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class AbstractToolTest extends TestCase {
    
    public AbstractToolTest(String testName) {
        super(testName);
    }

    public void testSetIcon() {
//        System.out.println("setIcon");
//        String path = "";
        AbstractTool instance = new AbstractToolImpl("text");
//        instance.setIcon(path);
//        fail("The test case is a prototype.");
        assertNotNull(instance.setIcon("images/textTool.png"));
    }

    public class AbstractToolImpl extends AbstractTool {

        public AbstractToolImpl(String text) {
            super(text);
        }


        @Override
        public Widget createWidget(Scene scene) {
            return null;
        }
    }
    
}
