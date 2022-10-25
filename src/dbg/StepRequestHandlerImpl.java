package dbg;

import com.sun.jdi.request.StepRequest;

public class StepRequestHandlerImpl implements StepRequestHandler{
    StepRequest stepRequest;

    public StepRequestHandlerImpl(StepRequest stepRequest) {
        this.stepRequest = stepRequest;
    }

    @Override
    public void disable() {
        this.stepRequest.disable();
    }
}
