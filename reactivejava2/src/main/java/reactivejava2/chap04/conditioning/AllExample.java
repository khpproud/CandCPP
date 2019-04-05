package reactivejava2.chap04.conditioning;

import io.reactivex.Observable;
import io.reactivex.Single;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

/**
 * 
 * all() 함수 사용 예
 *
 */
public class AllExample {
    public void all() {
        String[] data =  { "1", "2", "3", "4", "5", "6" };
        
        Single<Boolean> source = Observable.fromArray(data)
                .map(Shape::getShape)
                .all(Shape.BALL::equals);
        // .all(val -> Shape.BALL.equals(Shape.getShape(val)));
        source.subscribe(Log::i);
    }
    
    public static void main(String[] args) {
        AllExample demo = new AllExample();
        demo.all();
    }
}
