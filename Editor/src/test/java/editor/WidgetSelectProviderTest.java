/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class WidgetSelectProviderTest extends TestCase {
    
    public WidgetSelectProviderTest(String testName) {
        super(testName);
    }

    public void testIsAimingAllowed() {
    }

    public void testIsSelectionAllowed() {
    }

    public void testSelect() {
        MainScene scene = new MainScene();
        
        LabelWidget w1 = new LabelWidget(scene, "widget1");
        LabelWidget w2 = new LabelWidget(scene, "widget2");
        LabelWidget w3 = new LabelWidget(scene, "widget3");
        
        List<Widget> widgets = new ArrayList<Widget>();
        widgets.add(w1);
        widgets.add(w2);
        widgets.add(w3);
        
        scene.setSelectedWidgets(widgets);
        
        WidgetSelectProvider sp = new WidgetSelectProvider(scene);
        
        sp.select(w3, null, true);
    }
    
}
