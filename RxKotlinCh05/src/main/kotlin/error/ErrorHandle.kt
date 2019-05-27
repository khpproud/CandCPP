package error

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

/**
 * 에러 처리 연산자
 */
fun main() {
    // 에러 발생
//    Observable.just(1, 2, 3, 4, 5)
//        .map { it / (3 - it) }
//        .subscribe { println("Received $it") }

    // onErrorReturn() - 에러가 발생했을 때 다운스트림으로 전달할 수 있는 기본값을 지정 - onError가 최종 연산자이기 때문에 그 후의 데이터는 무식됨
//    Observable.just(1, 2, 3, 4, 5)
//        .map { it / (3 - it) }
//        .onErrorReturn { -1 }
//        .subscribe { println("Received $it") }

    // onErrorResumeNext() - 에러가 발생했을 때 다은 프로듀서를 구독
//    Observable.just(1, 2, 3, 4, 5)
//        .map { it/(3 - it) }
//        .onErrorResumeNext(Observable.range(10, 3))
//        .subscribe { println("Received $it") }

    // retry() 에러 발생시 재시도
    Observable.just(1, 2, 3, 4, 5)
        .map { it / (3 - it) }
        .retry(3)
        .subscribeBy(
            onNext = { println("Received $it") },
            onError = { println("Error: ${it.message}")}
        )
    println("\nWith Predicate\n")
    var retryCount = 0
    Observable.just(1, 2, 3, 4, 5)
        .map { it / (3 - it) }
        .retry {
            _, _ -> (++retryCount) < 3
        }.subscribeBy(
            onNext = { println("Received $it") },
            onError = { println("Error: ${it.message} ")}
        )
}