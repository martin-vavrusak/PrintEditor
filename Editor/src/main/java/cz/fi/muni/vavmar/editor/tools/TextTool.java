/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.tools;

import javax.swing.Icon;
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
  

    public Widget createWidget(Scene scene) {
        LabelWidget lw = new LabelWidget(scene, "TextTool");
        lw.getActions().addAction(ActionFactory.createMoveAction());
        return lw;
    }
    

}
