package command;

import dbg.VMHandler;

public class ContinueCommand implements InputCommand {

	VMHandler ir;
	
	public ContinueCommand(VMHandler ir) {
		this.ir = ir; 
	}
	
	@Override
	public void execute() {
		this.ir.continueHandler();

	}

}
