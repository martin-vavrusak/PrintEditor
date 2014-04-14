/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;
import cz.muni.fi.vavmar.printeditor.actions.ResizeParentByMoveActionProvider;
import cz.muni.fi.vavmar.printeditor.actions.ImageResizeStrategy;
import cz.muni.fi.vavmar.printeditor.actions.WidgetRectangularSelectDecorator;
import cz.muni.fi.vavmar.printeditor.actions.WidgetSelectionAction;
import cz.muni.fi.vavmar.printeditor.actions.WidgetHoverActionProvider;
import cz.muni.fi.vavmar.printeditor.actions.KeyProcessingAction;
import cz.muni.fi.vavmar.printeditor.actions.MultiMoveProvider;
import cz.muni.fi.vavmar.printeditor.actions.WidgetRectangularSelectionProvider;
import cz.muni.fi.vavmar.printeditor.DAO.DataProviderJDBC;
import cz.muni.fi.vavmar.printeditor.tools.ColumnWidget;
import cz.muni.fi.vavmar.printeditor.utils.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.LabelView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.print.MPrintFormat;
import org.compiere.util.Env;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;

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
    
    private DBManager dataProvider;
    
    private WidgetAction hoverAction;
    private WidgetAction rectangularSelectionAction;
    private WidgetAction multipleMovementAction;
    private WidgetAction keyProcessor = new KeyProcessingAction(this);
    private WidgetAction singleSelectActino = new WidgetSelectionAction(this);
    private ImageResizeStrategy resizeStrategyProvider = new ImageResizeStrategy();
    private WidgetAction imageResizeAction = ActionFactory.createResizeAction(resizeStrategyProvider, resizeStrategyProvider.getProvider());
    
    private Set<Widget> selectedWidgets;
    
    private boolean CONTROL_PRESSED = false;
    private static final int SCENE_WIDTH = 600; //width of scene
    private static final int SCENE_HEIGHT = 900; //width of scene
    private static final int A4_ASPECT_RATIO = 3;
    private static final Rectangle A4_BOUNDS = new Rectangle(0, 0, 210 * A4_ASPECT_RATIO + 2,
                                                                   279 * A4_ASPECT_RATIO + 2);
    public MainScene() {
        this(new DataProviderJDBC());
    }
    
    public MainScene(DBManager dataProvider) {
        this.dataProvider = dataProvider;
        setOpaque(true);
        selectedWidgets = new HashSet<Widget>();
        
        moveProvider = new ResizeParentByMoveActionProvider();
        hoverAction = ActionFactory.createHoverAction(new WidgetHoverActionProvider());
        multipleMovementAction = ActionFactory.createMoveAction( null , new MultiMoveProvider( this ));     //Must be before RectangularSelection

        backgroundLayer = new LayerWidget(this);
        backgroundLayer.setPreferredBounds(new Rectangle(-150, -20, A4_BOUNDS.width + 150, A4_BOUNDS.width + 20));
        
        LabelWidget paperRectangle = new LabelWidget(this);
        paperRectangle.setPreferredBounds(A4_BOUNDS);   //velikost A4
        paperRectangle.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED, 1));
        
        backgroundLayer.addChild(paperRectangle);
        
        
        mainLayer = new LayerWidget(this);
//        rectangularSelectionAction = ActionFactory.createRectangularSelectAction(this, mainLayer);
        rectangularSelectionAction = ActionFactory.createRectangularSelectAction(
                                    new WidgetRectangularSelectDecorator(this), backgroundLayer,    //layer where rectangle of selection will be painted
                                    new WidgetRectangularSelectionProvider(this, mainLayer));       //layer whose widgets will be tested for selection
        
        
        
        mainLayer.getActions().addAction( ActionFactory.createResizeAction() );
//        mainLayer.getActions().addAction( rectangularSelectionAction );
        mainLayer.setPreferredBounds(new Rectangle(SCENE_WIDTH, SCENE_HEIGHT));
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
        lw2.getActions().addAction( singleSelectActino );
        lw2.getActions().addAction( rectangularSelectionAction );
        lw2.setPreferredLocation(new Point(50, 50));
        mainLayer.addChild(lw2);

        
        LabelWidget lw = new LabelWidget(this, "Widget 2");
        
        lw.getActions().addAction(ActionFactory.createMoveAction(null, moveProvider));
        lw.getActions().addAction(0, singleSelectActino );
        lw.setPreferredLocation(new Point(50, 100));
        mainLayer.addChild(lw);
//        mainLayer.setOpaque(true);
        
        AcceptProvider ap = new AcceptProviderImpl(this);
        
        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createAcceptAction( ap ) );
        getActions().addAction( rectangularSelectionAction );
        getActions().addAction( hoverAction );
        getActions().addAction( keyProcessor );
//        getActions().addAction(ActionFactory.createRectangularSelectAction(new DefaultRectangularSelectDecorator(this), mainLayer, new WidgetRectangularSelectionProvider() ));
        
        loadSceneDatabase("AD_Printform");
    }

    public WidgetAction getMultipleMovementAction() {
        return multipleMovementAction;
    }

    public WidgetAction getImageResizeAction(){
        return imageResizeAction;
    }
    
    public void addWidget(Widget widget){
        logger.trace("Pridavam widget do sceny:" + widget);
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

    public Set<Widget> getSelectedWidgets() {
        return selectedWidgets;
    }

    public void setSelectedWidgets(Set<Widget> selectedWidgets) {
        //Nastaveni borderu znazornujiciho oznaceny widget (dashed) musi byt volano v Providerovi, jinak pri opakovanem
        //oznaceni nedojde ke spravnemu nastaveni borderu (resp se mu nastavi ale tady by se mu hned zase zrusil)
        this.selectedWidgets = selectedWidgets;
        logger.trace("Selection set: " + selectedWidgets.size() + " " + selectedWidgets);
    }
    
    /**
     * Clears selection and set all widgets as unselected
     * If used, then must be used before <b>setSelectedWidgets<b> method
     */
    public Set<Widget> clearSelection(){
        logger.trace("Cleaning selection.");
        Set<Widget> selectedList = getSelectedWidgets();
        for(Widget w : selectedList){
            w.setBorder(BorderFactory.createEmptyBorder());
            w.getActions().removeAction( multipleMovementAction );
            w.getActions().removeAction( imageResizeAction );           //teoreticky by nemuselo byt resize se nastavuje jenom pri single selectu
            logger.trace("Selection cancelled: " + w);
            
            if(w instanceof ColumnWidget) { ((ColumnWidget) w).resetBorder(); }
        }
        selectedWidgets = new HashSet<Widget>();
        return selectedWidgets;
    }

    public void setMultiMoveAction(Widget widget, WidgetAction action){
        logger.trace("");
        Utils.setMultiMoveAction(widget, action);
    }

    public boolean isControlPressed() {
        return CONTROL_PRESSED;
    }

    public void setControlPressed(boolean pressed) {
        this.CONTROL_PRESSED = pressed;
    }

    public DBManager getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(DBManager dataProvider) {
        this.dataProvider = dataProvider;
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
    
    
    public void loadSceneDatabase(int tableID){
    	MPrintFormat printFormat = new MPrintFormat(Env.getCtx(), tableID, null);
    }
    
    public void loadSceneDatabase(String tableName){
    	//Zjistit ID Tabulky
    	dataProvider.getTableID(tableName);
    }
}