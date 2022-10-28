package command;

import dbg.VMHandler;

public class ContinueCommand implements DebuggingCommand {

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

	@Override
	public String commandName() {
		return "continue";
	}

	@Override
	public String description() {
		return "continue l’exécution jusqu’au prochain point d’arrêt. La granularité est l’instruction step.";
	}
}
