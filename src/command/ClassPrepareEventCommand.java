package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.VirtualMachine;

public class ClassPrepareEventCommand implements EventCommand {

	private EventReceiver er;
	private Class debugClass;
	private VirtualMachine vm;
	
	public ClassPrepareEventCommand(Class debugClass, VirtualMachine vm) {
		this.debugClass = debugClass;
		this.vm = vm;
	}
	
	@Override
	public void execute() {
		try {
			er.ClassPrepareEventHandler(debugClass, vm);
		} catch(AbsentInformationException e) {
			e.printStackTrace();
		}
	}
}
