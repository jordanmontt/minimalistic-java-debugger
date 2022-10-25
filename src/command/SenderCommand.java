package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.InputReceiver;

public class SenderCommand implements InputCommand {

    InputReceiver ir;

    public SenderCommand(InputReceiver ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        this.ir.getSenderHandler();

    }
}
