package reactivejava2.chap02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import io.reactivex.Observable;
import reactivejava2.common.Order;

/**
 * 
 * fromIterable() 함수 활용 예
 *
 */
public class ObservableFromIterable {
    public void fromIterableList() {
        List<String> names = new ArrayList<>();
        names.add("Jerry");
        names.add("William");
        names.add("Bob");
        
        Observable<String> source = Observable.fromIterable(names);
        source.subscribe(System.out::println);
    }
    
    public void fromIterableSet() {
        Set<String> cities = new HashSet<String>();
        cities.add("Seoul");
        cities.add("London");
        cities.add("NewYork");
        
        Observable<String> source = Observable.fromIterable(cities);
        source.subscribe(System.out::println);
    }
    
    public void fromIterableBlockingQueue() {
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(100);
        orderQueue.add(new Order("ORD-1"));
        orderQueue.add(new Order("ORD-2"));
        orderQueue.add(new Order("ORD-3"));
        
        Observable<Order> source = Observable.fromIterable(orderQueue);
        source.subscribe(order -> System.out.println(order));
    }
    
    public static void main(String[] args) {
        ObservableFromIterable demo = new ObservableFromIterable();
        demo.fromIterableList();
        demo.fromIterableSet();
        demo.fromIterableBlockingQueue();
    }
}
