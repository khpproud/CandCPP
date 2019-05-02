package debug;

import io.reactivex.observers.DisposableMaybeObserver;

public class DebugMaybeObserver<T> extends DisposableMaybeObserver<T> {
    private String label;

    public DebugMaybeObserver() {
        super();
    }

    public DebugMaybeObserver(String label) {
        super();
        this.label = label;
    }

    @Override
    public void onSuccess(T t) {
        PrintMessageUtil.next(label, t);
    }

    @Override
    public void onError(Throwable e) {
        PrintMessageUtil.error(label, e);
    }

    @Override
    public void onComplete() {
        PrintMessageUtil.complete(label);
    }
}
