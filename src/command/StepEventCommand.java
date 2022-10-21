package command;

import java.io.IOException;

public class StepEventCommand implements EventCommand{

	EventReceiver er;
	
	public StepEventCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			er.StepEventHandler();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
