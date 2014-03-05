/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.MouseHoverAction;
import org.netbeans.modules.visual.action.SelectAction;

/**
 *
 * @author Martin
 */
public class MainScene extends Scene {
    private static final Logger logger = LogManager.getLogger(MainScene.class);
    private JScrollPane scrollPane;
    private LayerWidget mainLayer;
    private LayerWidget backgroundLayer;
    private ResizeParentByMoveActionProvider moveProvider;
    private WidgetAction hoverAction;
    private WidgetAction rectangularSelectionAction;
    private WidgetAction multipleMovementAction;
    private List<Widget> selectedWidgets;
    
    private WidgetAction singleSelectActino = new WidgetSelectionAction(this);
    
    public MainScene() {
        setOpaque(true);
        selectedWidgets = new ArrayList<Widget>();
        
        moveProvider = new ResizeParentByMoveActionProvider();
        hoverAction = ActionFactory.createHoverAction(new WidgetHoverActionProvider());
        multipleMovementAction = ActionFactory.createMoveAction( null , new MultiMoveProvider( this ));     //Must be before RectangularSelection
        
        backgroundLayer = new LayerWidget(this);
        mainLayer = new LayerWidget(this);
//        rectangularSelectionAction = ActionFactory.createRectangularSelectAction(this, mainLayer);
        rectangularSelectionAction = ActionFactory.createRectangularSelectAction(
                                    new WidgetRectangularSelectDecorator(this), backgroundLayer,    //layer where rectangle of selection will be painted
                                    new WidgetRectangularSelectionProvider(this, mainLayer));       //layer whose widgets will be tested for selection
        
        
        
        mainLayer.getActions().addAction( ActionFactory.createResizeAction() );
//        mainLayer.getActions().addAction( rectangularSelectionAction );
        mainLayer.setPreferredBounds(new Rectangle(900, 900));
        mainLayer.setVisible(true);
        
        addChild(0, backgroundLayer);
        addChild(mainLayer);
//        setBackground(Color.BLUE);
        
        //Vyclenit do samostatne tridy pro lepsi prehlednost
        scrollPane = new JScrollPane ( createView(), 
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
        scrollPane.getHorizontalScrollBar ().setUnitIncrement (32);
        scrollPane.getHorizontalScrollBar ().setBlockIncrement (256);
        
        scrollPane.getVerticalScrollBar ().setUnitIncrement (32);
        scrollPane.getVerticalScrollBar ().setBlockIncrement (256);
        scrollPane.setPreferredSize(new Dimension(400, 600));
                
        scrollPane.setColumnHeader(null);
        scrollPane.setRowHeaderView(createRowRuler());
        scrollPane.setColumnHeaderView(createColumnRuler());
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        //TODO Smazat
        LabelWidget lw2 = new LabelWidget(this, "Toto je hlavni scena!");
        lw2.getActions().addAction(ActionFactory.createMoveAction(null, moveProvider));
        lw2.getActions().addAction( ActionFactory.createSelectAction(new WidgetSelectProvider(this)) );
        lw2.getActions().addAction( rectangularSelectionAction );
        lw2.setPreferredLocation(new Point(50, 50));
        mainLayer.addChild(lw2);

        
        LabelWidget lw = new LabelWidget(this, "Widget 2");
        
        lw.getActions().addAction(ActionFactory.createMoveAction(null, moveProvider));
        lw.getActions().addAction(0, ActionFactory.createSelectAction(new WidgetSelectProvider(this)) );
        lw.setPreferredLocation(new Point(50, 100));
        mainLayer.addChild(lw);
//        mainLayer.setOpaque(true);
        
        AcceptProvider ap = new AcceptProviderImpl(this);
        
        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createAcceptAction( ap ) );
        getActions().addAction( rectangularSelectionAction );
        getActions().addAction( hoverAction );
//        getActions().addAction(ActionFactory.createRectangularSelectAction(new DefaultRectangularSelectDecorator(this), mainLayer, new WidgetRectangularSelectionProvider() ));
        
    }

    public WidgetAction getMultipleMovementAction() {
        return multipleMovementAction;
    }

    public void addWidget(Widget widget){
        logger.trace("Pridavam widget do sceny:" + widget);
//        widget.getActions().addAction( 0, ActionFactory.createSelectAction(new WidgetSelectProvider(this)) );   //selection action has to be first, It's not consuming action
        widget.getActions().addAction( singleSelectActino );
        widget.getActions().addAction( ActionFactory.createMoveAction(null, moveProvider ));
        widget.getActions().addAction( hoverAction );
        
        mainLayer.addChild(widget);
    }
    
    public LayerWidget getMainLayer(){
        return mainLayer;
    }
    
    /*
    * Vraci obalku sceny pro vlozni do swing komponenty
    */
    public JComponent getSceneEnvelope(){
        return scrollPane;
    }

    public List<Widget> getSelectedWidgets() {
        return selectedWidgets;
    }

    public void setSelectedWidgets(List<Widget> selectedWidgets) {
        //Nastaveni borderu znazornujiciho oznaceny widget (dashed) musi byt volano v Providerovi, jinak pri opakovanem
        //oznaceni nedojde ke spravnemu nastaveni borderu (resp se mu nastavi ale tady by se mu hned zase zrusil)
        this.selectedWidgets = selectedWidgets;
        logger.trace("Selection set: " + selectedWidgets.size() + " " + selectedWidgets);
    }
    
    /**
     * Clears selection and set all widgets as unselected
     * If used, then must be used before <b>setSelectedWidgets<b> method
     */
    public List<Widget> clearSelection(){
        logger.trace("Cleaning selection.");
        List<Widget> selectedList = getSelectedWidgets();
        for(Widget w : selectedList){
            w.setBorder(BorderFactory.createEmptyBorder());
            w.getActions().removeAction( multipleMovementAction );
            logger.trace("Selection cancelled: " + w);
        }
        selectedWidgets = new ArrayList<Widget>();
        return selectedWidgets;
    }

    public void setMultiMoveAction(Widget widget, WidgetAction action){
        logger.trace("");
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
                
            } else {    //there is no action
                widget.getActions().addAction(0, action);
                return;
            }
        } else { //there is no action
            widget.getActions().addAction(action);
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
    
    private JComponent createRowRuler(){
        JLabel rowheader = new JLabel() {
          @Override
          public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Rectangle rect = g.getClipBounds();
            for (int i = 50 - (rect.y % 50); i < rect.height; i += 50) {
              g.drawLine(0, rect.y + i, 3, rect.y + i);
              g.drawString("" + (rect.y + i), 6, rect.y + i + 3);
            }
          }

          @Override
          public Dimension getPreferredSize() {
            //System.out.println("this.getPreferredSize():" + this.getPreferredSize());
            return new Dimension(25, (int) scrollPane.getPreferredSize().getHeight());
          }
        };
        rowheader.setBackground(Color.white);
        rowheader.setOpaque(true);
        return rowheader;
    }
    
    private JComponent createColumnRuler(){
          JLabel columnheader = new JLabel() {

          @Override
          public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Rectangle r = g.getClipBounds();
            for (int i = 50 - (r.x % 50); i < r.width; i += 50) {
              g.drawLine(r.x + i, 0, r.x + i, 3);
              g.drawString("" + (r.x + i), r.x + i - 10, 16);
            }
          }

          @Override
          public Dimension getPreferredSize() {
            return new Dimension((int) scrollPane.getPreferredSize().getWidth(), 25);
          }
        };
        columnheader.setBackground(Color.white);
        columnheader.setOpaque(true);
        
        return columnheader;
    }
    
    
}
