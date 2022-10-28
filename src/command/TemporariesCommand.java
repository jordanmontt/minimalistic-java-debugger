package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class TemporariesCommand implements InputCommand {

	VMHandler vmHandler;
	
	public TemporariesCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleGetTemporaries();
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
		return "temporaries";
	}

	@Override
	public String description() {
		return "envoie et imprime la liste des variables temporaires de la frame courante, sous la forme de couples nom → valeur.";
	}
}
