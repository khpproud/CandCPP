import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject

/**
 * AsyncSubject - 소스 Observable 의 완료 시 마지막 값과 완료 만을 전달
 */
fun main() {
//    val observable = Observable.just(1, 2, 3,  4)
//    val subject = AsyncSubject.create<Int>()
//    observable.subscribe(subject)
//    subject.subscribe( { println("Received $it") }, { println("Error ${it.message}") }, { println("Complete") } )
//    subject.onComplete()

    // Subject 로 Observable 에 가입하면 Subject 는 Observable 이 값을 배출할 때마다 내부적으로 onNext 호출됨
    val subject = AsyncSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(4)
    subject.subscribe( { println("S1 received $it") }, { println("Error ${it.message}") }, { println("complete") } )
    subject.onNext(5)
    subject.subscribe( { println("S2 received $it") }, { println("Error ${it.message}") }, { println("complete") } )
    subject.onComplete()
}