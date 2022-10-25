package command;

import dbg.InputReceiver;

public class StepOverCommand implements InputCommand {

	InputReceiver ir;
	
	public StepOverCommand(InputReceiver ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.stepOverHandler();

	}

}
