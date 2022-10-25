package command;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import dbg.VMHandler;

public class ArgumentsCommand implements InputCommand {
    VMHandler ir;

    public ArgumentsCommand(VMHandler ir) {
        this.ir = ir;
    }

    @Override
    public void execute() {
        try {
            this.ir.getMethodArguments();
        } catch (IncompatibleThreadStateException | AbsentInformationException e) {
            throw new RuntimeException(e);
        }
    }
}
