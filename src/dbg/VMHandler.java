package dbg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.sun.jdi.*;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.request.StepRequest;


public class VMHandler {

    private final VirtualMachine vm;
    private StepRequestHandler stepRequest;
    private LocatableEvent event;
    private StackFrame frame;
    private Map<LocalVariable, Value> temporaries;
    private List<StackFrame> stack;
    private ObjectReference receiver;
    private ObjectReference sender;
    private Map<Field, Value> receiverTempVariables;
    private Method executedMethod;
    private List<LocalVariable> executedMethodArguments;

    public VMHandler(VirtualMachine vm) {
        this.stepRequest = new NullStepRequest();
        this.vm = vm;
    }

    public void setEvent(LocatableEvent event) {
        this.event = event;
    }

    public void stepHandler() {
        this.stepRequest.disable();
        this.stepRequest = enableStepIntoRequest(event);
    }

    public void stepOverHandler() {
        this.stepRequest.disable();
        this.stepRequest = enableStepOverRequest(event);
    }

    public void continueHandler() {
        this.stepRequest.disable();
    }

    public void handleFrame() throws IncompatibleThreadStateException {
        this.frame = getStackFrame(0);
        System.out.println(this.frame);
    }

    public void handleGetTemporaries() throws IncompatibleThreadStateException, AbsentInformationException {
        this.frame = getStackFrame(0);
        this.temporaries = this.frame.getValues(this.frame.visibleVariables());
        for (LocalVariable v : this.frame.visibleVariables()) {
            System.out.println(v.name() + " -> " + this.temporaries.get(v));
        }
    }

    public void handleGetStack() throws IncompatibleThreadStateException {
        this.event.thread().suspend();
        this.stack = this.event.thread().frames();
        this.event.thread().resume();
    }

    private StepRequestHandler enableStepIntoRequest(LocatableEvent event) {
        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_MIN, StepRequest.STEP_INTO);
        stepRequest.enable();
        return new StepRequestHandlerImpl(stepRequest);
    }

    private StepRequestHandler enableStepOverRequest(LocatableEvent event) {
        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_LINE, StepRequest.STEP_OVER);
        stepRequest.enable();
        return new StepRequestHandlerImpl(stepRequest);
    }

    public ObjectReference handleGetReceiver() throws IncompatibleThreadStateException {
        this.receiver = getReceiverOfFrame(getStackFrame(0));
        System.out.println(this.receiver);
        return this.receiver;
    }

    public ObjectReference handleGetSender() throws IncompatibleThreadStateException {
        this.sender = getReceiverOfFrame(getStackFrame(1));
        System.out.println(this.sender);
        return this.sender;
    }

    public ObjectReference receiverVariablesHandler() throws IncompatibleThreadStateException {
        this.receiver = getReceiverOfFrame(getStackFrame(0));
        this.receiverTempVariables = this.receiver.getValues(this.receiver.referenceType().allFields());
        System.out.println(this.receiverTempVariables);
        return this.receiver;
    }

    private ObjectReference getReceiverOfFrame(StackFrame frame) {
        return frame.thisObject();
    }

    public void printVarHandler() throws IOException, IncompatibleThreadStateException, AbsentInformationException {
        System.out.println("Enter the name of the variable : ");
        String userInput = getUserInput();
        this.frame = getStackFrame(0);
        Value val = this.frame.getValue(this.frame.visibleVariableByName(userInput));
        System.out.println(val);
    }

    private String getUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public Method handleGetBeingExecutedMethod() throws IncompatibleThreadStateException {
        this.executedMethod = getExecutedMethod();
        System.out.println(this.executedMethod.name());
        return this.executedMethod;
    }

    private Method getExecutedMethod() throws IncompatibleThreadStateException {
        return getStackFrame(0).location().method();
    }

    private StackFrame getStackFrame(int index) throws IncompatibleThreadStateException {
        this.event.thread().suspend();
        StackFrame frame = this.event.thread().frame(index);
        this.event.thread().resume();
        return frame;
    }

    public List<LocalVariable> getMethodArguments() throws IncompatibleThreadStateException, AbsentInformationException {
        this.executedMethod = getExecutedMethod();
        this.executedMethodArguments = this.executedMethod.arguments();
        for (LocalVariable localVar : this.executedMethodArguments) {
            System.out.println(localVar);
        }
        return this.executedMethodArguments;
    }
}
