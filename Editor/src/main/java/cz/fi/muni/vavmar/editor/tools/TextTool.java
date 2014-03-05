/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.tools;

import cz.fi.muni.vavmar.editor.dialogs.TextDialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.action.PopupMenuProvider;
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
//        lw.getActions().addAction( scene.crea ));
        
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
    protected void showEditDialog(Widget widget){
        logger.trace("Zobrazuji dialogove okno!");
        JDialog dialog = new TextDialog(widget);
        dialog.setVisible(true);
//        TextDialogPanel panel = new TextDialogPanel();
//        panel.setVisible(true);
    }

    
    /**
     * Reaguje na dvojklik levym mysitkem a deleguje dobrazeni dialogoveho okna {@link #showEditDialog()} pro nastaveni parametru textu
     */
    private class EditTextProvider implements EditProvider {
        private final Logger logger = LogManager.getLogger(EditTextProvider.class);
        
        public void edit(Widget widget) {
            logger.trace("Edit firing");
            showEditDialog(widget);
        }
        
    }
    
    /**
     * Zobrazi menu pri kliknuti pravym tlacitkem mysi na widget a pri zvoleni polozky
     * nastaveni vlastnosti textu deleguje zobrazeni dialogoveho okna a nastaveni na
     * {@link #showEditDialog()} materske tridy
     */
    private class TextPopupMenuProvider implements PopupMenuProvider, ActionListener {
        private final Logger logger = LogManager.getLogger(TextPopupMenuProvider.class);

        private final JPopupMenu menu;
        private final String PROPERTIES = "Properties";
        private Widget ownerWidget;
        
        public TextPopupMenuProvider() {
            menu = new JPopupMenu("Menu");
            JMenuItem menuItem;
            
            menuItem = new JMenuItem(PROPERTIES);
            menuItem.setActionCommand(PROPERTIES);
            menuItem.addActionListener(this);
            menu.add(menuItem);
        }
        
        
        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            logger.trace("Owner Widget:" + widget);
            ownerWidget = widget;           //Nastavi widget na kterem bylo vyvolano menu pro dalsi zpracovani v "actionPerformed()"
            return menu;
        }

        public void actionPerformed(ActionEvent e) {
            logger.trace("");
            if ( PROPERTIES.equals(e.getActionCommand()) ){
                logger.trace("Zobrazit menu!");
                showEditDialog(ownerWidget);        //deleguje zobrazeni dialogoveho okna a nastaveni hodnot na metodu materske tridy
            }
        }
        
    }
    
}
