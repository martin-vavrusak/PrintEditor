/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.reporteditor.actions;

import java.awt.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.RectangularSelectDecorator;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Create and paint rectangle which represents selection.
 * 
 * @author Martin
 */
public class WidgetRectangularSelectDecorator implements RectangularSelectDecorator {
    private static final Logger logger = LogManager.getLogger(WidgetRectangularSelectDecorator.class);

    private Scene scene;
    
    public WidgetRectangularSelectDecorator(Scene scene) {
        this.scene = scene;
    }
    
    
    //Vytvori pouze widget znazornujici obdelnikovy vyber, fakticky nic nevklada do sceny
    //Vrstva, ktera se zadava pri vytvareni akce pouze rika, kde se bude widget (vyberovy obdelnik) zobrazovat
    public Widget createSelectionWidget() {
        logger.trace("");
        LabelWidget lw = new LabelWidget(scene, "Selection Widget!!!");
        lw.setBorder(BorderFactory.createDashedBorder(Color.ORANGE, 3, 3));
        lw.setBackground(Color.YELLOW);
        lw.setForeground(Color.RED);
        lw.setOpaque(true);
        return lw;
    }
    
}
