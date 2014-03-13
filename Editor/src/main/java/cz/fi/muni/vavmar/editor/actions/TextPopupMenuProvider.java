/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.actions;

import cz.fi.muni.vavmar.editor.MainScene;
import cz.fi.muni.vavmar.editor.dialogs.TextDialog;
import cz.fi.muni.vavmar.editor.tools.ColumnWidget;
import java.awt.Dialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * Zobrazi menu pri kliknuti pravym tlacitkem mysi na widget a pri zvoleni polozky
 * nastaveni vlastnosti textu deleguje zobrazeni dialogoveho okna a nastaveni na
 * {@link #showEditDialog()} materske tridy
 */
public class TextPopupMenuProvider implements PopupMenuProvider, ActionListener {
        private final Logger logger = LogManager.getLogger(TextPopupMenuProvider.class);

        private final String TEXT_PROPERTIES_COMMAND = "TEXT_PROPERTIES";   //NOI18N
        private final String TEXT_PROPERTIES_LABEL = "Properties";
        private final String CHANGE_TEXT_COMMAND = "CHANGE_TEXT";   //NOI18N
        private final String CHANGE_TEXT_LABEL = "Change text";
        private Widget ownerWidget;

        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            logger.trace("Owner Widget: " + widget + " at: " + localLocation);
            ownerWidget = widget;           //Nastavi widget na kterem bylo vyvolano menu pro dalsi zpracovani v "actionPerformed()"
            
            JPopupMenu menu = new JPopupMenu("Menu");
            JMenuItem menuItem;       
            
            menuItem = new JMenuItem(TEXT_PROPERTIES_LABEL);
            menuItem.setActionCommand(TEXT_PROPERTIES_COMMAND);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            
            if( ! (ownerWidget instanceof ColumnWidget) ){
                menuItem = new JMenuItem(CHANGE_TEXT_LABEL);
                menuItem.setActionCommand(CHANGE_TEXT_COMMAND);
                menuItem.addActionListener(this);
                menu.add(menuItem);
            }
            
            
            return menu;
        }

        public void actionPerformed(ActionEvent e) {
            logger.trace("");
            if ( TEXT_PROPERTIES_COMMAND.equals(e.getActionCommand()) ){
                logger.trace("Zobrazit menu!");
                logger.trace("ownerWidget.getScene(): " + ownerWidget.getScene());
                //test na multiple
                ownerWidget.getScene();
                
                Set<Widget> selectedWidgets = ((MainScene) ownerWidget.getScene()).getSelectedWidgets();
                
                Dialog dialog;

                //when there is more than one widget selected and action is performed at one of them
                if(selectedWidgets.size() > 1 && selectedWidgets.contains(ownerWidget)){
                    //multiple selection edit
                    dialog = new TextDialog(ownerWidget, true);
                } else {
                    dialog = new TextDialog(ownerWidget, false); //deleguje zobrazeni dialogoveho okna a nastaveni hodnot na metodu materske tridy
                }
                
                dialog.setVisible(true);
                        
                
            } else if ( CHANGE_TEXT_COMMAND.equals(e.getActionCommand()) ){
                logger.trace("Nastavit text!!");
//                ownerWidget.getActions().addAction(0, ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor()));
                
                
                
                if(ownerWidget instanceof LabelWidget){
                    String s = (String) JOptionPane.showInputDialog(null, "Please enter new text:", "Input new text.", JOptionPane.PLAIN_MESSAGE);
                    logger.trace("String written: " + s);
                    if(s != null && s.trim().length() > 0){
                        ((LabelWidget) ownerWidget).setLabel(s);
                    }
                    
                } else {
                    logger.warn("Change text called on object which is not LabelWidget!");
                }
            }
        }
        
    }
