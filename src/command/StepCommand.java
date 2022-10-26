package command;

import dbg.VMHandler;

public class StepCommand implements InputCommand {

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
}
