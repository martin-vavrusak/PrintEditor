package cz.muni.fi.vavmar.printeditor.tools;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import cz.muni.fi.vavmar.printeditor.MainScene;
import cz.muni.fi.vavmar.printeditor.widgets.SubreportWidget;

public class SubreportTool extends AbstractTool {

	/**
	 * Create Subreport object with label text and specified icon.
	 * 
	 * @param text Name of the tool to be shown in scene to the user.
	 * @param iconPath Patch to the icon for this tool. If not supplied th default icon is used.
	 */
	public SubreportTool(String text, String iconPath) {
		super(text, iconPath);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5823981143112490732L;

	@Override
	public Widget createWidget(MainScene scene) {
		SubreportWidget subreportWidget = new SubreportWidget(scene);
		boolean succesfullySelected = subreportWidget.showDialog();			//show dialog and let user to choose subreport
		
		subreportWidget.setOpaque(true);									
		subreportWidget.initializeMenu();
		
		if(succesfullySelected){
			return subreportWidget;											//if successfully selected, pass new widget
		}
		
		return null;
	}

	public void showSubreportChooser(){
		
	}
}
