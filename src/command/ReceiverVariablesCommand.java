package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ReceiverVariablesCommand implements InputCommand {

	VMHandler ir;
	
	public ReceiverVariablesCommand(VMHandler ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		try {
			this.ir.receiverVariablesHandler();
		} catch (IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}
	}

}
