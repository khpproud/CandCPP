import io.reactivex.Observable
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.atomic.AtomicInteger

/**
 * 자신만의 연산자 작성하기 예 : lift() 이용
 */
// ObservableOperator 인터페이스 Instance 구현
// 첫 번째 요소는 일련번호를 추가한 Pair, 두 번째 요소는 실제 배출값을 가짐
class AddSerialNumber<T>: ObservableOperator<Pair<Int,T>,T> {
    // 일련번호에 사용될 AtomicInteger - 스레드 세이프 해야 되므로 AtomicInteger 사용
    val counter: AtomicInteger = AtomicInteger()

    // 다운스트림에 전달한 Observer를 인자로 받아 업스트림 배출을 수신하는데 사용할 다른 Observer 를 반환
    override fun apply(observer: Observer<in Pair<Int, T>>): Observer<in T> {
        return object : Observer<T> {
            override fun onComplete() {
                observer.onComplete()
            }

            override fun onSubscribe(d: Disposable) {
                observer.onSubscribe(d)
            }

            override fun onNext(t: T) {
                observer.onNext(Pair(counter.incrementAndGet(), t))
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }
        }
    }
}

fun main() {
//    Observable.range(10, 20)
//        .lift(AddSerialNumber<Int>())
//        .subscribeBy(
//            onNext = { println("Next $it") },
//            onError = { it.printStackTrace() },
//            onComplete = { println("Completed") }
//        )

    // ObservableOperator 인터페이스는 함수형 인터페이스이므로 람다로 변환 가능
    listOf("Reactive", "Programming", "in", "kotlin", "good", "!").toObservable()
        .lift<Pair<Int, String>> { observer ->
            val counter = AtomicInteger()
            object : Observer<String> {
                override fun onComplete() {
                    observer.onComplete()
                }

                override fun onSubscribe(d: Disposable) {
                    observer.onSubscribe(d)
                }

                override fun onNext(t: String) {
                    observer.onNext(Pair(counter.incrementAndGet(), t))
                }

                override fun onError(e: Throwable) {
                    observer.onError(e)
                }
            }
        }.subscribeBy(
            onNext = { println("Next $it") },
            onError = { it.printStackTrace() },
            onComplete = { println("Completed") }
        )
}