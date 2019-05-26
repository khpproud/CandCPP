import io.reactivex.Observable
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Observable.from~ (Array, Iterable, Callable, Future) 등 예
 */
fun main() {
    // Iterable
    val list = listOf("Str1", "Str2", "Str3", "Str4")
    val observableFromIterable: Observable<String> = Observable.fromIterable(list)
    observableFromIterable.subscribe(observer)

    // Callable
    val callable = Callable<String> { "From Callable" }
    val observableFromCallable: Observable<String> = Observable.fromCallable(callable)
    observableFromCallable.subscribe(observer)

    // Future
    val future: Future<String> = object : Future<String> {
        override fun isDone(): Boolean = true

        override fun get(): String = "Hello from future"

        override fun get(timeout: Long, unit: TimeUnit?): String = "Hello from future"

        override fun cancel(mayInterruptIfRunning: Boolean): Boolean = false

        override fun isCancelled(): Boolean = false
    }

    val observableFromFuture: Observable<String> = Observable.fromFuture(future)
    observableFromFuture.subscribe(observer)
}