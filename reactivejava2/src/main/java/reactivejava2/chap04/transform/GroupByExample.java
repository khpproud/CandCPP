package reactivejava2.chap04.transform;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import reactivejava2.common.Log;
import reactivejava2.common.Shape;

/**
 * 
 * groupBy() 함수 사용 예
 *
 */
public class GroupByExample {
    String[] objs = { "6", "4", "2-T", "2", "4-T", "6-T" };
    
    public void groupBy() {
        Observable<GroupedObservable<String, String>> source = 
                Observable.fromArray(objs)
                .groupBy(Shape::getShape);
        
        source.subscribe(obj -> 
            obj.subscribe(val -> Log.i("Group : " + obj.getKey() + "\t Value : " + val)));
    }
    
    // Ball 그룹 만 filter
    public void groupByFilter() {
        Observable<GroupedObservable<String, String>> source = 
                Observable.fromArray(objs)
                .groupBy(Shape::getShape);
        
        source.subscribe(obj -> 
                obj.filter(val -> obj.getKey().equals(Shape.BALL))
                .subscribe(val -> Log.i("Group : " + obj.getKey() + "\t Value : " + val)));
    }
    
    public static void main(String[] args) {
        GroupByExample demo = new GroupByExample();
        //demo.groupBy();
        demo.groupByFilter();
    }
}
