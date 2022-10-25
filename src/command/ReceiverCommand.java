package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ReceiverCommand implements InputCommand {

    VMHandler ir;

    public ReceiverCommand(VMHandler ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        try {
            this.ir.handleGetReceiver();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        }

    }
}
