package command;

import dbg.VMHandler;

public class StepOverCommand implements DebuggingCommand {

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

	@Override
	public String commandName() {
		return "step-over";
	}

	@Override
	public String description() {
		return "execute la ligne courante.";
	}
}
