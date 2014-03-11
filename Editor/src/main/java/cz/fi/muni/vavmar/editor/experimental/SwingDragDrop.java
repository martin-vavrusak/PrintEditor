
package cz.fi.muni.vavmar.editor.experimental;

import cz.fi.muni.vavmar.editor.tools.TextTool;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class SwingDragDrop{
    
	JTextField txtField;
	JLabel lbl;
        
	public static void main(String[] args){
		SwingDragDrop sdd = new SwingDragDrop();
	}

	public SwingDragDrop(){
		
		txtField = new JTextField(20);
		lbl = new JLabel("This is the text for drag and drop.");
		lbl.setTransferHandler(new TransferHandler("text"));
		
                MouseListener ml = new MouseAdapter(){
                        @Override
			public void mousePressed(MouseEvent e){
				JComponent jc = (JComponent)e.getSource();
				TransferHandler th = jc.getTransferHandler();
				th.exportAsDrag(jc, e, TransferHandler.COPY);
                                System.out.println("Mouse Pressed");
			}
		};
                
		lbl.addMouseListener(ml);
		
                JPanel panel = new JPanel();
		panel.add(txtField);
                
                
//                panel.add(TextTool.getJLabelIcon());
                
//                URL url = getClass().getClassLoader().getResource("images/textTool.png");
//                
//                System.out.println("PNG located at: " + url );
//                
//                JLabel label = new JLabel("textTool", new ImageIcon(url), JLabel.CENTER );
//                label.setVerticalTextPosition(JLabel.BOTTOM);
//                label.setHorizontalTextPosition(JLabel.CENTER);
//                panel.add(label);
                
               panel.add(new TextTool("Text2", "images/textTool.png"));
                       
                
                JFrame frame = new JFrame("Drag Drop Demo");
		frame.add(lbl, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
}