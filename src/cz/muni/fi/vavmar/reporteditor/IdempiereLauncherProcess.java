package cz.muni.fi.vavmar.reporteditor;

import org.compiere.process.SvrProcess;

public class IdempiereLauncherProcess extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		MainFrame.runProgram();
		return null;
	}

}
