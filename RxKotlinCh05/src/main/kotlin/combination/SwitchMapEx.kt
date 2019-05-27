package combination

import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * switchMap() - 모든 배출을 비동기로 대기하지만 정해진 시간 이내의 최신 아이템만 배출
 */
fun main() {
//    println("Without Delay")
//    Observable.range(1, 10)
//        .switchMap {
//            val randDelay = Random().nextInt(100)
//            return@switchMap Observable.just(it)
//        }.blockingSubscribe { println("Received $it") }
//
//    println("With Delay")
//    Observable.range(1, 10)
//        .switchMap {
//            val randDelay = Random().nextInt(100)
//            return@switchMap Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
//        }.blockingSubscribe { println("Received $it") }

    Observable.range(1, 10)
        .switchMap {
            val randDelay = Random().nextInt(100)
            if (it % 3 == 0)
                Observable.just(it)
            else
                Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
        }.blockingSubscribe { println("Received $it") }
}