package cz.muni.fi.vavmar.printeditor.widgets;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

import cz.muni.fi.vavmar.printeditor.MainScene;
import cz.muni.fi.vavmar.printeditor.dialogs.TableChooserInitDialog;

public class SubreportWidget extends LabelWidget {

	private static final Logger logger = LogManager.getLogger(SubreportWidget.class);
	
	private int subreportPrintformatID;
	private String name;
	private MainScene mainScene;
	private boolean isRelativePositioned = true;
	
	public SubreportWidget(MainScene scene) {
		super(scene);
		this.mainScene = scene;
		// TODO Auto-generated constructor stub
	}

	public SubreportWidget(MainScene scene, String label) {
		super(scene, label);
		this.mainScene = scene;
		// TODO Auto-generated constructor stub
	}

	public boolean showDialog(){
		
		logger.trace("Opening report dialog.");
		
		TableChooserInitDialog dialog = new TableChooserInitDialog(mainScene.getDataProvider(), true);
		dialog.setVisible(true);
		
		int subreportID = dialog.getSelectedPrintFormatID();
		String subreportName = dialog.getSelectedPrintFormatName();
		
		if(subreportID > -1 && subreportName != null){
			this.name = subreportName; 
			this.subreportPrintformatID = subreportID;
			setLabel("Subreport: " + this.name);
			logger.trace("New subreport set. ID: " + subreportID + " Name: '" + subreportName + "'.");
			return true;
		}
		
		logger.trace("Incalid data returned. ID: " + subreportID + " Name: '" + subreportName + "'.");
		return false;
	}
	
	public int getSubreportPrintformatID() {
		return subreportPrintformatID;
	}

	public String getName() {
		return name;
	}

	public boolean isRelativePositioned() {
		return isRelativePositioned;
	}

	public void setRelativePositioned(boolean isRelativePositioned) {
		this.isRelativePositioned = isRelativePositioned;
	}
	
	
	
	/**
	 * Finish initialization of widget. Set PopupMenu provider.
	 */
	public void initializeMenu(){
		getActions().addAction(ActionFactory.createPopupMenuAction( new SubreportPopupMenuProvider()) );
	}
	
	
	private class SubreportPopupMenuProvider implements PopupMenuProvider, ActionListener {
		private static final String RELATIVE_POSITON_OPTION = "RelativePosition";
		private static final String RELATIVE_POSITON_LABEL = "Relative position";
		
		private static final String ABSOLUTE_POSITON_OPTION = "AbsolutePosition";
		private static final String ABSOLUTE_POSITON_LABEL = "Absolute position";
		
		
		private Widget ownerWidget;
		
		@Override
		public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
			ownerWidget = widget;

			JPopupMenu menu = new JPopupMenu("Menu");
            JMenuItem menuItem;       
            
            ButtonGroup group = new ButtonGroup();
            
            menuItem = new JRadioButtonMenuItem(RELATIVE_POSITON_LABEL);
            menuItem.setActionCommand(RELATIVE_POSITON_OPTION);
            menuItem.addActionListener(this);
            menuItem.setSelected(isRelativePositioned);
            group.add(menuItem);
            menu.add(menuItem);
            
            menuItem = new JRadioButtonMenuItem(ABSOLUTE_POSITON_LABEL);
            menuItem.setActionCommand(ABSOLUTE_POSITON_OPTION);
            menuItem.addActionListener(this);
            menuItem.setSelected(!isRelativePositioned);
            group.add(menuItem);
            menu.add(menuItem);
	            
			return menu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			logger.trace("");
			String command = e.getActionCommand();
			if( RELATIVE_POSITON_OPTION.equals(command) ){
				logger.trace("Relative positioning selected.");
				isRelativePositioned = true;
				
			} else if( ABSOLUTE_POSITON_OPTION.equals(command) ) {
				logger.trace("Absolute positioning selected.");
				isRelativePositioned = false;
			}
		}
	}
}
