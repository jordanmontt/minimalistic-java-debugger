package command;

import java.io.IOException;

import dbg.VMHandler;

public class BreakBeforeMethodCallCommand implements InputCommand {

	VMHandler vmHandler;
	
	public BreakBeforeMethodCallCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleBreakBeforeMethodCall();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

}
