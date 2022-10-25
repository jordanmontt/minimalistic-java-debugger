package command;

import dbg.VMHandler;

public class StepOverCommand implements InputCommand {

	VMHandler ir;
	
	public StepOverCommand(VMHandler ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.stepOverHandler();

	}

}
