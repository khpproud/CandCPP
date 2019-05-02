package debug;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 디버그용 Subscriber 예제
 */
public class DebugSubscriber<T> extends DisposableSubscriber<T> {
    private String label;

    public DebugSubscriber() {
        super();
    }

    public DebugSubscriber(String label) {
        super();
        this.label = label;
    }

    protected String getLabel() {
        return label;
    }

    @Override
    public void onNext(T t) {
        // onNext 메서드 호출 시 출력
        PrintMessageUtil.next(label, t);
    }

    @Override
    public void onError(Throwable t) {
        // onError 메서드 호출시 출력
        PrintMessageUtil.error(label, t);
    }

    @Override
    public void onComplete() {
        // onComplete 메서드 호출 시 출력
        PrintMessageUtil.complete(label);
    }
}
