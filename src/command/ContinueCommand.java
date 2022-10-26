package command;

import dbg.VMHandler;

public class ContinueCommand implements InputCommand {

	VMHandler vmHandler;
	
	public ContinueCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler; 
	}
	
	@Override
	public void execute() {
		this.vmHandler.handleContinue();

	}

	@Override
	public boolean isResumable() {
		return true;
	}
}
