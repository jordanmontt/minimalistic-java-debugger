package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.InputReceiver;

public class ReceiverCommand implements InputCommand {

    InputReceiver ir;

    public ReceiverCommand(InputReceiver ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        this.ir.getReceiverHandler();

    }
}
