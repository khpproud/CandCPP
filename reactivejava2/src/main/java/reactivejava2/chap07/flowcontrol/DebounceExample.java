package reactivejava2.chap07.flowcontrol;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;

/**
 * 
 * debounce() 함수 사용 예
 *
 */
public class DebounceExample {
    public void debounce() {
        String[] data = { "1" , "2" , "3", "5" };
        
        CommonUtils.exampleStart();
        Observable<String> source = Observable.concat(
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[0]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[1]),
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[2]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[3]))
                .debounce(200L, TimeUnit.MILLISECONDS);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        DebounceExample demo = new DebounceExample();
        demo.debounce();
    }
}
