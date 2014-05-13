package cz.muni.fi.vavmar.reporteditor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class EditorActivator implements BundleActivator {
	private static final Logger logger = LogManager.getLogger(EditorActivator.class);
	
	@Override
	public void start(BundleContext context) throws Exception {
		logger.trace("Starting main frame.");
//		MainFrame mainFrame = new MainFrame();
//		mainFrame.runProgram();
		
		MainFrame.runProgram();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logger.trace("Stopping bundle.");
	}

}
