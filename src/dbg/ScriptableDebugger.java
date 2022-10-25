package dbg;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class ScriptableDebugger {

	private Class debugClass;
	private VirtualMachine vm;

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
			vm = connectAndLaunchVM();
			enableClassPrepareRequest(vm);
			startDebugger();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalConnectorArgumentsException e) {
			e.printStackTrace();
		} catch (VMStartException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (VMDisconnectedException e) {
			System.out.println("Virtual Machine is disconnected: " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startDebugger()
			throws VMDisconnectedException, InterruptedException, IOException, AbsentInformationException, IncompatibleThreadStateException {
		EventSet eventSet = null;
		StepRequest stepRequest = null;
		
		InputReceiver ir = new InputReceiver(vm, stepRequest);
		HashMap<String, InputCommand> hashmap = new HashMap<String, InputCommand>();
		hashmap.put("step", new StepCommand(ir));
		hashmap.put("step-over", new StepOverCommand(ir));
		hashmap.put("continue", new ContinueCommand(ir));
		hashmap.put("frame", new FrameCommand(ir));
		hashmap.put("temporaries", new TemporariesCommand(ir));
		hashmap.put("stack", new StackCommand(ir));
		hashmap.put("receiver", new ReceiverCommand(ir));
		hashmap.put("sender", new ReceiverCommand(ir));

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
					ir.setEvent((BreakpointEvent)event);
					hashmap.get(userInput).execute();
				}

				if (event instanceof StepEvent) {
					String userInput = getUserInput();
					ir.setEvent((StepEvent)event);
					hashmap.get(userInput).execute();
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
