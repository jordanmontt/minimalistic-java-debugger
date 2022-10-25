package dbg;

import java.util.Map;

import com.sun.jdi.*;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.request.StepRequest;


public class InputReceiver {

    private final VirtualMachine vm;
    private StepRequest stepRequest;
    private LocatableEvent event;
    private StackFrame frame;
    private Map<LocalVariable, Value> temporaries;

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
        if (this.stepRequest == null) {
            this.stepRequest = enableStepIntoRequest(event);
        } else {
            this.stepRequest.disable();
            this.stepRequest = enableStepIntoRequest(event);
        }
    }

    public void stepOverHandler() {
        if (this.stepRequest == null) {
            this.stepRequest = enableStepOverRequest(event);
        } else {
            this.stepRequest.disable();
            this.stepRequest = enableStepOverRequest(event);
        }
    }

    public void continueHandler() {
        if (this.stepRequest != null) {
            this.stepRequest.disable();
        }
    }

    public void frameHandler() {
        try {
            this.event.thread().suspend();
            this.frame = this.event.thread().frame(0);
            System.out.println(this.frame);
            this.event.thread().resume();
        } catch (IncompatibleThreadStateException e) {
            e.printStackTrace();
        }
    }
    
    public void temporariesHandler() {
		try {
			this.event.thread().suspend();
			this.frame = this.event.thread().frame(0);
			this.temporaries = this.frame.getValues(this.frame.visibleVariables());
			for( LocalVariable v : this.frame.visibleVariables() ) {
				System.out.println(v.name() + " -> " + this.temporaries.get(v));
			}
			this.event.thread().resume();
		}catch(IncompatibleThreadStateException e) {
			e.printStackTrace();
		}catch(AbsentInformationException e) {
			e.printStackTrace();
		}
	}

    private StepRequest enableStepIntoRequest(LocatableEvent event) {
        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_MIN, StepRequest.STEP_INTO);
        stepRequest.enable();
        return stepRequest;
    }

    private StepRequest enableStepOverRequest(LocatableEvent event) {
        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_LINE, StepRequest.STEP_OVER);
        stepRequest.enable();
        return stepRequest;
    }

}
