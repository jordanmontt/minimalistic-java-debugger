package dbg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.sun.jdi.*;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.request.BreakpointRequest;
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

    public void handleStep() {
        this.stepRequest.disable();
        this.stepRequest = enableStepIntoRequest(event);
    }

    public void handleStepOver() {
        this.stepRequest.disable();
        this.stepRequest = enableStepOverRequest(event);
    }

    public void handleContinue() {
        this.stepRequest.disable();
    }

    public StackFrame handleFrame() throws IncompatibleThreadStateException {
        this.frame = getStackFrame(0);
        System.out.println(this.frame);
        return this.frame;
    }

    public Map<LocalVariable, Value> handleGetTemporaries() throws IncompatibleThreadStateException, AbsentInformationException {
        this.event.thread().suspend();

        this.frame = getStackFrame(0);
        this.temporaries = this.frame.getValues(this.frame.visibleVariables());
        for (LocalVariable v : this.frame.visibleVariables()) {
            System.out.println(v.name() + " -> " + this.temporaries.get(v));
        }
        this.event.thread().resume();
        return this.temporaries;
    }

    public List<StackFrame> handleGetStack() throws IncompatibleThreadStateException {
        this.event.thread().suspend();
        this.stack = this.event.thread().frames();
        for (StackFrame frame : this.stack) {
            System.out.println(frame);
        }
        this.event.thread().resume();
        return this.stack;
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

    public Value handlePrintVariable() throws IOException, IncompatibleThreadStateException, AbsentInformationException {
        System.out.println("Enter the name of the variable : ");
        String userInput = getUserInput();
        this.frame = getStackFrame(0);
        Value val = this.frame.getValue(this.frame.visibleVariableByName(userInput));
        System.out.println(val);
        return val;
    }
    
    public void handleBreak() throws IOException, AbsentInformationException, ClassNotFoundException {
    	System.out.println("Enter the name of the file : ");
        String userInput1 = getUserInput();
        System.out.println("Enter the number of the line : ");
        String userInput2 = getUserInput();
        setBreakPoint("dbg." + userInput1, Integer.parseInt(userInput2));
    }

    public List<LocalVariable> handleGetMethodArguments() throws IncompatibleThreadStateException, AbsentInformationException {
        this.executedMethod = getExecutedMethod();
        this.executedMethodArguments = this.executedMethod.arguments();
        for (LocalVariable localVar : this.executedMethodArguments) {
            System.out.println(localVar);
        }
        return this.executedMethodArguments;
    }

    public Method handleGetExecutedMethod() throws IncompatibleThreadStateException {
        this.executedMethod = getExecutedMethod();
        System.out.println(this.executedMethod.name());
        return this.executedMethod;
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

    private ObjectReference getReceiverOfFrame(StackFrame frame) {
        return frame.thisObject();
    }

    private String getUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private Method getExecutedMethod() throws IncompatibleThreadStateException {
        StackFrame frame = getStackFrame(0);
        this.event.thread().suspend();
        Method method = frame.location().method();
        this.event.thread().resume();
        return method;
    }

    private StackFrame getStackFrame(int index) throws IncompatibleThreadStateException {
        return this.event.thread().frame(index);
    }
    
    private void setBreakPoint(String className, int lineNumber) throws AbsentInformationException {
        for (ReferenceType targetClass : vm.allClasses()) {
            if (targetClass.name().equals(className)) {
                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(location);
                bpReq.enable();
            }
        }
    }

}
