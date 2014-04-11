package cz.muni.fi.vavmar.printeditor;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyClass {
	private static Logger logger = LogManager.getLogger(MyClass.class);
	
	public void startProgram(){
		System.out.println("Starting program...");
		logger.trace("trace: Starting program...");
		logger.debug("debug: Starting program...");
		logger.info("info: Starting program...");
		logger.error("error: Starting program...");
		
		JFrame frame = new JFrame();
		
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(screenSize.width / 2, screenSize.height / 2, 100, 200);
		
		frame.setVisible(true);
	}
}
