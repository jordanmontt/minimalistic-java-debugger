package command;

import dbg.InputReceiver;

public class ReceiverVariablesCommand implements InputCommand {

	InputReceiver ir;
	
	public ReceiverVariablesCommand(InputReceiver ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.receiverVariablesHandler();

	}

}
