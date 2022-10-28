package command;

import java.io.IOException;

import com.sun.jdi.AbsentInformationException;

import dbg.VMHandler;

public class BreakOnceCommand implements InputCommand {

	VMHandler vmHandler;
	
	public BreakOnceCommand(VMHandler vmHandler) {
		this.vmHandler = vmHandler;
	}
	
	@Override
	public void execute() {
		try {
			this.vmHandler.handleBreakOnce();
		} catch(IOException | AbsentInformationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isResumable() {
		return false;
	}

	@Override
	public String commandName() {
		return "break-once";
	}

	@Override
	public String description() {
		return "installe un point d’arrêt à la ligne lineNumber du fichier fileName. Ce point d’arrêt se désinstalle après avoir été atteint (les paramètres seront demandés après saisir la commande).";
	}
}
