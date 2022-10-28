package command;

import dbg.VMHandler;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements DebuggingCommand {

    VMHandler vmHandler;

    public HelpCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        StringBuilder stringBuilder = new StringBuilder();
        List<DebuggingCommand> commands = getDebuggingCommands();

        for (DebuggingCommand command : commands) {
            stringBuilder.append("- ");
            stringBuilder.append(command.commandName());
            stringBuilder.append("\n");
            stringBuilder.append(command.description());
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }

    private List<DebuggingCommand> getDebuggingCommands() {
        List<DebuggingCommand> commands = new ArrayList<>();
        commands.add(new StepCommand(vmHandler));
        commands.add(new StepOverCommand(vmHandler));
        commands.add(new ContinueCommand(vmHandler));
        commands.add(new FrameCommand(vmHandler));
        commands.add(new TemporariesCommand(vmHandler));
        commands.add(new StackCommand(vmHandler));
        commands.add(new ReceiverCommand(vmHandler));
        commands.add(new SenderCommand(vmHandler));
        commands.add(new ReceiverVariablesCommand(vmHandler));
        commands.add(new MethodCommand(vmHandler));
        commands.add(new ArgumentsCommand(vmHandler));
        commands.add(new PrintVarCommand(vmHandler));
        commands.add(new BreakCommand(vmHandler));
        commands.add(new BreakpointsCommand(vmHandler));
        commands.add(new BreakOnceCommand(vmHandler));
        commands.add(new BreakOnCountCommand(vmHandler));
        commands.add(new BreakBeforeMethodCallCommand(vmHandler));
        return commands;
    }

    @Override
    public boolean isResumable() {
        return false;
    }

    @Override
    public String commandName() {
        return "help";
    }

    @Override
    public String description() {
        return "I am a command that shows information about the other commands.";
    }
}
