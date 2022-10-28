package command;

import dbg.VMHandler;

public class StepCommand implements DebuggingCommand {

    VMHandler vmHandler;

    public StepCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        this.vmHandler.handleStep();
    }

    @Override
    public boolean isResumable() {
        return true;
    }

    @Override
    public String commandName() {
        return "step";
    }

    @Override
    public String description() {
        return "execute la prochaine instruction. S’il s’agit d’un appel de méthode, l’exécution entre dans cette dernière.";
    }
}
