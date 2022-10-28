package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ReceiverVariablesCommand implements DebuggingCommand {

    VMHandler vmHandler;

    public ReceiverVariablesCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.handleReceiverVariables();
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

    @Override
    public String commandName() {
        return "receiver-variables";
    }

    @Override
    public String description() {
        return "renvoie et imprime la liste des variables d’instance du receveur courant, sous la forme d’un couple nom → valeur.";
    }
}
