package command;

import java.io.IOException;

import com.sun.jdi.AbsentInformationException;

import dbg.VMHandler;

public class BreakOnceCommand implements InputCommand {

	VMHandler vmHandler;
	
	public BreakOnceCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleBreakOnce();
		} catch(IOException | AbsentInformationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

}
