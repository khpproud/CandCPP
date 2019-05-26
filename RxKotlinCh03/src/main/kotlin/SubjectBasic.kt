import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * Subject - Observable 과 Observer 의 조합 Hot Observable 구현의 예
 */
fun main() {
    val observable = Observable.interval(300L, TimeUnit.MILLISECONDS)
    val subject = PublishSubject.create<Long>()
    observable.subscribe(subject)
    subject.subscribe { println("Subscription 1 Received $it") }
    runBlocking {
        delay(1200)
    }
    subject.subscribe { println("Subscription 2 Received $it") }
    runBlocking {
        delay(1200)
    }
}
