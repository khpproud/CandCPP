import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * Observable의 기타 팩토리 메소드 예
 */
fun main() {
    Observable.range(1, 5).subscribe(observer)
    Observable.empty<String>().subscribe(observer)

    runBlocking {
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribe(observer)
        delay(900)
        Observable.timer(400, TimeUnit.MILLISECONDS).subscribe(observer)
        delay(450)
    }
}