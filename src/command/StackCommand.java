package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class StackCommand implements InputCommand {

	VMHandler vmHandler;
	
	public StackCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleGetStack();
		} catch (IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}

	}

}
