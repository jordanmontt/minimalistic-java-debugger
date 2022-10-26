package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class SenderCommand implements InputCommand {

    VMHandler vmHandler;

    public SenderCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.handleGetSender();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isResumable() {
        return false;
    }
}
