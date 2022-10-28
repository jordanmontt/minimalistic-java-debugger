package command;

import java.io.IOException;

import com.sun.jdi.AbsentInformationException;

import dbg.VMHandler;

public class BreakOnCountCommand implements InputCommand {

    VMHandler vmHandler;

    public BreakOnCountCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        try {
            this.vmHandler.handleBreakOnCount();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isResumable() {
        return false;
    }

    @Override
    public String commandName() {
        return "break-on-count";
    }

    @Override
    public String description() {
        return "installe un point d’arrêt à la ligne lineNumber du fichier fileName. Ce point d’arrêt ne s’active qu’après avoir été atteint un certain nombre de fois count (les paramètres seront demandés après saisir la commande).";
    }
}
