package command;

import java.io.IOException;
import com.sun.jdi.AbsentInformationException;
import dbg.VMHandler;

public class BreakCommand implements InputCommand {

	VMHandler vmHandler;
	
	public BreakCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleBreak();
		}catch(IOException e) {
			throw new RuntimeException(e);
		}catch(AbsentInformationException e) {
			throw new RuntimeException(e);
		}catch(ClassNotFoundException e) {
			throw new RuntimeException (e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

}
