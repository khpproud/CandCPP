package debug;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * 디버그용 completableObserver
 */
public class DebugCompletableObserver<T> extends DisposableCompletableObserver {
    private String label;

    public DebugCompletableObserver() {
        super();
    }

    public DebugCompletableObserver(String label) {
        super();
        this.label = label;
    }

    @Override
    public void onComplete() {
        PrintMessageUtil.complete(label);
    }

    @Override
    public void onError(Throwable e) {
        PrintMessageUtil.error(label, e);
    }
}
