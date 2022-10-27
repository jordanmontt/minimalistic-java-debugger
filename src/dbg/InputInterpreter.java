package dbg;

import command.*;

import java.util.HashMap;

public class InputInterpreter {
    VMHandler vmHandler;
    HashMap<String, InputCommand> hashMap;

    public InputInterpreter(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
        this.hashMap = new HashMap<>();
        buildHashMap();
    }

    private void buildHashMap() {
        this.hashMap.put("step", new StepCommand(vmHandler));
        this.hashMap.put("step-over", new StepOverCommand(vmHandler));
        this.hashMap.put("continue", new ContinueCommand(vmHandler));
        this.hashMap.put("frame", new FrameCommand(vmHandler));
        this.hashMap.put("temporaries", new TemporariesCommand(vmHandler));
        this.hashMap.put("stack", new StackCommand(vmHandler));
        this.hashMap.put("receiver", new ReceiverCommand(vmHandler));
        this.hashMap.put("sender", new SenderCommand(vmHandler));
        this.hashMap.put("receiver-variables", new ReceiverVariablesCommand(vmHandler));
        this.hashMap.put("method", new MethodCommand(vmHandler));
        this.hashMap.put("arguments", new ArgumentsCommand(vmHandler));
        this.hashMap.put("print-var", new PrintVarCommand(vmHandler));
        this.hashMap.put("break", new BreakCommand(vmHandler));
        this.hashMap.put("breakpoints", new BreakPointsCommand(vmHandler));
        this.hashMap.put("break-once", new BreakOnceCommand(vmHandler));
        this.hashMap.put("break-on-count", new BreakOnCountCommand(vmHandler));
        this.hashMap.put("break-before-method-call", new BreakBeforeMethodCallCommand(vmHandler));
    }

    private InputCommand getCommand(String userInput) {
        if (this.hashMap.containsKey(userInput)) {
            return this.hashMap.get(userInput);
        } else {
            return new NullCommand(userInput);
        }
    }

    public void executeCommand(String userInput) {
        getCommand(userInput).execute();
    }

    public boolean isCommandResumable(String userInput) {
        return getCommand(userInput).isResumable();
    }
}
