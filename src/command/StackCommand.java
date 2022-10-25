package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class StackCommand implements InputCommand {

	VMHandler ir;
	
	public StackCommand(VMHandler ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		try {
			this.ir.handleGetStack();
		} catch (IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}

	}

}
