package combination

import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * flatMap() 과 concatMap()의 차이 비교
 */
fun main() {
    // flatMap
    Observable.range(1, 10)
        .flatMap {
            val randDelay = Random().nextInt(10)
            return@flatMap Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
        }.blockingSubscribe {
            println("Received $it")
        }
//        }.subscribe { println("Received $it") }

    // concatMap
    Observable.range(1, 10)
        .concatMap {
            val randDelay = Random().nextInt(10)
            return@concatMap Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
        }.blockingSubscribe { println("Received $it") }
}