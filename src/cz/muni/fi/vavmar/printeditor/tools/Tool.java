/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.tools;

import javax.swing.Icon;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public interface Tool {
    /**
     * 
     * @return Icon - ikona nastroj
     */
    public Icon getIcon();
    
    /**
     * Vytvori widget reprezentujici nastroj - umozni kliknutim zobrazit properities (velikost textu, barva...) budto v samostatnem okne nebo dvojklikem jako DialogBox
     * 
     * @return Widget - instance widgetu reprezentujiciho nastroj pro vlozeni do sceny - obsahuje veskere nastaveni a pod
     */
    public Widget createWidget(Scene scene);
}
