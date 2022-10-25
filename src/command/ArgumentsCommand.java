package command;

import dbg.InputReceiver;

public class ArgumentsCommand implements InputCommand {
    InputReceiver ir;

    public ArgumentsCommand(InputReceiver ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        this.ir.getMethodArguments();
    }
}
