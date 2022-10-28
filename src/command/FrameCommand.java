package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class FrameCommand implements InputCommand {

	VMHandler vmHandler;
	
	public FrameCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler; 
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleFrame();
		} catch (IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean isResumable() {
		return false;
	}

	@Override
	public String commandName() {
		return "frame";
	}

	@Override
	public String description() {
		return "envoie et imprime la frame courante.";
	}
}
