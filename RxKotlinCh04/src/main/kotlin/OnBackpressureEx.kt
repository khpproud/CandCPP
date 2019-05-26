import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * BackpressureStrategy.MISSING 과 onBackpressureXXX() 사용
 */
fun main() {
    val source = Observable.range(1, 1000)
    // onBackpressureBuffer()
//    source.toFlowable(BackpressureStrategy.MISSING)
//        .onBackpressureBuffer(20)
//        .map { MyItem(it) }
//        .observeOn(Schedulers.io())
//        .subscribe {
//            println(it)
//            runBlocking { delay(100) }
//        }
//    runBlocking { delay(60000) }

    // onBackpressureDrop()
//    source.toFlowable(BackpressureStrategy.MISSING)
//        .onBackpressureDrop { print("Dropped $it;\t") }
//        .map { MyItem(it) }
//        .observeOn(Schedulers.io())
//        .subscribe {
//            println(it)
//            runBlocking { delay(100) }
//        }
//    runBlocking { delay(60000) }

    // onBackpressureLatest()
    source.toFlowable(BackpressureStrategy.MISSING)
        .onBackpressureLatest()
        .map { MyItem(it) }
        .observeOn(Schedulers.io())
        .subscribe {
            println(it)
            runBlocking { delay(100) }
        }
    runBlocking { delay(60000) }
}