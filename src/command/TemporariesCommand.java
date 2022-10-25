package command;

import dbg.InputReceiver;

public class TemporariesCommand implements InputCommand {

	InputReceiver ir;
	
	public TemporariesCommand(InputReceiver ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.temporariesHandler();

	}

}
