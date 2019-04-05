package reactivejava2.chap05;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * Executor 변환 스케줄러 사용 예
 *
 */
public class ExecutorSchedulerExample {
    public void executor() {
        final int THREAD_NUM = 10;
        
        String[] data = { "1", "3", "5" };
        Observable<String> source = Observable.fromArray(data);
        Executor executor = Executors.newFixedThreadPool(THREAD_NUM);
        
        source.subscribeOn(Schedulers.from(executor))
        .subscribe(Log::i);
        
        source.subscribeOn(Schedulers.from(executor))
        .subscribe(Log::i);
        
        CommonUtils.sleep(500);
    }
    
    public static void main(String[] args) {
        ExecutorSchedulerExample demo = new ExecutorSchedulerExample();
        demo.executor();
    }
}
