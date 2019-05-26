import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

// RxKotlin과 kotlin의 푸시, 풀 메커니즘
fun main() {
    // Iterator 패턴의 풀 매커니즘
    var list: List<Any> = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f)
    var iterator = list.iterator()
    while (iterator.hasNext())
        println(iterator.next())

    // Observer 패턴에 기반한 푸시 메커니즘
    var observable: Observable<Any> = list.toObservable()
    observable.subscribeBy(onNext = { println(it) }, onError = { it.printStackTrace() }, onComplete = { println("Done!") })

    // Reactive Even, Odd
    var subject: Subject<Int> = PublishSubject.create()
    subject.map { isEven(it) }.subscribe { println("The number is ${(if (it) "Even" else "Odd")}") }

    subject.onNext(4)
    subject.onNext(3)
}

fun isEven(num: Int): Boolean = num % 2 == 0