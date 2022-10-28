package dbg;

import command.*;

import java.util.HashMap;

public class InputInterpreter {
    VMHandler vmHandler;
    HashMap<String, DebuggingCommand> hashMap;

    public InputInterpreter(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
        this.hashMap = new HashMap<>();
        buildHashMap();
    }

    private void buildHashMap() {
        learnCommand(new StepCommand(vmHandler));
        learnCommand(new StepOverCommand(vmHandler));
        learnCommand(new ContinueCommand(vmHandler));
        learnCommand(new FrameCommand(vmHandler));
        learnCommand(new TemporariesCommand(vmHandler));
        learnCommand(new StackCommand(vmHandler));
        learnCommand(new ReceiverCommand(vmHandler));
        learnCommand(new SenderCommand(vmHandler));
        learnCommand(new ReceiverVariablesCommand(vmHandler));
        learnCommand(new MethodCommand(vmHandler));
        learnCommand(new ArgumentsCommand(vmHandler));
        learnCommand(new PrintVarCommand(vmHandler));
        learnCommand(new BreakCommand(vmHandler));
        learnCommand(new BreakpointsCommand(vmHandler));
        learnCommand(new BreakOnceCommand(vmHandler));
        learnCommand(new BreakOnCountCommand(vmHandler));
        learnCommand(new BreakBeforeMethodCallCommand(vmHandler));

        learnCommand(new HelpCommand(vmHandler));
    }

    private DebuggingCommand getCommand(String userInput) {
        if (this.hashMap.containsKey(userInput)) {
            return this.hashMap.get(userInput);
        } else {
            return new NullCommand(userInput);
        }
    }

    private void learnCommand(DebuggingCommand command) {
        this.hashMap.put(command.commandName(), command);
    }

    public void executeCommand(String userInput) {
        getCommand(userInput).execute();
    }

    public boolean isCommandResumable(String userInput) {
        return getCommand(userInput).isResumable();
    }
}
