package command;

import dbg.InputReceiver;

public class PrintVarCommand implements InputCommand {

	InputReceiver ir;
	
	public PrintVarCommand(InputReceiver ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.printVarHandler();

	}

}
