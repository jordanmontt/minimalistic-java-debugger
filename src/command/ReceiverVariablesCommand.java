package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ReceiverVariablesCommand implements InputCommand {

    VMHandler vmHandler;

    public ReceiverVariablesCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.receiverVariablesHandler();
        } catch (IncompatibleThreadStateException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            System.out.println("Cannot get receiver variables as the receiver is null");
        }
    }

    @Override
    public boolean isResumable() {
        return false;
    }
}
