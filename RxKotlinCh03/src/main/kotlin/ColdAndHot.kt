import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * Cold & Hot Observable
 */
fun main() {
    // Cold observable - 구독(subscribe()) 시에 실행을 시작 아이템을 동일한 순서로 배출
//    val observable: Observable<String> = listOf("Str 1", "Str 2", "Str 3").toObservable()
//
//    observable.subscribe( { println("Received $it") }, { println("Error ${it.message}") }, { println("Done") } )
//    observable.subscribe(observer)
//
//    // Hot observable - 구독 여부에 관계 없이 배출 - 주로 이벤트 대응에 사용됨 and 1대다 관계(1 Observable : many Observer) 가질 수 있음
//    // ConnectableObservable : subscribe 시가 아닌 connect가 호출된 후 활성화
//    val connectableObservable = listOf("Str 1", "Str 2", "Str 3").toObservable().publish()
//
//    connectableObservable.subscribe { println("Subscription 1: $it") }
//    connectableObservable.map(String::reversed).subscribe { println("Subscription 2: $it") }
//    connectableObservable.connect()
//    // 아래는 배출을 받지 못함
//    connectableObservable.subscribe { println("Subscription 3: $it") }

    val connectableObservable2 = Observable.interval(300L, TimeUnit.MILLISECONDS).publish()
    connectableObservable2.subscribe { println("Subscription 1: $it") }
    connectableObservable2.subscribe { println("Subscription 2: $it") }
    connectableObservable2.connect()
    runBlocking { delay(600) }
    connectableObservable2.subscribe { println("Subscription 3: $it") }
    runBlocking { delay(600) }
}