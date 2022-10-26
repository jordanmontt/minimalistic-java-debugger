package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

import java.io.IOException;

public class PrintVarCommand implements InputCommand {

	VMHandler vmHandler;
	
	public PrintVarCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handlePrintVariable();
		} catch (IOException | AbsentInformationException | IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}
	}

}
