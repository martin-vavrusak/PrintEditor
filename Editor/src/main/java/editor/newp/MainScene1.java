
package editor.newp;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author Martin
 */
public class MainScene1 extends Scene {
    private LayerWidget mainLayer;
    private JScrollPane scrollPane;
    
    public MainScene1() {
        setOpaque(true);
        mainLayer = new LayerWidget(this);
        this.addChild(mainLayer);
        
        scrollPane = new JScrollPane ( createView() );
        scrollPane.getHorizontalScrollBar ().setUnitIncrement (32);
        scrollPane.getHorizontalScrollBar ().setBlockIncrement (256);
        
        scrollPane.getVerticalScrollBar ().setUnitIncrement (32);
        scrollPane.getVerticalScrollBar ().setBlockIncrement (256);
        scrollPane.setPreferredSize(new Dimension(400, 600));
                
        
        LabelWidget lw2 = new LabelWidget(this, "This is main scene!");
        lw2.getActions().addAction(ActionFactory.createMoveAction());
        lw2.setPreferredLocation(new Point(50, 50));
        mainLayer.addChild(lw2);
        
        LabelWidget lw = new LabelWidget(this, "Widget 2");
        lw.getActions().addAction(ActionFactory.createMoveAction());
        lw.setPreferredLocation(new Point(50, 100));
        mainLayer.addChild(lw);
        
        getActions().addAction(ActionFactory.createZoomAction());
    }
    

    public JComponent getSceneEnvelope(){
        return scrollPane;
    }
   
    
}
