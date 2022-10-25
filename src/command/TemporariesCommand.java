package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class TemporariesCommand implements InputCommand {

	VMHandler ir;
	
	public TemporariesCommand(VMHandler ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		try {
			this.ir.handleGetTemporaries();
		} catch (IncompatibleThreadStateException | AbsentInformationException e) {
			throw new RuntimeException(e);
		}
	}

}
