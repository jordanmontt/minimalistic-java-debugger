package command;

public class EventCommandInvoker {
	private EventCommand command;
	
	public EventCommandInvoker(EventCommand command) {
		this.command = command;
	}
	
	public void setCommand(EventCommand command) {
		this.command = command;
	}
	
	public void executeCommand() {
		
	}
}
