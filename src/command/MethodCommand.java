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

    @Override
    public boolean isResumable() {
        return false;
    }

    @Override
    public String commandName() {
        return "method";
    }

    @Override
    public String description() {
        return "envoie et imprime la méthode en cours d’exécution.";
    }
}
