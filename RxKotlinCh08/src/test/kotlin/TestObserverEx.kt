import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.toObservable
import org.junit.Test

class TestObserverEx {
    val testList = listOf(2, 10, 5, 6, 9, 8, 7, 1, 4, 3, 12, 20, 15, 16, 19, 18, 17, 11, 14, 13)

    @Test
    fun `test with TestObserver`() {
        val observable = testList.toObservable().sorted()

        val testObserver = TestObserver<Int>()
        observable.subscribe(testObserver)

        // 구독이 성공적 이었는가
        testObserver.assertSubscribed()

        // 실행이 완료될떄 까지 스레드를 차단
        testObserver.awaitTerminalEvent()
        // 구독 중에 오류가 발생하지 않았는가
        testObserver.assertNoErrors()
        // 구독이 성공적으로 완료되었는가
        testObserver.assertComplete()
        // 전체 배출 아이템의 개수 count
        testObserver.assertValueCount(20)
        // 각 배출의 예상 값과 실제 값을 순서대로 테스트
        testObserver.assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
    }
}