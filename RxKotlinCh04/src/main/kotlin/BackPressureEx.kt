import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * BackPressure(배압)의 이해
 */
fun main() {
    val observable = Observable.range(1, 10)
//    val subject = BehaviorSubject.create<Int>()
//    subject.observeOn(Schedulers.computation())
//        .subscribe {
//            println("Subs1 Received $it - ${Thread.currentThread().name}")
//            runBlocking { delay(200) }
//        }
//    subject.observeOn(Schedulers.computation())
//        .subscribe { println("Subs2 Received $it - ${Thread.currentThread().name}") }
//    observable.subscribe(subject)
//    runBlocking { delay(2000) }

    observable.map { MyItem(it) }
        .observeOn(Schedulers.computation())
        .subscribe {
            println("Received $it")
            runBlocking { delay(200) }
        }
    runBlocking { delay(2000) }
}

data class MyItem(val id: Int) {
    init {
        println("MyItem Created $id")
    }
}