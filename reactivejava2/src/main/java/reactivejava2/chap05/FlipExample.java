package reactivejava2.chap05;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

/**     
 * 
 * 스케줄러 기본 사용 예
 *
 */
public class FlipExample {
    public void flip() {
        String[] objs = { "1-S", "2-T", "3-P" };
        Observable<String> source = Observable.fromArray(objs)
                .doOnNext(data -> Log.i("Original data = " + data))
                // 구독이 실행되는 스레드 지정
                .subscribeOn(Schedulers.newThread())
                // 데이터 흐름의 동작이 실행될 스레드 지정
                .observeOn(Schedulers.newThread())
                .map(Shape::flip);
        source.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
    
    public static void main(String[] args) {
        FlipExample demo = new FlipExample();
        demo.flip();
    }
}
