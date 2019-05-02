package debug;

import io.reactivex.observers.DisposableSingleObserver;

public class DebugSingleObserver<T> extends DisposableSingleObserver<T> {
    private String label;

    public DebugSingleObserver() {
        super();
    }

    public DebugSingleObserver(String label) {
        this.label = label;
    }

    @Override
    public void onSuccess(T t) {
        PrintMessageUtil.next(label, t);
    }

    @Override
    public void onError(Throwable e) {
        PrintMessageUtil.error(label, toString());
    }
}
