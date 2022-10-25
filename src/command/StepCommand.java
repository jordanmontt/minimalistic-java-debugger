package command;

import dbg.VMHandler;

public class StepCommand implements InputCommand{

	VMHandler ir;
	
	public StepCommand(VMHandler ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.stepHandler();
	}
}
