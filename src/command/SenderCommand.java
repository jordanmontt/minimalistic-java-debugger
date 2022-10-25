package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class SenderCommand implements InputCommand {

    VMHandler ir;

    public SenderCommand(VMHandler ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        try {
            this.ir.handleGetSender();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        }

    }
}
