package swag.theokanning.airhorn.ui.base.mvp;

public class ViewNotAttachedException extends RuntimeException {

    public ViewNotAttachedException() {
        super("Must attach view to presenter before attempting to perform view operations");
    }
}