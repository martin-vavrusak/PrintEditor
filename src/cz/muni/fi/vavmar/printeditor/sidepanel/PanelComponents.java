/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.sidepanel;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Martin
 */

//TODO Zkusit udělat jako komponentu NB VisualLib a jako Transferable vracet přímo příslušnou komponentu NB VL (LabelWidget, ImageWidget...)
public class PanelComponents extends JPanel {

    public PanelComponents() {
        setPreferredSize(new Dimension (200, 200));
    }
    
    //This metod add component to the panel
    public void addComponent (JComponent components) {
        //add as image thumbnail
    }
    
    //implement DnD (Drag and Drop)
}
