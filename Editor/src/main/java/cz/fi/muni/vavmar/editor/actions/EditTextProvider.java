/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.actions;

import cz.fi.muni.vavmar.editor.dialogs.TextDialog;
import javax.swing.JDialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class EditTextProvider implements EditProvider {
        private final Logger logger = LogManager.getLogger(EditTextProvider.class);
        
        public void edit(Widget widget) {
            logger.trace("Edit firing");
            
            JDialog dialog = new TextDialog(widget, false);     //This is fired by double click which should change selection only to one Widged
                                                                //therefore its just single edit
            dialog.setVisible(true);
        }
    }