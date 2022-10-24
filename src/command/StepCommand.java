package command;

public class StepCommand implements InputCommand{

	InputReceiver ir;
	
	public StepCommand(InputReceiver ir) {
		this.ir = ir;
	}
	
	@Override
	public void execute() {
		this.ir.stepHandler();
	}
}
