package command;

import dbg.VMHandler;

public class BreakpointsCommand implements InputCommand {
	
	VMHandler vmHandler;
	
	public BreakpointsCommand(VMHandler vmHandler) {
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

	@Override
	public String commandName() {
		return "breakpoints";
	}

	@Override
	public String description() {
		return "liste les points d’arrêts actifs et leurs location dans le code.";
	}
}
