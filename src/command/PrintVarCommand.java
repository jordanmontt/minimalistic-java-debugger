package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

import java.io.IOException;

public class PrintVarCommand implements DebuggingCommand {

	VMHandler vmHandler;
	
	public PrintVarCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handlePrintVariable();
		} catch (IOException | AbsentInformationException | IncompatibleThreadStateException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

	@Override
	public String commandName() {
		return "print-var";
	}

	@Override
	public String description() {
		return "imprime la valeur de la variable passée en paramètre (le nom de la variable sera demandé après saisir la commande).";
	}
}
