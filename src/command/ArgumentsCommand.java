package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ArgumentsCommand implements DebuggingCommand {
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

    @Override
    public boolean isResumable() {
        return false;
    }

    @Override
    public String commandName() {
        return "arguments";
    }

    @Override
    public String description() {
        return "renvoie et imprime la liste des arguments de la méthode en cours d’exécution, sous la forme d’un couple nom → valeur.";
    }
}
