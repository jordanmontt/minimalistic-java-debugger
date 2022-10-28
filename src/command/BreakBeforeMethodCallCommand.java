package command;

import java.io.IOException;

import dbg.VMHandler;

public class BreakBeforeMethodCallCommand implements DebuggingCommand {

	VMHandler vmHandler;
	
	public BreakBeforeMethodCallCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleBreakBeforeMethodCall();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

	@Override
	public String commandName() {
		return "break-before-method-call";
	}

	@Override
	public String description() {
		return "configure l’exécution pour s’arrêter au tout début de l’exécution de la méthode methodName. (le nom sera demandé après saisir la commande).";
	}
}
