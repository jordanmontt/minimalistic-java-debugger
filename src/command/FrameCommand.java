package command;

import dbg.InputReceiver;

public class FrameCommand implements InputCommand {

	InputReceiver ir;
	
	public FrameCommand(InputReceiver ir) {
		this.ir = ir; 
	}
	
	@Override
	public void execute() {
		this.ir.frameHandler();

	}

}
