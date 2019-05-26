import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Subscriber(구독자) 설정 예
 */
fun main() {
    Flowable.range(1, 15)
        .map { MyItem(it) }
        .observeOn(Schedulers.io())
        .subscribe(subscriber)
    runBlocking { delay(10000) }
}

val subscriber = object : Subscriber<MyItem> {
    lateinit var subscription: Subscription
    override fun onComplete() {
        println("Done")
    }

    override fun onSubscribe(s: Subscription) {
        subscription = s
        subscription.request(5)
    }

    override fun onNext(t: MyItem) {
        runBlocking { delay(50) }
        println("Subscriber received $t!")
        if (t.id == 5) {
            println("Requesting two more")
            subscription.request(2)
        }
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
    }
}