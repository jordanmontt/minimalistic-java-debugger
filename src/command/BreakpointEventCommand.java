package command;

import com.sun.jdi.event.Event;

import java.io.IOException;

import com.sun.jdi.VirtualMachine;

public class BreakpointEventCommand implements EventCommand {

	EventReceiver er;
	Event event;
	VirtualMachine vm;
	
	public BreakpointEventCommand(Event event, VirtualMachine vm) {
		this.event = event;
		this.vm = vm;
	}
	
	@Override
	public void execute() {
		try {
			er.BreakpointEventHandler(event, vm);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
