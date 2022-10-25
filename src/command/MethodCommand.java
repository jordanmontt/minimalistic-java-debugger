package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class MethodCommand implements InputCommand {
    VMHandler ir;

    public MethodCommand(VMHandler ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        try {
            this.ir.handleGetBeingExecutedMethod();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        }
    }
}
