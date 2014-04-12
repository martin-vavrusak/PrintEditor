/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.tools;

import cz.muni.fi.vavmar.printeditor.actions.EditTextProvider;
import cz.muni.fi.vavmar.printeditor.actions.TextPopupMenuProvider;
import cz.muni.fi.vavmar.printeditor.dialogs.TextPropertiesDialog;

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
  

    public Widget createWidget(Scene scene) {
        logger.trace("TextTool: vytvarim widget");
        LabelWidget lw = new LabelWidget(scene, "TextTool");

//        String s = (String) JOptionPane.showInputDialog(null, "Please enter text:", "Input text.", JOptionPane.PLAIN_MESSAGE);
        String s = (String) JOptionPane.showInputDialog(null, "Please enter text:", "Create new text label.", JOptionPane.PLAIN_MESSAGE, null, null, "TextTool");
                    logger.trace("String written: " + s);
                    if(s != null && s.trim().length() > 0){
                        lw.setLabel(s);
                    }
                    
//        lw.getActions().addAction(ActionFactory.createMoveAction());
        lw.getActions().addAction(ActionFactory.createEditAction( new EditTextProvider() ));
        lw.getActions().addAction(ActionFactory.createPopupMenuAction( new TextPopupMenuProvider() ));

        lw.setOpaque(true);
        return lw;
    }
    
    /**
     * Shows dialog for fillin parameters of text and set all parameters to widget
     * @param widget - widget which parameters should be changed
     */
    protected void showEditDialog(Widget widget, boolean multipleEdit){
        logger.trace("Zobrazuji dialogove okno!");
        JDialog dialog = new TextPropertiesDialog(widget, multipleEdit);
        dialog.setVisible(true);
//        TextDialogPanel panel = new TextDialogPanel();
//        panel.setVisible(true);
    }

    
//    /**
//     * Reaguje na dvojklik levym mysitkem a deleguje dobrazeni dialogoveho okna {@link #showEditDialog()} pro nastaveni parametru textu
//     */
//    private class EditTextProvider implements EditProvider {
//        private final Logger logger = LogManager.getLogger(EditTextProvider.class);
//        
//        public void edit(Widget widget) {
//            logger.trace("Edit firing");
//            showEditDialog(widget, false);
//        }
//        
//    }
    
//    /**
//     * Zobrazi menu pri kliknuti pravym tlacitkem mysi na widget a pri zvoleni polozky
//     * nastaveni vlastnosti textu deleguje zobrazeni dialogoveho okna a nastaveni na
//     * {@link #showEditDialog()} materske tridy
//     */
//    private class TextPopupMenuProvider implements PopupMenuProvider, ActionListener {
//        private final Logger logger = LogManager.getLogger(TextPopupMenuProvider.class);
//
//        private final JPopupMenu menu;
//        private final String TEXT_PROPERTIES_COMMAND = "TEXT_PROPERTIES";   //NOI18N
//        private final String TEXT_PROPERTIES_LABEL = "Properties";
//        private final String CHANGE_TEXT_COMMAND = "CHANGE_TEXT";   //NOI18N
//        private final String CHANGE_TEXT_LABEL = "Change text";
//        private Widget ownerWidget;
//        
//        public TextPopupMenuProvider() {
//            menu = new JPopupMenu("Menu");
//            JMenuItem menuItem;
//            
//            menuItem = new JMenuItem(TEXT_PROPERTIES_LABEL);
//            menuItem.setActionCommand(TEXT_PROPERTIES_COMMAND);
//            menuItem.addActionListener(this);
//            menu.add(menuItem);
//            
//            menuItem = new JMenuItem(CHANGE_TEXT_LABEL);
//            menuItem.setActionCommand(CHANGE_TEXT_COMMAND);
//            menuItem.addActionListener(this);
//            menu.add(menuItem);
//        }
//        
//        
//        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
//            logger.trace("Owner Widget: " + widget + " at: " + localLocation);
//            ownerWidget = widget;           //Nastavi widget na kterem bylo vyvolano menu pro dalsi zpracovani v "actionPerformed()"
//            return menu;
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            logger.trace("");
//            if ( TEXT_PROPERTIES_COMMAND.equals(e.getActionCommand()) ){
//                logger.trace("Zobrazit menu!");
//                logger.trace("ownerWidget.getScene(): " + ownerWidget.getScene());
//                //test na multiple
//                ownerWidget.getScene();
//                
//                Set<Widget> selectedWidgets = ((MainScene) ownerWidget.getScene()).getSelectedWidgets();
//                
//                //when there is more than one widget selected and action is performed at one of them
//                if(selectedWidgets.size() > 1 && selectedWidgets.contains(ownerWidget)){
//                    //multiple selection edit
//                    showEditDialog(ownerWidget, true);
//                } else {
//                    showEditDialog(ownerWidget, false); //deleguje zobrazeni dialogoveho okna a nastaveni hodnot na metodu materske tridy
//                }
//                
//                        
//                
//            } else if ( CHANGE_TEXT_COMMAND.equals(e.getActionCommand()) ){
//                logger.trace("Nastavit text!!");
////                ownerWidget.getActions().addAction(0, ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor()));
//                
//                
//                
//                if(ownerWidget instanceof LabelWidget){
//                    String s = (String) JOptionPane.showInputDialog(null, "Please enter new text:", "Input new text.", JOptionPane.PLAIN_MESSAGE);
//                    logger.trace("String written: " + s);
//                    if(s != null && s.trim().length() > 0){
//                        ((LabelWidget) ownerWidget).setLabel(s);
//                    }
//                    
//                } else {
//                    logger.warn("Change text called on object which is not LabelWidget!");
//                }
//            }
//        }
//        
//    }
    
//    private class LabelTextFieldEditor implements TextFieldInplaceEditor {
//
//        public boolean isEnabled (Widget widget) {
//            return true;
//        }
//
//        public String getText (Widget widget) {
//            return ((LabelWidget) widget).getLabel ();
//        }
//
//        public void setText (Widget widget, String text) {
//            ((LabelWidget) widget).setLabel (text);
//        }
//
//    }
    
}
