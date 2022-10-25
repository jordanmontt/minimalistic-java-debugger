package dbg;

import com.sun.jdi.request.StepRequest;

public class NullStepRequest implements StepRequestHandler {
    StepRequest stepRequest;

    @Override
    public void disable() {
    }
}
