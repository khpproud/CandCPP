import io.reactivex.Observable
import java.io.Closeable

/**
 * RxKotlin 에서 리스소 관리를 지원하는 using 에 대한 사용 예
 */
class Resource: Closeable {
    init {
        println("Resource Created!")
    }

    // 리소스에서 가져온 데이터를 모킹
    val data: String = "Hello Resource"

    override fun close() {
        println("Resource Closed!")
    }
}

fun main() {
    Observable.using({
        Resource()
    }, {
        Observable.just(it)
    }, {
        it.close()
    }).subscribe { println("Resource Data ${it.data}") }
}