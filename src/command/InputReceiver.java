package command;

import com.sun.jdi.*;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.request.StepRequest;


public class InputReceiver {
	
	private VirtualMachine vm;
	private StepRequest stepRequest;
	private LocatableEvent event;

	public InputReceiver(VirtualMachine vm, StepRequest stepRequest) {
		this.vm = vm;
		this.stepRequest = stepRequest;
	}
	
	public LocatableEvent getEvent() {
		return event;
	}

	public void setEvent(LocatableEvent event) {
		this.event = event;
	}
	
	public void stepHandler() {
		if(this.stepRequest == null) {
			this.stepRequest = enableStepIntoRequest(event);
		}
		else {
			this.stepRequest.disable();
			this.stepRequest = enableStepIntoRequest(event);
		}
	}
	
	private StepRequest enableStepIntoRequest(LocatableEvent event) {
		StepRequest stepRequest = vm.eventRequestManager()
				.createStepRequest(event.thread(), StepRequest.STEP_MIN, StepRequest.STEP_INTO);
		stepRequest.enable();
		return stepRequest;
	}

}
