package command;

public interface DebuggingCommand {
	
	void execute();
	boolean isResumable();
	String commandName();
	String description();
}
