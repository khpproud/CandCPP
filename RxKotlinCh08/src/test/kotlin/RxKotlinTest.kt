import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Maybes
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RxKotlinTest {
    @Test
    fun `check emission count`() {
        val emisionCount = AtomicInteger()
        Observable.range(1, 10)
            .subscribeOn(Schedulers.computation())
            .blockingSubscribe { emisionCount.incrementAndGet() }
        assertEquals(10, emisionCount.get())
    }

    val testList = listOf(2, 10, 5, 6, 9, 8, 7, 1, 4, 3)

    @Test
    fun `test with blockingFirst`() {
        val observable = testList.toObservable().sorted()
        val firstItem = observable.blockingFirst()
        assertEquals(1, firstItem)
    }

    @Test
    fun `test Single with blockingGet`() {
        val observable = testList.toObservable().sorted()

        val firstElement: Single<Int> = observable.first(0)
        val firstItem = firstElement.blockingGet()
        assertEquals(1, firstItem)
    }

    @Test
    fun `test Maybe with blockingGet`() {
        val observable = testList.toObservable().sorted()

        val firstElement: Maybe<Int> = observable.firstElement()
        val firstItem = firstElement.blockingGet()
        assertEquals(1, firstItem)
    }

    @Test
    fun `test with blockingLast`() {
        val observable = testList.toObservable().sorted()

        val lastItem = observable.blockingLast()
        assertEquals(10, lastItem)
    }

    @Test
    fun `test with blockingIterable`() {
        val observable = testList.toObservable().sorted()

        val iterable = observable.blockingIterable()
        assertEquals(testList.sorted(), iterable.toList())
    }

    @Test
    fun `test with blockingForEach`() {
        val observable = testList.toObservable().filter { it % 2 == 0 }
        observable.blockingForEach { assertTrue { it % 2 == 0 } }
    }
}