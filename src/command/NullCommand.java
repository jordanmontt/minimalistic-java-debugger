package command;

public class NullCommand implements InputCommand {
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
}
