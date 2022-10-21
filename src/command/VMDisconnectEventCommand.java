package command;

import java.io.IOException;

import com.sun.jdi.VirtualMachine;

public class VMDisconnectEventCommand implements EventCommand {

	private EventReceiver er;
	private VirtualMachine vm;
	
	public VMDisconnectEventCommand(VirtualMachine vm) {
		this.vm = vm;
	}
	
	@Override
	public void execute() {
		try {
			er.VMDisconnectEventHandler(vm);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
