package debug;

public class DebugTimeSubscriber<T> extends DebugSubscriber<T> {
    private String label;

    public DebugTimeSubscriber() {
        super();
    }

    public DebugTimeSubscriber(String label) {
        super(label);
        this.label = label;
    }
    @Override
    protected void onStart() {
        super.onStart();
        PrintMessageUtil.startT(label);
    }

    @Override
    public void onNext(T t) {
        PrintMessageUtil.nextT(label, t);
    }

    @Override
    public void onError(Throwable t) {
        PrintMessageUtil.errorT(label, t);
    }

    @Override
    public void onComplete() {
        PrintMessageUtil.completeT(label);
    }
}
