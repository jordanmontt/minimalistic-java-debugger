package command;

import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class SenderCommand implements DebuggingCommand {

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
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Cannot get the sender as there is no receiver");
        }
    }

    @Override
    public boolean isResumable() {
        return false;
    }

    @Override
    public String commandName() {
        return "sender";
    }

    @Override
    public String description() {
        return "envoie l’objet qui a appelé la méthode courante.";
    }
}
