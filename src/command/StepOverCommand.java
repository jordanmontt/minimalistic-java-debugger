package command;

import dbg.VMHandler;

public class StepOverCommand implements InputCommand {

	VMHandler vmHandler;
	
	public StepOverCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		this.vmHandler.handleStepOver();

	}

	@Override
	public boolean isResumable() {
		return true;
	}
}
