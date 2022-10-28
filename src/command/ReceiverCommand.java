package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ReceiverCommand implements DebuggingCommand {

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

    @Override
    public String commandName() {
        return "receiver";
    }

    @Override
    public String description() {
        return "envoie le receveur de la méthode courante (this).";
    }
}
