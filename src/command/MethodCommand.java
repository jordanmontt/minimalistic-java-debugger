package command;

import dbg.InputReceiver;

public class MethodCommand implements InputCommand {
    InputReceiver ir;

    public MethodCommand(InputReceiver ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        this.ir.getCurrentExecutedMethodHandler();
    }
}
