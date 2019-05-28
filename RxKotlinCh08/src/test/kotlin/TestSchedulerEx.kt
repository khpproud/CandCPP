import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

class TestSchedulerEx {
    @Test
    fun `test by fast forwarding time`() {
        val testScheduler = TestScheduler()

        val observable = Observable.interval(5, TimeUnit.MINUTES, testScheduler)
        val testObserver = TestObserver<Long>()

        observable.subscribe(testObserver)
        testObserver.assertSubscribed()

        // 배출 간격이 5분이기 때문에 하나도 존재해서는 안됨
        testObserver.assertValueCount(0)

        // 100분을 빨리감기 함
        testScheduler.advanceTimeBy(100, TimeUnit.MINUTES)
        // 20개의 배출을 받았는지 테스트
        testObserver.assertValueCount(20)

        // 400분 빨리감기
        testScheduler.advanceTimeBy(400, TimeUnit.MINUTES)
        // 100개의 배출을 받았는지 테스트
        testObserver.assertValueCount(100)
    }
}