package command;

public interface InputCommand {
	
	void execute();
	boolean isResumable();
	String commandName();
	String description();
}
