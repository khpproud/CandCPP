package reactivejava2.chap05;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * IO 스케줄러 사용 예
 *
 */
public class IOSchedulerExample {
    public void io() {
        // 현재 홈폴더에 파일 목록 생성
        String home = "/Users/patrick/";
        File[] files = new File(home).listFiles();
        Observable<String> source = Observable.fromArray(files)
                .filter(f -> !f.isDirectory())
                .map(f -> f.getAbsolutePath())
                .subscribeOn(Schedulers.io());
        source.subscribe(Log::i);
        CommonUtils.sleep(500);
    }
    
    public static void main(String[] args) {
        IOSchedulerExample demo = new IOSchedulerExample();
        demo.io();
    }
}
