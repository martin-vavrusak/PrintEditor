/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor.actions;

import cz.muni.fi.vavmar.reporteditor.MainScene;
import cz.muni.fi.vavmar.reporteditor.dialogs.TextPropertiesDialog;
import cz.muni.fi.vavmar.reporteditor.widgets.ColumnWidget;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
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
        private final String CHANGE_COLOR_COMMAND = "CHANGE_COLOR";   //NOI18N
        private final String CHANGE_COLOR_LABEL = "Change color";
        
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
            
            menuItem = new JMenuItem(CHANGE_COLOR_LABEL);
            menuItem.setActionCommand(CHANGE_COLOR_COMMAND);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            
            if( ! (ownerWidget instanceof ColumnWidget) ){			//This provider should be used only for TextTool and ColumnWidget, both of them are implementing LabelWidget
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
                    dialog = new TextPropertiesDialog(ownerWidget, true);
                } else {
                    dialog = new TextPropertiesDialog(ownerWidget, false); //deleguje zobrazeni dialogoveho okna a nastaveni hodnot na metodu materske tridy
                }
                
                dialog.setVisible(true);
                        
                
            } else if ( CHANGE_TEXT_COMMAND.equals(e.getActionCommand()) ){
                logger.trace("Nastavit text!!");
//                ownerWidget.getActions().addAction(0, ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor()));
                
                
                
                if(ownerWidget instanceof LabelWidget){
//                    String s = (String) JOptionPane.showInputDialog(null, "Please enter new text:", "Input new text.", JOptionPane.PLAIN_MESSAGE);
                    String s = (String) JOptionPane.showInputDialog(null, 
                                                            "Please enter new text:",
                                                            "Input new text.",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            null,
                                                            ((LabelWidget)ownerWidget).getLabel());
                    logger.trace("String written: " + s);
                    if(s != null && s.trim().length() > 0){
                        ((LabelWidget) ownerWidget).setLabel(s);
                    }
                    
                } else {
                    logger.warn("Change text called on object which is not LabelWidget!");
                }
                
            } else if ( CHANGE_COLOR_COMMAND.equals(e.getActionCommand()) ){
            	
            	if(ownerWidget instanceof LabelWidget){
            		Color c = ((LabelWidget) ownerWidget).getForeground();
            		Color newColor = JColorChooser.showDialog(null, "Choose color.", c);
            		logger.trace("New color choosen: " + newColor);
            		
            		if(newColor != null){
            			ownerWidget.setForeground(newColor);
            			
            			//Set color to all selected items
            			Scene scene = ownerWidget.getScene();
            			if( scene instanceof MainScene ){
            				Set<Widget> selectedWidgets = ((MainScene) scene).getSelectedWidgets();
            				for(Widget w: selectedWidgets){
            					if(w instanceof LabelWidget){	//Here should be better to create separate Widget for text only!!!! And include this menu provider there too!!!
            						w.setForeground(newColor);
            					}
            				}
            			} else {
            				logger.warn("Something realy weird happend this widget doesn't have scene of type MainScene!!!");
            			}
            		}
            	} else {
                    logger.warn("Change text called on object which is not LabelWidget!");
                }
            	
            }
        }
        
    }
