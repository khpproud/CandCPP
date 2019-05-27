package grouping

import io.reactivex.Observable

/**
 * groupBy() - 특정 속성을 기준으로 배출을 분류
 */
fun main() {
    val observable = Observable.range(1, 30)

//    observable.groupBy { it % 5 }.subscribe { it.subscribe { value -> println("Key ${it.key} Value $value") } }
    // blockingSubscribe() : 새로운 처리를 진행하기 전에 배출에서 처리가 완료될 때 까지 프로그램을 대기 상태로 만듬
    observable.groupBy { it % 5 }.blockingSubscribe { println("Key ${it.key}")
        it.subscribe { println("Received $it") }
    }
}