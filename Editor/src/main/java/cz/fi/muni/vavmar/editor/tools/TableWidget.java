/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.tools;

import cz.fi.muni.vavmar.editor.DAO.DataProvider;
import cz.fi.muni.vavmar.editor.DAO.DataSourceImpl;
import org.eclipse.persistence.internal.jpa.config.persistenceunit.DataServiceImpl;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Exceptions;

/**
 *
 * @author Martin
 */
public class TableWidget extends LabelWidget{

    
    public TableWidget(Scene scene) {
        super(scene);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        DataProvider provider = new DataProvider();
        provider.getTables();
    }

    public TableWidget(Scene scene, String label) {
        super(scene, label);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        DataProvider provider = new DataProvider();
        provider.getTables();
    }
    
    //zatim pouze k odliseni od ostatnich widgetu, bude na nej navazany objekt Tabulka asi
}
