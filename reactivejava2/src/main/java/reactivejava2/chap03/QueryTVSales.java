package reactivejava2.chap03;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import reactivejava2.common.Log;

/**
 * 
 * 간단한 데이터 쿼리 예
 *
 */
public class QueryTVSales {
    public void run() {
        // 1. 데이터 입력
        List<Pair<String, Integer>> sales = new ArrayList<>();
        
        sales.add(Pair.of("TV", 2500));
        sales.add(Pair.of("Camera", 300));
        sales.add(Pair.of("TV", 1600));
        sales.add(Pair.of("Phone", 800));
        
        Maybe<Integer> tvSales = Observable.fromIterable(sales)
                // 2. 매출 데이터 중 TV 매출을 필터링 함
                .filter(item -> "TV".equals(item.getLeft()))
                .map(sale -> sale.getRight())
                // 3. TV 매출의 합을 구함
                .reduce((sale1, sale2) -> sale1 + sale2);
        tvSales.subscribe(total -> Log.i("Total TV sales : $" + total));
    }
    
    public static void main(String[] args) {
        QueryTVSales demo = new QueryTVSales();
        demo.run();
    }
}
