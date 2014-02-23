/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.shapesample;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Martin
 */
public class GraphSceneImpl extends GraphScene<MyNode, String>{

    private LayerWidget mainLayer;
    private WidgetAction editorAction = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor());
    
    public GraphSceneImpl() {
        
//        LabelWidget lw1 = new LabelWidget(this, "Layer widget1");
//        lw1.getActions().addAction(ActionFactory.createMoveAction());
//        lw1.setPreferredLocation(new Point(20, 20));
//        
//        LabelWidget lw2 = new LabelWidget(this, "Layer widget1");
//        lw2.getActions().addAction(ActionFactory.createMoveAction());
//        lw2.setPreferredLocation(new Point(50, 50));
        
//        addChild(lw1);
//        addChild(lw2);
        
        mainLayer = new LayerWidget (this);
        addChild (mainLayer);
        
//        mainLayer.addChild(lw1);
//        mainLayer.addChild(lw2);
        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {

                @Override
                public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                    Image dragImage = getImageFromTransferable(transferable);
                    JComponent view = getView();
                    Graphics2D g2 = (Graphics2D) view.getGraphics();
                    Rectangle visRect = view.getVisibleRect();
                    view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
                    g2.drawImage(dragImage, //umozni vykreslit nad pracovni plochou tazeny tvar
                            AffineTransform.getTranslateInstance(point.getLocation().getX(),
                            point.getLocation().getY()),
                            null);
                    return ConnectorState.ACCEPT;
                }

                @Override
                public void accept(Widget widget, Point point, Transferable transferable) {
                    Image image = getImageFromTransferable(transferable);
                    Widget w = GraphSceneImpl.this.addNode(new MyNode(image));
                    w.setPreferredLocation(widget.convertLocalToScene(point));
                }

                }));
    }
    
    private Image getImageFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(DataFlavor.imageFlavor);
        } catch (IOException ex) {
        } catch (UnsupportedFlavorException ex) {
        }
        return o instanceof Image ? (Image) o : ImageUtilities.loadImage("org/netbeans/shapesample/palette/shape1.png");
    }

    @Override
    protected Widget attachNodeWidget(MyNode node) {
        IconNodeWidget widget = new IconNodeWidget(this);
        widget.setImage(node.getImage());
        widget.setLabel(Long.toString(node.hashCode()));
        
        
        //double-click, the event is consumed while double-clicking only:
        widget.getLabelWidget().getActions().addAction(editorAction);
        //single-click, the event is not consumed:
        widget.getActions().addAction(createSelectAction());
        //mouse-dragged, the event is consumed while mouse is dragged:
        widget.getActions().addAction(ActionFactory.createMoveAction());
        //mouse-over, the event is consumed while the mouse is over the widget:
        widget.getActions().addAction(createObjectHoverAction());
        
        mainLayer.addChild(widget);
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(String edge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeSourceAnchor(String edge, MyNode oldSourceNode, MyNode sourceNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeTargetAnchor(String edge, MyNode oldTargetNode, MyNode targetNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    private class LabelTextFieldEditor implements TextFieldInplaceEditor {

        public boolean isEnabled(Widget widget) {
            return true;
        }

        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }

        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel(text);
        }

    }   

}
