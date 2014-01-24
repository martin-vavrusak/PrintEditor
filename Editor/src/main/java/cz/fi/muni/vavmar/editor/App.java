package cz.fi.muni.vavmar.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.LabelView;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //Set basic size of scene
        Scene scene = new Scene();
        
        scene.setBackground(Color.BLUE);
        //sceneView.setBackground(Color.GREEN);     //Z nejakeho duvodu nefunguje JComponent udajne neumi vykreslovat barvu
        //sceneView.setBounds(new Rectangle(400, 400));
        scene.getActions().addAction(ActionFactory.createZoomAction());
        
        scene.addChild(new LabelWidget(scene, "Label"));
        
        //Create new base canvas and asociate JScrollPane
        JScrollPane canvas = new JScrollPane ( scene.createView() );
        canvas.getHorizontalScrollBar ().setUnitIncrement (32);
        canvas.getHorizontalScrollBar ().setBlockIncrement (256);
        canvas.getVerticalScrollBar ().setUnitIncrement (32);
        canvas.getVerticalScrollBar ().setBlockIncrement (256);
        canvas.setBounds(0, 0, 300, 150);
        //canvas.getViewport().setBackground(Color.ORANGE);
        
        
        //Create scene No. 2.
        
        Scene scene2 = new Scene();
        scene2.createView();
        scene2.setBackground(Color.YELLOW);
        scene2.getActions().addAction(ActionFactory.createZoomAction());
        scene2.addChild(new LabelWidget(scene2, "Label scene 2."));  //.getActions().addAction(ActionFactory.createMoveAction(); )
        
        JScrollPane scene2canvas = new JScrollPane(scene2.getView());
        scene2canvas.getHorizontalScrollBar ().setUnitIncrement (32);
        scene2canvas.getHorizontalScrollBar ().setBlockIncrement (256);
        scene2canvas.getVerticalScrollBar ().setUnitIncrement (32);
        scene2canvas.getVerticalScrollBar ().setBlockIncrement (256);
        scene2canvas.setBounds(0, 400, 300, 150);
        
        
        //Create basic frame and add previously created canvas
        int width=800,height=600;
        JFrame frame = new JFrame ();//new JDialog (), true);
        frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        //frame.setLayout(new FlowLayout());
        
        frame.add (canvas, BorderLayout.CENTER);
        //frame.add (scene.createSatelliteView(), BorderLayout.CENTER);
        
        JLabel label = new JLabel("Label1");
        label.setBackground(Color.red);
        frame.add(label);
        
        frame.add(scene2canvas);
        
        JLabel label2 = new JLabel("Label2");
        label.setBackground(Color.red);
        frame.add(label2);
        
        //Set maximal size of frame
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((screenSize.width-width)/2, (screenSize.height-height)/2, width, height);   //(screenSize.width-width)/2 "Sets position" to the middle of screen
        frame.setBackground(Color.yellow);
        frame.setVisible (true);
    }
}
