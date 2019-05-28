import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * compose() : 여러 연산자를 결합해 새 연산자를 만듬(ObservableTransformer 인터페이스 구현)
 */
fun main() {
    // 두 연산자를 합성 하기 전
//    Observable.range(1, 10)
//        .map {
//            println("map - ${getThreadName()} $it")
//            it
//        }.subscribeOn(Schedulers.computation())
//        .observeOn(Schedulers.io())
//        .subscribe { println("onNext - ${getThreadName()} $it") }
//    runBlocking { delay(200) }

//    Observable.range(1, 10)
//        .map {
//            println("map - ${getThreadName()} $it")
//            it
//        }.compose(SchedulerManager(Schedulers.computation(), Schedulers.io()))
//        .subscribe { println("onNext - ${getThreadName()} $it") }
//    runBlocking { delay(200) }

    // 람다를 사용해 구현
    Observable.range(1, 10)
        .compose<List<Int>> {
            upstream: Observable<Int> -> upstream.toList().toObservable()
        }
        .first(listOf())
        .subscribeBy { println(it) }
}

// subscribeOn(), observeOn() 합성
class SchedulerManager<T>(val subscribeScheduler: Scheduler, val observeScheduler: Scheduler)
    : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)
    }

}

fun getThreadName(): String = Thread.currentThread().name