package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class MethodCommand implements InputCommand {
    VMHandler vmHandler;

    public MethodCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.handleGetExecutedMethod();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        }
    }
}
