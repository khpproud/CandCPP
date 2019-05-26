import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * 구독 과 해지의 예
 */
fun main() {
    // subscribe 연산자는 Observable 을 Observer 에 연결하는 매개체의 용도로 사용됨
    val observable: Observable<Int> = Observable.range(1, 5)

    // subscribe 의 인자로 세가지 메서드 전달
    observable.subscribe ({ println("Next $it")}, { println("Error $it") }, { println("Done") })

    // subscribe 의 인자로 Observer 인터페이스의 인스턴스 전달
    observable.subscribe(observer)

    // 구독 해지
    runBlocking {
        val observable: Observable<Long> = Observable.interval(100L, TimeUnit.MILLISECONDS)
        val observer: Observer<Long> = object : Observer<Long> {
            lateinit var disposable: Disposable

            override fun onSubscribe(d: Disposable) {
                // 수신된 매개변수 Disposable 값 할당
                disposable = d
            }

            override fun onNext(t: Long) {
                println("Received $t")
                if (t >= 10 && !disposable.isDisposed) {
                    disposable.dispose()
                    println("Disposed")
                }
            }

            override fun onError(e: Throwable) {
                println("Error ${e.message}")
            }

            override fun onComplete() {
                println("Completed")
            }

        }
        observable.subscribe(observer)
        delay(1500)
    }
}