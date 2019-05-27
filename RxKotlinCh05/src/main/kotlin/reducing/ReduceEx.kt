package reducing

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

/**
 * 축소 연산자
 */
fun main() {
    // count() - 프롣서의 배출량을 담고 있는 Single 객체 반환
    listOf(1, 5, 7, 3, 5, 1, 6, 9).toObservable().count().subscribeBy { println("count $it") }

    // reduce() - 모든 배출을 누적해서 onComplete() 시 결과를 배출
    Observable.range(1, 10).reduce { prevAcc, newEmission -> prevAcc + newEmission }
        .subscribeBy { println(it) }

    Observable.range(1, 5)
        .reduce { prevAcc: Int, newEmission: Int -> prevAcc * 10 + newEmission }
        .subscribeBy { println("accumulation $it") }
}