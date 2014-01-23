package cz.fi.muni.vavmar.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
        Scene scene = new Scene();
        
        //Create new base canvas and asociate JScrollPane
        JScrollPane canvas = new JScrollPane ( scene.createView() );
        canvas.getHorizontalScrollBar ().setUnitIncrement (32);
        canvas.getHorizontalScrollBar ().setBlockIncrement (256);
        canvas.getVerticalScrollBar ().setUnitIncrement (32);
        canvas.getVerticalScrollBar ().setBlockIncrement (256);
        canvas.setBackground(Color.BLUE);
        
        //Create basic frame and add previously created canvas
        int width=800,height=600;
        JFrame frame = new JFrame ();//new JDialog (), true);
        frame.add (canvas, BorderLayout.CENTER);
        frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        
        //Set maximal size of frame
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((screenSize.width-width)/2, (screenSize.height-height)/2, width, height);   //(screenSize.width-width)/2 "Sets position" to the middle of screen
        frame.setVisible (true);
    }
}
