package command;

import dbg.InputReceiver;

public class StackCommand implements InputCommand {

	InputReceiver ir;
	
	public StackCommand(InputReceiver ir) {
		this.ir = ir;
	}
	@Override
	public void execute() {
		this.ir.stackHandler();

	}

}
