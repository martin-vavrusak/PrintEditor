package cz.fi.muni.vavmar.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;

public class App 
{
    public static void main( String[] args )
    {

        Scene scene = new Scene();
        
        scene.setBackground(Color.BLUE);

        scene.getActions().addAction(ActionFactory.createZoomAction());
        scene.getActions().addAction(ActionFactory.createPanAction());
        
        LayerWidget mainLayer = new LayerWidget(scene);
        
        
        LabelWidget lw = new LabelWidget(scene, "Label");
        lw.getActions().addAction(ActionFactory.createMoveAction());
        lw.setOpaque(true);
        lw.setPreferredLocation(new Point(40, 0));
        mainLayer.addChild(lw);
        
        lw = new LabelWidget(scene, "Label22222");
        lw.setOpaque(true);
        lw.setPreferredLocation(new Point(30, 80));
        lw.getActions().addAction(ActionFactory.createMoveAction());
        mainLayer.addChild(lw);
        
        lw = new LabelWidget(scene, "Label33");
        lw.setOpaque(true);
        lw.setPreferredLocation(new Point(50, 30));
        lw.getActions().addAction(ActionFactory.createMoveAction());
        mainLayer.addChild(lw);
        
        scene.addChild(mainLayer);
        

        JScrollPane canvas = new JScrollPane ( scene.createView() );
        canvas.getHorizontalScrollBar ().setUnitIncrement (32);
        canvas.getHorizontalScrollBar ().setBlockIncrement (256);
        canvas.getVerticalScrollBar ().setUnitIncrement (32);
        canvas.getVerticalScrollBar ().setBlockIncrement (256);

        int width=800,height=600;
        JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add (canvas, BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((screenSize.width-width)/2, (screenSize.height-height)/2, width, height);   //(screenSize.width-width)/2 "Sets position" to the middle of screen
        frame.setVisible (true);
    }
}
