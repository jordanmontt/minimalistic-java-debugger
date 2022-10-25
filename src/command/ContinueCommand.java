package command;

import dbg.InputReceiver;

public class ContinueCommand implements InputCommand {

	InputReceiver ir;
	
	public ContinueCommand(InputReceiver ir) {
		this.ir = ir; 
	}
	
	@Override
	public void execute() {
		this.ir.continueHandler();

	}

}
