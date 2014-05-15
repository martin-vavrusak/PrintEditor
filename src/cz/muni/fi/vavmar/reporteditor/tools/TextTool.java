/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor.tools;

import cz.muni.fi.vavmar.reporteditor.MainScene;
import cz.muni.fi.vavmar.reporteditor.actions.EditTextProvider;
import cz.muni.fi.vavmar.reporteditor.actions.TextPopupMenuProvider;
import cz.muni.fi.vavmar.reporteditor.dialogs.TextPropertiesDialog;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class TextTool extends AbstractTool {
//    private static final Logger logger = Logger.getLogger(TextTool.class.getName());

    public TextTool(Icon icon) {
        super(icon);
        logger = LogManager.getLogger(TextTool.class.getName());    //inherited logger
    }

    public TextTool(String text) {
        super(text);
        logger = LogManager.getLogger(TextTool.class.getName());    //inherited logger
    }
    
    public TextTool(String text, String iconPath) {
        super(text, iconPath);
        logger = LogManager.getLogger(TextTool.class.getName());    //inherited logger
    }
  

    public Widget createWidget(MainScene scene) {
        logger.trace("TextTool: vytvarim widget");
        LabelWidget lw = new LabelWidget(scene, "TextTool");

        String s = (String) JOptionPane.showInputDialog(null, "Please enter text:", "Create new text label.", JOptionPane.PLAIN_MESSAGE, null, null, "TextTool");
                    logger.trace("String written: " + s);
                    if(s != null && s.trim().length() > 0){
                        lw.setLabel(s);
                    }
                    
        lw.getActions().addAction(ActionFactory.createEditAction( new EditTextProvider() ));
        lw.getActions().addAction(ActionFactory.createPopupMenuAction( new TextPopupMenuProvider() ));

        lw.setOpaque(true);		//this need to be set because of hover action and color change
        return lw;
    }
    
}
