package command;

public class NullCommand implements DebuggingCommand {
    private String userInput;

    public NullCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() {
        System.out.println("The command: " + this.userInput + " does not exist.");
    }

    @Override
    public boolean isResumable() {
        return false;
    }

    @Override
    public String commandName() {
        return "null command";
    }

    @Override
    public String description() {
        return "I am a null command that is only used to avoid ifs.";
    }
}
