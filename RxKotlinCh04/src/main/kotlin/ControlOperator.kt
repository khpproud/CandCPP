import io.reactivex.Flowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * buffer(), throttle(), window() 연산자로 아이템 처리 조절 예
 */
fun main() {
    // buffer()
    val flowable = Flowable.range(1, 111)
//    flowable.buffer(10)
//        .subscribe { println(it) }

    // buffer(count, skip) skip 매개변수 사용
//    flowable.buffer(10, 15)
//        .subscribe { println("Subscription 1 $it") }

//    flowable.buffer(15, 7)
//        .subscribe { println("Subscription 2 $it") }

    // buffer(timespan, unit) 시간 간격으로 버퍼링
//    val flowable2 = Flowable.interval(100L, TimeUnit.MILLISECONDS)
//    flowable2.buffer(1, TimeUnit.SECONDS)
//        .subscribe { println(it) }
//    runBlocking { delay(5000) }

    // buffer(flowable) 다른 flowable(생산자)를 경계로 취할 수 있음
//    val boundaryFlowable = Flowable.interval(350, TimeUnit.MILLISECONDS)
//    boundaryFlowable.subscribe { println("boundaryFlowable emit $it")}
//
//    val flowable3 = Flowable.interval(100, TimeUnit.MILLISECONDS)
//    flowable3.buffer(boundaryFlowable)
//        .subscribe { println(it) }
//    runBlocking { delay(5000) }

    // window() - 아이템을 컬렉션 형태로 버퍼링 하는 대신 다른 프로듀서 형태로 버퍼링
//    flowable.window(10)
//        .subscribe {
//            it.subscribe {
//                print("$it, ")
//            }
//            println()
//        }

    // throttle() - 배출을 생략
    val flowable4 = Flowable.interval(100, TimeUnit.MILLISECONDS)
    // 200ms 마다 발생하는 첫 번째 배출을 건너뜀
    flowable4.throttleFirst(200, TimeUnit.MILLISECONDS)
        .subscribe { println(it) }
    runBlocking { delay(1000) }
}