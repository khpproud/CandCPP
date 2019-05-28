import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun add(a: Int, b: Int) = a + b
fun subtract(a: Int, b: Int) = a - b
fun multiply(a: Int, b: Int) = a * b
fun divide(a: Int, b: Int) = a / b

fun main() {
    Observable.range(1, 10)
        .subscribeOn(Schedulers.computation())
        .subscribe { println("Received $it") }
    runBlocking { delay(10) }
}