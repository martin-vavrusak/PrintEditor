/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.tools;

import cz.fi.muni.vavmar.editor.Table;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 * Widget reprezentujici sloupec tabulky drzi si odkaz na Objekt tabulky ve scene, ke kteremu patri.
 * @author Martin
 */
public class ColumnWidget extends LabelWidget{
    private static final Logger logger = LogManager.getLogger(ColumnWidget.class);
    private static final Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    
    private Table parentTable;      //Asi smazat a predelat jenom pomoci odkazu na Objekt ve scene
    
    public ColumnWidget(Scene scene) {
        super(scene);
        resetBorder();
    }

    public ColumnWidget(Scene scene, String label) {
        super(scene, label);
        resetBorder();
    }
    
    public void resetBorder(){
        setBorder(defaultBorder);
    }
    
    
    //zatim pouze k odliseni od ostatnich widgetu, bude na nej navazany objekt Tabulka asi

    public Table getParentTable() {
        return parentTable;
    }

    public void setParentTable(Table parentTable) {
        this.parentTable = parentTable;
    }
}
