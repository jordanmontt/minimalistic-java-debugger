package command;

import dbg.VMHandler;

public class BreakPointsCommand implements InputCommand {
	
	VMHandler vmHandler;
	
	public BreakPointsCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}

	@Override
	public void execute() {
		this.vmHandler.handleBreakPoints();
	}

	@Override
	public boolean isResumable() {
		return false;
	}

}
