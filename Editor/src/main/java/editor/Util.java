/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author Martin
 */
public class Util {
    public static ListModel createListModel(final List<String> list){
        if(list == null | list.size() <= 0) {
            return new DefaultListModel();
        }
        
        return new AbstractListModel() {
            
            public int getSize() {
                return list.size();
            }

            public Object getElementAt(int index) {
                return list.get(index);
            }
        };
    }
}
