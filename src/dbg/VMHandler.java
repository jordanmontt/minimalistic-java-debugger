package dbg;

import com.sun.jdi.*;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.StepRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

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
    private String breakOnCountFile;
    private int breakOnCountLine;
    private int breakOnCountCounter;

    public VMHandler(VirtualMachine vm) {
        this.stepRequest = new NullStepRequest();
        this.vm = vm;
        this.breakOnCountFile = "";
        this.breakOnCountLine = -1;
        this.breakOnCountCounter = -1;

    }

    public void setEvent(LocatableEvent event) {
        this.event = event;
    }

    public String getBreakOnCountFile() {
        return breakOnCountFile;
    }

    public int getBreakOnCountLine() {
        return breakOnCountLine;
    }

    public int getBreakOnCountCounter() {
        return breakOnCountCounter;
    }

    public void setBreakOnCountCounter(int breakOnCountCounter) {
        this.breakOnCountCounter = breakOnCountCounter;
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

    public ObjectReference handleReceiverVariables() throws IncompatibleThreadStateException {
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
        String fileName = getUserInput();
        System.out.println("Enter the number of the line : ");
        int lineNumber = Integer.parseInt(getUserInput());
        setBreakPoint("dbg." + fileName, lineNumber);
    }

    public void handleBreakPoints() {
        List<BreakpointRequest> bpReq = vm.eventRequestManager().breakpointRequests();
        for (BreakpointRequest bp : bpReq) {
            if (bp.isEnabled()) {
                System.out.println("Breakpoint : " + bp + ", location : " + bp.location());
            }
        }
    }

    public void handleBreakOnce() throws IOException, AbsentInformationException {
        System.out.println("Enter the name of the file : ");
        String fileName = getUserInput();
        System.out.println("Enter the number of the line : ");
        int lineNumber = Integer.parseInt(getUserInput());
        for (ReferenceType targetClass : vm.allClasses()) {
            if (targetClass.name().equals("dbg." + fileName)) {
                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(location);
                bpReq.addCountFilter(1);
                bpReq.enable();
            }
        }
    }

    public void handleBreakOnCount() throws IOException {
        System.out.println("Enter the name of the file : ");
        this.breakOnCountFile = getUserInput();
        System.out.println("Enter the number of the line : ");
        this.breakOnCountLine = Integer.parseInt(getUserInput());
        System.out.println("Enter the count number > 0 : ");
        this.breakOnCountCounter = Integer.parseInt(getUserInput());
    }

    public void handleBreakBeforeMethodCall() throws IOException {
        System.out.println("Enter the name of the method : ");
        String methodName = getUserInput();
        for (ReferenceType targetClass : vm.allClasses()) {
            List<Method> methods = targetClass.methodsByName(methodName);
            if (methods.size() > 0) {
                Location location = methods.get(0).location();
                BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(location);
                bpReq.enable();
            }
        }
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

    public StepRequestHandler enableStepIntoRequest(LocatableEvent event) {
        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_MIN, StepRequest.STEP_INTO);
        stepRequest.enable();
        return new StepRequestHandlerImpl(stepRequest);
    }

    public StepRequestHandler enableStepOverRequest(LocatableEvent event) {
        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_LINE, StepRequest.STEP_OVER);
        stepRequest.enable();
        return new StepRequestHandlerImpl(stepRequest);
    }

    private ObjectReference getReceiverOfFrame(StackFrame frame) {
        return frame.thisObject();
    }

    public String getUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public Method getExecutedMethod() throws IncompatibleThreadStateException {
        StackFrame frame = getStackFrame(0);
        this.event.thread().suspend();
        Method method = frame.location().method();
        this.event.thread().resume();
        return method;
    }

    public StackFrame getStackFrame(int index) throws IncompatibleThreadStateException {
        return this.event.thread().frame(index);
    }

    public void setBreakPoint(String className, int lineNumber) throws AbsentInformationException {
        for (ReferenceType targetClass : vm.allClasses()) {
            if (targetClass.name().equals(className)) {
                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(location);
                bpReq.enable();
            }
        }
    }

}