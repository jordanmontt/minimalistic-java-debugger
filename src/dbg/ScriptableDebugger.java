package dbg;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

public class ScriptableDebugger {

    private Class debugClass;
    private VirtualMachine vm;
    private InputInterpreter inputInterpreter;
    private VMHandler vmHandler;

    public VirtualMachine connectAndLaunchVM()
            throws IOException, IllegalConnectorArgumentsException, VMStartException {
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, Connector.Argument> arguments = launchingConnector.defaultArguments();
        arguments.get("main").setValue(debugClass.getName());
        VirtualMachine vm = launchingConnector.launch(arguments);
        return vm;
    }

    public void attachTo(Class debuggeeClass) {
        this.debugClass = debuggeeClass;
        try {
            this.vm = connectAndLaunchVM();
            enableClassPrepareRequest(this.vm);
            startDebugger();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalConnectorArgumentsException e) {
            e.printStackTrace();
        } catch (VMStartException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (VMDisconnectedException e) {
            System.out.println("Virtual Machine is disconnected: " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startDebugger()
            throws VMDisconnectedException, InterruptedException, IOException, AbsentInformationException {
        EventSet eventSet = null;
        this.vmHandler = new VMHandler(vm);
        this.inputInterpreter = new InputInterpreter(vmHandler);

        while ((eventSet = vm.eventQueue().remove()) != null) {
            for (Event event : eventSet) {
            	System.out.println(event.toString());
                if (event instanceof VMDisconnectEvent) {
                    printVmProcesses();
                    return;
                }
                if (event instanceof ClassPrepareEvent) {
                    setBreakPoint(debugClass.getName(), 6);
                    setBreakPoint(debugClass.getName(), 9);
                }
                if (event instanceof BreakpointEvent) {
                	System.out.println("coucou");
                    executeCommandUntilIsResumable((BreakpointEvent) event);
                }
                if (event instanceof StepEvent) {
                    executeCommandUntilIsResumable((StepEvent) event);
                }
                vm.resume();
            }
        }
    }

    private void executeCommandUntilIsResumable(LocatableEvent event) throws IOException {
        String userInput = getUserInput();
        vmHandler.setEvent(event);
        this.inputInterpreter.executeCommand(userInput);
        while (!this.inputInterpreter.isCommandResumable(userInput)) {
            userInput = getUserInput();
            this.inputInterpreter.executeCommand(userInput);
        } 
    }

    private void printVmProcesses() throws IOException {
        System.out.println("=== End of program");
        System.out.println("Debugee output ===");
        InputStreamReader reader = new InputStreamReader(vm.process().getInputStream());
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        char[] buf = new char[vm.process().getInputStream().available()];
        reader.read(buf);
        writer.write(buf);
        writer.flush();
    }

    private String getUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
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

    public void enableClassPrepareRequest(VirtualMachine vm) {
        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.addClassFilter(debugClass.getName());
        classPrepareRequest.enable();
    }

}
