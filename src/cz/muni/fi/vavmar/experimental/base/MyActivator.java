package cz.muni.fi.vavmar.experimental.base;

import java.util.logging.Level;


import org.adempiere.plugin.utils.AdempiereActivator;
import org.compiere.util.CLogger;


public class MyActivator extends AdempiereActivator {

	CLogger logger;
	
	MyActivator (){
		logger = CLogger.getCLogger(MyActivator.class);
	}
	
	@Override
	public String getName() {
		System.out.println("xXxXxXxXxXxXxXxXxXxXxXxX getName xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		logger.log(Level.SEVERE, "xXxXxXxXxXxXxXxXxXxXxXxX Logging: getName xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		throw new RuntimeException("Metoda getName");
		//return "Experimental plugin";
	}

	@Override
	public String getVersion() {
		System.out.println("xXxXxXxXxXxXxXxXxXxXxXxX getVersion xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		logger.log(Level.SEVERE, "xXxXxXxXxXxXxXxXxXxXxXxX Logging: getVersion xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		throw new RuntimeException("Metoda getVersion");
		//return "return 0.1";
	}

	@Override
	public String getDescription() {
		System.out.println("xXxXxXxXxXxXxXxXxXxXxXxX getDescription xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		logger.log(Level.SEVERE, "xXxXxXxXxXxXxXxXxXxXxXxX Logging: getDescription xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		throw new RuntimeException("Metoda getDescription");
		//return "Plugin for experiments with iDempiere";
	}

	@Override
	protected void install() {
		super.install();
		System.out.println("xXxXxXxXxXxXxXxXxXxXxXxX Install xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		logger.log(Level.SEVERE, "xXxXxXxXxXxXxXxXxXxXxXxX Logging: Install xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		throw new RuntimeException("Metoda install");
	}

	@Override
	protected void start() {
		super.start();
		System.out.println("xXxXxXxXxXxXxXxXxXxXxXxX Start xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		logger.log(Level.SEVERE, "xXxXxXxXxXxXxXxXxXxXxXxX Logging: Start xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		throw new RuntimeException("Metoda start");
	}

	@Override
	protected void stop() {
		super.stop();
		System.out.println("xXxXxXxXxXxXxXxXxXxXxXxX Stop xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		logger.log(Level.SEVERE, "xXxXxXxXxXxXxXxXxXxXxXxX Logging: Stop xXxXxXxXxXxXxXxXxXxXxXxXxXxXxX");
		throw new RuntimeException("Metoda stop");
	}

}
