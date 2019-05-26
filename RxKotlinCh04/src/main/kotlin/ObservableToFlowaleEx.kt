import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Observable 에서 Flowable 변환 후 배압 기능 살펴보기
 */
fun main() {
    val source = Observable.range(1, 1000)
//    source.toFlowable(BackpressureStrategy.BUFFER)
//        .map { MyItem(it) }
//        .observeOn(Schedulers.io())
//        .subscribe {
//            print("Rec. $it;\t")
//            runBlocking { delay(50) }
//        }
//    runBlocking { delay(60000) }

    // BackpressureStrategy.ERROR
//    source.toFlowable(BackpressureStrategy.ERROR)
//        .map { MyItem(it) }
//        .observeOn(Schedulers.io())
//        .subscribe {
//            println(it)
//            runBlocking { delay(50) }
//        }
//    runBlocking { delay(60000) }

    // BackpressureStrategy.DROP
    source.toFlowable(BackpressureStrategy.DROP)
        .map { MyItem(it) }
        .observeOn(Schedulers.io())
        .subscribe {
            println(it)
            runBlocking { delay(50) }
        }
    runBlocking { delay(60000) }
}