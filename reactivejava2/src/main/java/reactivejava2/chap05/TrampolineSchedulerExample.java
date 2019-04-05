package reactivejava2.chap05;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * 트램펄린 스케줄러 사용 예
 *
 */
public class TrampolineSchedulerExample {
    public void trampoline() {
        String[] orgs = { "1", "3", "5" };
        
        Observable<String> source = Observable.fromArray(orgs);
        
        // 구독 #1
        source.subscribeOn(Schedulers.trampoline())
        .map(data -> "<<" + data + ">>")
        .subscribe(Log::i);
        
        // 구독 #2
        source.subscribeOn(Schedulers.trampoline())
        .map(data -> "##" + data + "##")
        .subscribe(Log::i);
        
        // 메인 스레드에서 실행되므로 없어도 됨
        CommonUtils.sleep(500);
    }
    
    public static void main(String[] args) {
        TrampolineSchedulerExample demo = new TrampolineSchedulerExample();
        demo.trampoline();
    }
}
