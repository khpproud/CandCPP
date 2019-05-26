import io.reactivex.Observable

/**
 * Observable.create() 예
 */
fun main() {
    val observable: Observable<String> = Observable.create<String> {
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onComplete()
    }

    observable.subscribe(observer)

    val observable2: Observable<String> = Observable.create<String> {
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onError(Exception("My Custom error"))
    }

    observable2.subscribe(observer)
}