package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ArgumentsCommand implements InputCommand {
    VMHandler vmHandler;

    public ArgumentsCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.handleGetMethodArguments();
        } catch (IncompatibleThreadStateException | AbsentInformationException e) {
            throw new RuntimeException(e);
        }
    }
}
