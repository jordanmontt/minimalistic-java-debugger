package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

import java.io.IOException;

public class PrintVarCommand implements InputCommand {

	VMHandler ir;
	
	public PrintVarCommand(VMHandler ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		try {
			this.ir.printVarHandler();
		} catch (IOException | AbsentInformationException | IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}
	}

}
