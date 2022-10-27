package command;

import java.io.IOException;

import com.sun.jdi.AbsentInformationException;

import dbg.VMHandler;

public class BreakOnCountCommand implements InputCommand {

	VMHandler vmHandler;
	
	public BreakOnCountCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleBreakOnCount();
		} catch(IOException | AbsentInformationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

}
