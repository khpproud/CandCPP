import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable

/**
 * Observable의 이해
 */
// Observer 인터페이스의 인스턴스 생성
val observer: Observer<Any> = object : Observer<Any> {
    override fun onComplete() {
        println("All completed")
    }

    override fun onSubscribe(d: Disposable) {
        println("Subscribed to $d")
    }

    override fun onNext(item: Any) {
        println("Next $item")
    }

    override fun onError(e: Throwable) {
        println("Error Occurred $e")
    }
}

fun main() {
    val list1 = listOf("One", 2, "Three", 4.5, "Five", 6.0f)
    val observable: Observable<Any> = list1.toObservable()

    observable.subscribe(observer)

    val observableOnList: Observable<List<Any>> = Observable.just(list1, listOf("List with single item"),
        listOf(1, 2, 3, 4, 5, 6))

    observableOnList.subscribe(observer)
}