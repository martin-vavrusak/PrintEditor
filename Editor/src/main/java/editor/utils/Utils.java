/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.utils;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.MouseHoverAction;
import org.netbeans.modules.visual.action.SelectAction;

/**
 *
 * @author Martin
 */
public class Utils {
    
    public static void setMultiMoveAction(Widget widget, WidgetAction action){
        List<WidgetAction> actions = widget.getActions().getActions();
        if(actions.size() >= 2){
            WidgetAction firstAction = actions.get(0);
            WidgetAction secondAction = actions.get(1);
            if( (firstAction instanceof SelectAction || firstAction instanceof MouseHoverAction) ||
                (secondAction instanceof SelectAction || secondAction instanceof MouseHoverAction)  ){
                
                if(actions.size() > 2){
                    widget.getActions().addAction(2, action);
                    return;
                } else {
                    widget.getActions().addAction(action);
                    return;
                }
            }
        }
        
        
        if(actions.size() >= 1) {
            WidgetAction widgetAction = actions.get(0);
            if( widgetAction instanceof SelectAction || widgetAction instanceof MouseHoverAction ) {
                if(actions.size() > 1){
                    widget.getActions().addAction(1, action);
                    return;
                } else {
                    widget.getActions().addAction(action);
                    return;
                }
            }
        }
        
//        if( actions.size() > 1 && actions.get(0) instanceof SelectAction ){ //jestlize widget ma vice nez 1 akci a prvni je SelectAction
//            widget.getActions().addAction(1, action);    //vloz movement hned za select
//
//        } else if ( actions.size() == 1 && actions.get(0) instanceof SelectAction ){
//            widget.getActions().addAction(action);
//        } else {
//            widget.getActions().addAction(0, action);
//        }
    }
    
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
