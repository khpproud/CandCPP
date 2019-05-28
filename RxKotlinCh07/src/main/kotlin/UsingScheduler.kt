import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * subscribeOn(), observeOn() 사용 예
 */
fun main() {
    // 메인 스레드 에서 작업 수행
//    (1.. 10).toObservable()
//        .map {
//            println("Mapping $it - ${getThreadName()}")
//            return@map it
//        }.subscribe { println("Received $it - ${getThreadName()}")}

    // subscribeOn() : 구독 스레드를 변경
//    (1.. 10).toObservable()
//        .map {
//            println("Mapping $it - ${getThreadName()}")
//            return@map it
//        }.subscribeOn(Schedulers.computation())
//        .subscribe { println("Received $it - ${getThreadName()}") }
//    runBlocking { delay(1000) }

    // observeOn() : 다른 스레드에서 관찰
    (1.. 10).toObservable()
        .observeOn(Schedulers.computation())
        .map {
            println("Mapping $it - ${getThreadName()}")
            return@map it
        }
        .observeOn(Schedulers.io())
        .subscribe { println("Received $it - ${getThreadName()}") }
    runBlocking { delay(1000) }
}