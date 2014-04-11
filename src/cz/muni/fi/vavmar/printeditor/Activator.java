package cz.muni.fi.vavmar.printeditor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;

public class Activator implements BundleActivator {

	private static BundleContext context;
//	private static Logger logger = LogManager.getLogger(Activator.class);
	static BundleContext getContext() {
		return context;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Bundle activated.");
		Activator.context = bundleContext;
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			
			@Override
			public void run() {
				MyClass myClass = new MyClass();
				myClass.startProgram();
			}
		});
		System.out.println("Program started.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Bundle stopped.");
		Activator.context = null;
	}

}
