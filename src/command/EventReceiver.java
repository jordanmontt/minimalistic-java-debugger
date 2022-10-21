package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.StepRequest;


public class EventReceiver {
	
	private StepRequest stepRequest;

	public EventReceiver() {
		stepRequest = null;
	}
	
	//EventHandlers
	public void VMDisconnectEventHandler(VirtualMachine vm) throws IOException {
		System.out.println("===End of program.");
		System.out.println("Debugee output ===");
		InputStreamReader reader = 
				new InputStreamReader(vm.process().getInputStream());
		OutputStreamWriter writer = 
				new OutputStreamWriter(System.out);
		char[] buf = new char[vm.process().getInputStream().available()];
		reader.read(buf);
		writer.write(buf);
		writer.flush();
		return;
	}
	
	public void ClassPrepareEventHandler(Class debugClass, VirtualMachine vm) throws AbsentInformationException{
		setBreakPoint(debugClass.getName(), 6, vm);
	}
	
	public void BreakpointEventHandler(Event event, VirtualMachine vm) throws IOException {
		String userInput = getUserInput();
		if (doesInputContainsStep(userInput)) {
			stepRequest = enableStepRequest((BreakpointEvent) event, vm);
		}
	}
	
	public void StepEventHandler() throws IOException {
		String userInput = getUserInput();
		if (!doesInputContainsStep(userInput)) {
			stepRequest.disable();
		}
	}
	
	//Methods
	private String getUserInput() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	private boolean doesInputContainsStep(String userInput) {
		return userInput.contains("step");
	}
	
	private StepRequest enableStepRequest(BreakpointEvent event, VirtualMachine vm) {
    	StepRequest stepRequest = vm.eventRequestManager().
				createStepRequest(event.thread(),StepRequest.STEP_MIN, StepRequest.STEP_OVER);
				stepRequest.enable();
		return stepRequest;
		
	}
	
	private void setBreakPoint(String className, int lineNumber, VirtualMachine vm) throws AbsentInformationException {
		for(ReferenceType targetClass : vm.allClasses()) {
			if(targetClass.name().equals(className)) {
				Location location = 
						targetClass.locationsOfLine(lineNumber).get(0);
				BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(location);
				bpReq.enable();
			}
		}
		
	}
}
