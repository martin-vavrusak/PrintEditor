/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.dialogs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JDialog;
import javax.swing.JTextField;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class TextDialog extends JDialog{

    public TextDialog(Widget widget) {
        super();
        setLayout(new BorderLayout());
        add(new TextDialogPanel(this));
        
        setBounds(new Rectangle(600, 600));     //TODO Smazat!!! Nechat vypocitat dynamicky
        
        
//        JTextPane textPane = new JTextPane();
//        JTextField textField = new JTextField("--- AaBbCc ---");
//        textField.setHorizontalAlignment(JTextField.CENTER);
//        Font f = new Font(null, WIDTH, WIDTH);


    }
    
    
}
