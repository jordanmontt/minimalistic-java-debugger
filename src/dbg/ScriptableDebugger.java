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
            throws VMDisconnectedException, InterruptedException, IOException, AbsentInformationException, IncompatibleThreadStateException {
        EventSet eventSet = null;
        VMHandler vmHandler = new VMHandler(vm);
        this.inputInterpreter = new InputInterpreter(vmHandler);

        while ((eventSet = vm.eventQueue().remove()) != null) {
            for (Event event : eventSet) {
                if (event instanceof VMDisconnectEvent) {
                    System.out.println("=== End of program");
                    System.out.println("Debugee output ===");
                    InputStreamReader reader = new InputStreamReader(vm.process().getInputStream());
                    OutputStreamWriter writer = new OutputStreamWriter(System.out);
                    char[] buf = new char[vm.process().getInputStream().available()];

                    reader.read(buf);
                    writer.write(buf);
                    writer.flush();
                    return;
                }
                if (event instanceof ClassPrepareEvent) {
                    setBreakPoint(debugClass.getName(), 6);
                    setBreakPoint(debugClass.getName(), 9);
                }
                if (event instanceof BreakpointEvent) {
                    String userInput = getUserInput();
                    vmHandler.setEvent((BreakpointEvent) event);
                    this.inputInterpreter.executeCommand(userInput);
                }
                if (event instanceof StepEvent) {
                    String userInput = getUserInput();
                    vmHandler.setEvent((StepEvent) event);
                    this.inputInterpreter.executeCommand(userInput);
                }
                System.out.println(event.toString());
                vm.resume();
            }
        }
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
