package reactivejava2.chap05;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * 뉴 스레드 스케줄러 사용 예
 *
 */
public class NewThreadSchedulerExample {
    public void newThread() {
        String[] orgs = { "1", "3", "5" };
        Observable.fromArray(orgs)
        .doOnNext(data -> Log.v("Original data : " + data))
        .map(data -> "<<" + data + ">>")
        .subscribeOn(Schedulers.newThread())
        .subscribe(Log::i);
        // 메인 스레드가 완료되지 않도록 sleep 추가
        CommonUtils.sleep(500);
        Observable.fromArray(orgs)
        .doOnNext(data -> Log.v("Original data : " + data))
        .map(data -> "##" + data + "##")
        .subscribeOn(Schedulers.newThread())
        .subscribe(Log::i);
        CommonUtils.sleep(500);
    }
    
    public static void main(String[] args) {
        NewThreadSchedulerExample demo = new NewThreadSchedulerExample();
        demo.newThread();
    }
}
