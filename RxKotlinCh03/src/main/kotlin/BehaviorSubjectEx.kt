import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject

/**
 * BehaviorSubject - 구독 전의 마지막 데이터와 구독 후 모든 데이터 수신
 * ReplaySubject - 갖고 있는 모든 아이템을 옵저버의 구독 시점과 관계없이 다시 전달(버퍼에 저장된 용량만큼)
 */
fun main() {
    val subject = BehaviorSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)
    subject.subscribe( { println("S1 received $it") }, { println("Error ${it.message}") }, { println("complete") } )
    subject.onNext(4)
    subject.subscribe( { println("S2 received $it") }, { println("Error ${it.message}") }, { println("complete") } )
    subject.onComplete()

    val subject2 = ReplaySubject.create<Int>()
    subject2.onNext(1)
    subject2.onNext(2)
    subject2.onNext(3)
    subject2.subscribe( { println("S1 received $it") }, { println("Error ${it.message}") }, { println("complete") } )
    subject2.onNext(4)
    subject2.subscribe( { println("S2 received $it") }, { println("Error ${it.message}") }, { println("complete") } )
    subject2.onComplete()
}