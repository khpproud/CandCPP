package reactivejava2.chap04.combine;

import io.reactivex.Observable;
import reactivejava2.common.Log;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.text.DecimalFormat;

import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 * 주택용 전기 요금 계산 예 (Zip 활용)
 *
 */
public class ElectricBills {
    int index = 0;
    String[] data = {
            "100",      // 910 + 93.3 * 100 = 10,240원
            "300",      // 1600 + 93.3 * 200 + 187.9 * 100 = 39,050원
            "800",      // 7300 + 93.3 * 200 + 187.9 * 200 + 280.65 * 200 = 175,800원
    };
    
    // 기본 요금 계산
    Observable<Integer> basePrice = Observable.fromArray(data)
            .map(Integer::parseInt)
            .map(val -> {
               if (val <= 200) return 910;
               else if(val <= 400) return 1600;
               else return 7300;
            });
            
    // 전력량 요금 계산
    Observable<Integer> usagePrice = Observable.fromArray(data)
            .map(Integer::parseInt)
            .map(val -> {
               double series1 = min(200, val) * 93.3;                   // 200kWh 이하 구간
               double series2 = min(200, max(val - 200, 0)) * 187.9;    // 400kWh 이하 구간
               double series3 = max(val - 400, 0) * 280.65;             // 400kWh 초과 구간
               return (int)(series1 + series2 + series3);
            });
    
    // 멤버 변수인 인덱스를 사용(부수 효과 발생 우려)
    public void electricBillV1() {
        // 기본 요금 + 전력량 요금
        Observable<Integer> source = Observable.zip(
                basePrice,
                usagePrice,
                (a, b) -> a + b);
        // 결과 출력
        source.map(val -> new DecimalFormat("#,###").format(val))
        .subscribe(val -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Usage : " + data[index] + " kWh => ");
            sb.append("Price : " + val + "원");
            Log.i(sb.toString());
            index++;
        });
    }
    
    // 부수 효과 없앤 계산
    public void electricBillV2() {
        Observable<Pair<String, Integer>> source = Observable.zip(
                basePrice,
                usagePrice,
                Observable.fromArray(data),
                (v1, v2, i) -> Pair.of(i, v1 + v2));
        
        // 결과 출력
        source.map(val -> Pair.of(val.getLeft(), new DecimalFormat("#,###").format(val.getRight())))
                .subscribe(val -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Usage : " + val.getLeft() + " kWh => ");
                    sb.append("Price : " + val.getRight() + "원");
                    Log.i(sb.toString());
                });
    }
    
    
    public static void main(String[] args) {
        ElectricBills demo = new ElectricBills();
        demo.electricBillV1();
        demo.electricBillV2();
    }
}
