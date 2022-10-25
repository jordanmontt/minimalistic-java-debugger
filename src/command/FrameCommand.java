package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class FrameCommand implements InputCommand {

	VMHandler ir;
	
	public FrameCommand(VMHandler ir) {
		this.ir = ir; 
	}
	
	@Override
	public void execute() {
		try {
			this.ir.handleFrame();
		} catch (IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}

	}

}
