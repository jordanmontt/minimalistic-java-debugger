package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ReceiverCommand implements InputCommand {

    VMHandler vmHandler;

    public ReceiverCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.handleGetReceiver();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isResumable() {
        return false;
    }
}
