import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Flowable 과 Observable 의 차이
 */
fun main() {
    // Observable
//    Observable.range(1, 1000)
//        .map { MyItem(it) }
//        .observeOn(Schedulers.computation())
//        .subscribe( {
//            print("Received $it;\t")
//            runBlocking { delay(50) }
//        }, { it.printStackTrace() } )
//    runBlocking { delay(60000) }

    // Flowable
    Flowable.range(1, 1000)
        .map { MyItem(it) }
        .observeOn(Schedulers.io())
        .subscribe( {
            println("Received $it")
            runBlocking { delay(50) }
        }, { it.printStackTrace() } )
    runBlocking { delay(60000) }
}